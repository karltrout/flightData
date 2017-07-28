package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by karltrout on 7/14/17.
 */
public class CsvReader {

    private static HashMap<String, ArrayList<flightRecord>> flightRecords;

    static final String fileDirectory = "/Users/karltrout/Downloads/";

    public static void main(String[] args) {

        flightRecords = new HashMap<>();

        String fileName = fileDirectory+"final.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(x -> processLine(x));

            System.out.println("Size: "+flightRecords.size());


        } catch (IOException e) {
            e.printStackTrace();
        }

        String flightId = "AAL503";//  "AAL424";
        System.out.println("Processing flight: "+flightId);

        ArrayList<flightRecord> flightData = flightRecords.get(flightId);

        //Find Maximum Altitude
        Stream<flightRecord> flightStream = flightData.stream();
        Optional<flightRecord> maxAltitude =  flightStream.max((alt1, alt2) -> Double.compare(alt1.altitude, alt2.altitude));
        System.out.println("Max Altitude for flight "+flightId+" is: "+maxAltitude.get().altitude);

        //Index the Flight array
        flightRecord[] recordsArray = flightData.toArray(new flightRecord[flightData.size()]);

        //Array to hold all Ascending and leveling records
        ArrayList<flightRecord> climbing = new ArrayList<>();

        boolean isClimbing = false; // Have we began to climb?
        boolean isCruise = false; //Are we Cruising
        boolean isDescending = false; // are We Descending
        int levelingCount = 0; // We want the first and last Leveling data points.
        int cruiseCount = 0; // after a certain amount of Cruising we can assume we have leveled off and can stop collecting Ascending data.
        int maxCruiseCounter = 20; // Once leveled, if we Cruise for 20 records in a row it is safe to stop iterating for Ascending data.
        double maxAltVal = maxAltitude.get().altitude;
        int accentStartIndicator = 0;
        String vectorDirection = "Grounded";

        for (int i = 5; i < recordsArray.length - 15; i++){ /// We start at 5 because we look back 5 records and forward 5 records to compare direction

            double currentAlt = recordsArray[i].altitude;                       //Where we are
            double previousClimbRate = currentAlt - recordsArray[i-5].altitude; //Where we've Been
            double nextClimbRate = recordsArray[i+5].altitude - currentAlt;     //Where we are going

            // Are we Ascending? Next Climb rate should be positive and greater the 55% of the previous ClimbRate
            if( nextClimbRate > 0 && nextClimbRate > (previousClimbRate * .55) ) {
                vectorDirection = "Ascending";
                // If We have any Leveling counts then we are Ascending out of a leveling off period. collect the first and last points of the leveling off.
                if (levelingCount > 0 ){
                    climbing.add(recordsArray[i - levelingCount]);
                    climbing.add(recordsArray[i]);
                    levelingCount = 0;
                }

                if (accentStartIndicator == 0 ) {
                    accentStartIndicator = i; //we started an ascent;
                }
                cruiseCount = 0;
                isClimbing = true; // we are now in the air.
            }

            /* Are we Descending? next Climb Rate is negative and less then ??? TODO: compute Descending based on preivious clib rate. */
            else if(nextClimbRate < 0 && nextClimbRate <  - (currentAlt * .0125)) {
                vectorDirection = "Descending";
                if (levelingCount > 0 ){
                    climbing.add(recordsArray[i - levelingCount]);
                    climbing.add(recordsArray[i]);
                    levelingCount = 0;
                }
                accentStartIndicator = 0;
                cruiseCount = 0;
            }
            /*
            Are we Leveling? if we were ascending and nextClimbRate is positive but less then 55% of the previous climb rate
            then we presume we are leveling out for some period. this is a possible indicator of reaching Cruise altitude, it is also a
            change in the Climb Rate that is significate for modeling the Climb. we use leveling off to indicate a change in the
            rate of climbing and to possible indicate we are reaching the cruise rate.
             */
            else if (nextClimbRate > 0 && nextClimbRate < (previousClimbRate * .55)) {
                vectorDirection = "leveling";
                if (levelingCount == 0 && accentStartIndicator != 0){ // just started leveling gather 2 point at 1/3 and 2/3 of the previous ascent.
                   int oneThird = (i - accentStartIndicator) * 1/3;
                   int twoThirds = (i - accentStartIndicator) * 2/3;
                    climbing.add(recordsArray[i - twoThirds]);
                    climbing.add(recordsArray[i- oneThird]);
                    accentStartIndicator = 0;
                }
                levelingCount ++;
                cruiseCount = 0;
            }
            else{

                if(!isClimbing ) continue; // we haven't taken off yet. So we are not Cruising.
                    vectorDirection = "Cruise";

                    if (levelingCount > 0 ){
                        climbing.add(recordsArray[i - levelingCount]);
                        climbing.add(recordsArray[i]);
                        levelingCount = 0;
                    }
                    // Are we close to max Altitude? if so we are Cruising at or around Max.
                    if (currentAlt > maxAltVal - (maxAltVal *.05) && currentAlt < maxAltVal + (maxAltVal * .05)){
                        vectorDirection = vectorDirection + " Max Altitude";
                    }
                    cruiseCount++;
                }


            System.out.print("P: "+previousClimbRate+" C: "+currentAlt+" N: "+nextClimbRate);
            System.out.println(" Current Vector: "+ vectorDirection);

            //Have we Climbed and have we Cruised long enough.
            if (isClimbing && cruiseCount > maxCruiseCounter ) {

                climbing.add(recordsArray[i- maxCruiseCounter - 1]);
                break;

            }

        }

