package KML;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Csv2Kml {

    private static final Logger logger = Logger.getLogger(Csv2Kml.class);
    private static String csvFileStringPath;
    private static String kmlFileName;
    private static String aircraftTypeRequested;

    private static Random random = new Random();
    private static String switchString;

    public static void main (String[] args){

        if (args.length == 3){
                if(args[0].equalsIgnoreCase("--list")){
                    switchString = "list";
                    csvFileStringPath = args[2];
                    aircraftTypeRequested = "all";
                }
        }

        else if ( args.length < 6 ){

            logger.error("Error : Csv2Kml -t [acType|all] -p /path2csvFile -o outputFile.kml");
            exit(0);

        }

        else {
            aircraftTypeRequested = args[1];
            csvFileStringPath = args[3];
            kmlFileName = args[5];
        }
        HashMap<String, Flight> flights = new HashMap<>();

        try {

            File csvFile = new File(csvFileStringPath);
            if (! csvFile.exists()) throw new FileNotFoundException();

            List<String> lines = Files.readAllLines(csvFile.toPath());

            for (String line : lines) {
                if (line.startsWith("tt_id")) continue;


                String[] values = line.split(",");

                if ( values.length < 9 ) continue;

                if (!aircraftTypeRequested.equalsIgnoreCase("all"))
                    if (!values[2].equalsIgnoreCase(aircraftTypeRequested)) continue;

                String ttid = values[0];
                flights.computeIfPresent(ttid, (key, flight) -> addPositionTo(flight, values));
                flights.putIfAbsent(ttid, createFlight(values));

            }
            logger.info("Completed parsing CSV file. Flights: "+flights.size());

            if(switchString != null && switchString.equalsIgnoreCase("list")){
                printFlightTypeList(flights);
            }
            else {
                createKMLFile(flights);
            }

        } catch (FileNotFoundException exception){
            logger.error("File "+csvFileStringPath+ " does not exists. exiting.");
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void printFlightTypeList(HashMap<String, Flight> flights) {
        HashMap<String, Integer> types = new HashMap<>();

        flights.values().forEach(flight ->
            types.compute(flight.getType(), (type, count) -> count == null ? 1 : count + 1)
        );

        SortedSet<Map.Entry<String, Integer>> sortedTypes = sortTypesByCountDES(types);

        //types.forEach((type, count) -> logger.info("type: "+type+" cnt: "+count));

        sortedTypes.forEach(type -> logger.info("Sorted: " + type.getKey() + " cnt: "+type.getValue()));

    }

    private static SortedSet<Map.Entry<String, Integer>> sortTypesByCountDES(HashMap<String, Integer> types) {

        SortedSet<Map.Entry<String, Integer>> results = new TreeSet<>((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        results.addAll(types.entrySet());
        return results;

    }


    private static void createKMLFile(HashMap<String, Flight> flights) throws FileNotFoundException {

        Kml kml = KmlFactory.createKml();
        Document flightDocument = kml.createAndSetDocument();

        categoriseAndColorACTypesFrom( flights, flightDocument );

        for (Flight flight : flights.values()){

            Placemark flightPlacemark = new Placemark().withStyleUrl(String.format("#%2s",flight.getType()));
            LineString flightPath = flightPlacemark
                    .withName(flight.getAcid() + " - " + flight.getType())
                    .withVisibility(true)
                    .createAndSetLineString()
                    .withExtrude(true)
                    .withAltitudeMode(AltitudeMode.ABSOLUTE)
                    ;

            for (Position position : flight.getSortedPositions()){
                flightPath.addToCoordinates(position.longitude, position.latitude, position.altitude);
            }

            flightDocument.addToFeature(flightPlacemark);

        }

        kml.marshal(new File(kmlFileName));

    }

    private static void categoriseAndColorACTypesFrom(HashMap<String, Flight> flights, Document flightDocument) {
        List<String> types = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (!types.contains(flight.getType())) {
                String color = returnRandomColor();
                flightDocument.createAndAddStyle()
                        .withLineStyle( new LineStyle()
                                .withColor("ff"+color)
                                .withColorMode(ColorMode.NORMAL)
                                .withWidth(1.0))
                        .withPolyStyle( new PolyStyle()
                                .withColor("40"+color)
                                .withColorMode(ColorMode.NORMAL))
                        .withId(flight.getType());
                types.add(flight.getType());
            }
        }
    }

    private static String returnRandomColor() {
        return String.format("%06x", random.nextInt(256*256*256));
    }


    private static Flight addPositionTo(Flight flight, String[] withValues){
        flight.addPosition(new Position( withValues ));
        return flight;
    }

    private static Flight createFlight(String[] values) {
        Flight flight = new Flight(values);
        Position position = new Position( values );
        flight.addPosition(position);

        return flight;
    }


}

class Flight {

    private String ttid;
    private String acid;
    private String type;
    private String departureAirport;
    private List<Position> positions;

    Flight( String[] values ){
        positions = new ArrayList<>();
        this.withTtid(values[0])
                .withAcid(values[1])
                .withType(values[2])
                .withDepartureAirport(values[3]);
    }


    public Flight withTtid(String ttid){ this.ttid = ttid; return this;}
    public Flight withAcid(String acid){ this.acid = acid; return this;}
    public Flight withType(String type){ this.type = type; return this;}
    public Flight withDepartureAirport(String departureAirport){
        this.departureAirport = departureAirport; return this;
    }

    public void addPosition(Position position){
        this.positions.add(position);
    }

    public String getTtid() {
        return ttid;
    }

    public String getAcid() {
        return acid;
    }

    public String getType() {
        return type;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public List<Position> getSortedPositions() {
        Collections.sort(positions);
        return positions;
    }
}


class Position implements Comparable{
    public Long time;
    public Double latitude;
    public Double longitude;
    public Double altitude;

    Position( String[] values ){
        this(values[4], values[6], values[7], values[8]);
    }

    Position( String time, String latitude, String longitude, String altitude ){
        this.time = Long.parseLong(time);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.altitude = Double.parseDouble(altitude);
    }

    @Override
    public int compareTo(Object o) {
        Position otherPosition = (Position) o;
        return this.time.compareTo(otherPosition.time);
    }
}