        System.out.println("Size of Climbing array: "+climbing.size());
        System.out.println("acid, time, latitude, longitude, altitude");

        try (FileWriter output = new FileWriter(fileDirectory + flightId + ".csv")) {

            output.write("acid, time, latitude, longitude, altitude\n");
            climbing.stream().forEach(x -> {
                try {
                    output.write(x.toString()+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ioe){
            //TODO: better error handling here
            ioe.printStackTrace();
        }

        System.out.println("File Saved: "+fileDirectory+flightId+".csv");
    }

    private static void processLine(String x) {

        try {
            String[] cells = x.split(",");

            if (cells.length < 8){
                return;
            }


            if (cells[0].toLowerCase().contains("acid")) return;
            Date timestamp = new GregorianCalendar().getTime();
            //long dateLong = Long.parseLong()
            timestamp.setTime(Long.parseLong(cells[3]));

            flightRecord record =
                    new flightRecord(
                            cells[0], cells[1], cells[2],
                            timestamp,
                            Double.valueOf( (cells[4].equals(""))?"0": cells[4] ),
                            Double.valueOf( (cells[5].equals(""))?"0": cells[5] ) ,
                            Double.valueOf( (cells[6].equals(""))?"0": cells[6] ),
                            (cells.length > 5)?
                            Double.valueOf( (cells.length > 5 && !cells[7].equals(""))? cells[7] : "0" ): 0.0d);

            if(flightRecords.containsKey(cells[0])) {
                flightRecords.get(cells[0]).add(record);
            }
            else{
                ArrayList<flightRecord> tracks = new ArrayList<>();
                tracks.add(record);
                flightRecords.put(cells[0], tracks);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class flightRecord{
        String acid;
        String type;
        String departureAirport;
        Date time;
        Number latitude;
        Number longitude;
        double climbRate;
        double altitude;

        public flightRecord(String acid, String type, String departureAirport, Date time, double climbRate, double latitude, double longitude,  double altitude) {

            this.acid = acid;
            this.type = type;
            this.departureAirport = departureAirport;
            this.time = time;
            this.latitude = latitude;
            this.longitude = longitude;
            this.climbRate = climbRate;
            this.altitude = altitude;
        }

        public String toString(){
            return ""+acid+","+time.getTime()+","+latitude+","+longitude+","+altitude;
        }
    }
}
