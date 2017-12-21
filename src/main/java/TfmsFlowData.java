import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;


/**
 * to run :
 * > cd  flightData/target
 * on Local:
 * > /usr/local/spark/bin/spark-submit --class TfmsFlowData --master local[4] --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar --params ./config.properties
 * on YARN:
 * > /usr/local/spark/bin/spark-submit --class TfmsFlowData --master yarn --num-executors 3 --executor-cores 1 --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar --params ./config.properties
 */

public class TfmsFlowData {

  private static Logger logger = Logger.getLogger(TfmsFlowData.class);
  private static Properties properties = new Properties();
  private static String hadoopInputURI;
  private static String rootSavePath;
  private static String fileNameConstant;


  public static void main(String[] args) {

    if (args.length < 2 || args[0].compareToIgnoreCase("--params") != 0) {
      logger.error("No properties file supplied.");
      logger.info("Usage: TfmsFlowData --params /path/to/parameters.file.");
      System.exit(0);
    }

    String propertiesFilePath = args[1];

    try {
      loadProperties(propertiesFilePath);
    } catch (Exception exception) {
      logger.error("Properties file " + propertiesFilePath + " does not exist or is not configured properly.");
      logger.info("Usage: TfmsFlowData --params /path/to/parameters.file.");
      System.exit(0);
    }

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    String hadoopImportFilesPathString = hadoopInputURI + createFileNamePatternFrom(calendar)+"*";

    SparkSession spark = SparkSession.builder().getOrCreate();

    HashMap<String, String> options = new HashMap<>();
    options.put("rowTag", "fdm:fltdMessage");

    Dataset<Row> df =
        spark.read()
            .format("com.databricks.spark.xml")
            .options(options)
            .schema(TfmsFlowMessageSchema.getSchema())
            .load(hadoopImportFilesPathString)
            .cache();

    extractTrackInformationMessages(df);
    extractArrivalInformationMessages(df);
    extractDepartureInformationMessages(df);
    extractBoundryCrossingInformationMessages(df);
    extractFlightPlanInformationMessages(df);
    extractFlightPlanAmendmentInformationMessages(df);

  }

  private static void extractFlightPlanAmendmentInformationMessages(Dataset<Row> df) {
    String savePath;
    Dataset<Row>
    flightPlanAmendmentInformation = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:newAircraftId").alias("newAcid"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newFlightAircraftSpecs._VALUE").alias("newAcType"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newFlightAircraftSpecs._equipmentQualifier").alias("newSpecialAircraftQualifier"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newSpeed.nxce:filedTrueAirSpeed").alias("newFiledTrueAirSpeed"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newSpeed.nxce:mach").alias("newMach"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:fixRadialDistance._VALUE").alias("newCP_FRD_Fix"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:fixRadialDistance._radial").alias("newCP_FRD_Radial"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:fixRadialDistance._distance").alias("newCP_FRD_distance"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._degrees").alias("newlatitude_degrees"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._direction").alias("newlatitude_direction"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._minutes").alias("newlatitude_minutes"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._seconds").alias("newlatitude_seconds"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._degrees").alias("newlongitude_degrees"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._direction").alias("newlongitude_direction"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._minutes").alias("newlongitude_minutes"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._seconds").alias("newlongitude_seconds"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationPoint.nxce:namedFix").alias("newCP_fix"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationTime._VALUE").alias("newCoordinationTime"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newCoordinationTime._type").alias("newCoordinationTimeProposed"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newAltitude.nxce:assignedAltitude.nxce:simpleAltitude").alias("newAltitude"),
        df.col("fdm:flightPlanAmendmentInformation.nxcm:amendmentData.nxcm:newRouteOfFlight._legacyFormat").alias("newLegacyFlightPlan")

    ).filter("_msgType = 'flightPlanAmendmentInformation'");

    savePath = "flightPlanAmendmentInformation";
    saveToCsv(flightPlanAmendmentInformation, savePath);
  }

  private static void extractFlightPlanInformationMessages(Dataset<Row> df) {
    String savePath;
    Dataset<Row>
    flightPlanInformation = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:flightPlanInformation.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:flightPlanInformation.nxcm:flightAircraftSpecs._VALUE").alias("acType"),
        df.col("fdm:flightPlanInformation.nxcm:flightAircraftSpecs._specialAircraftQualifier").alias("specialAircraftQualifier"),
        df.col("fdm:flightPlanInformation.nxcm:flightAircraftSpecs._equipmentQualifier").alias("equippage"),
        df.col("fdm:flightPlanInformation.nxcm:speed.nxce:filedTrueAirSpeed").alias("filedTrueAirSpeed"),
        df.col("fdm:flightPlanInformation.nxcm:speed.nxce:mach").alias("mach"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:fixRadialDistance._VALUE").alias("CP_FRD_Fix"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:fixRadialDistance._radial").alias("CP_FRD_Radial"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:fixRadialDistance._distance").alias("CP_FRD_distance"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._degrees").alias("latitude_degrees"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._direction").alias("latitude_direction"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._minutes").alias("latitude_minutes"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:latitude.nxce:latitudeDMS._seconds").alias("latitude_seconds"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._degrees").alias("longitude_degrees"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._direction").alias("longitude_direction"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._minutes").alias("longitude_minutes"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:latLong.nxce:longitude.nxce:longitudeDMS._seconds").alias("longitude_seconds"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationPoint.nxce:namedFix").alias("CP_fix"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationTime._VALUE").alias("coordinationTime"),
        df.col("fdm:flightPlanInformation.nxcm:coordinationTime._type").alias("coordinationTimeProposed"),
        df.col("fdm:flightPlanInformation.nxcm:altitude.nxce:requestedAltitude.nxce:simpleAltitude").alias("requestedAltitude"),
        df.col("fdm:flightPlanInformation.nxcm:routeOfFlight._legacyFormat").alias("legacyFlightPlan")
    ).filter("_msgType = 'flightPlanInformation'");

    savePath = "flightPlanInformation";
    saveToCsv(flightPlanInformation, savePath);
  }

  private static void extractBoundryCrossingInformationMessages(Dataset<Row> df) {
    String savePath;
    Dataset<Row>
    boundaryCrossingUpdate = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:flightAircraftSpecs._VALUE").alias("acType"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:flightAircraftSpecs._specialAircraftQualifier").alias("specialAircraftQualifier"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:flightAircraftSpecs._equipmentQualifier").alias("equippage"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:speed.nxce:filedTrueAirSpeed").alias("filedTrueAirSpeed"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:speed.nxce:mach").alias("mach"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition._boundaryCrossingTime").alias("crossingTime"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:fixRadialDistance._VALUE").alias("FRD_Fix"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:fixRadialDistance._radial").alias("FRD_Radial"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:fixRadialDistance._distance").alias("FRD_distance"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:latitude.nxce:latitudeDMS._degrees").alias("latitude_degrees"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:latitude.nxce:latitudeDMS._direction").alias("latitude_direction"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:latitude.nxce:latitudeDMS._minutes").alias("latitude_minutes"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:latitude.nxce:latitudeDMS._seconds").alias("latitude_seconds"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:longitude.nxce:longitudeDMS._degrees").alias("longitude_degrees"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:longitude.nxce:longitudeDMS._direction").alias("longitude_direction"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:longitude.nxce:longitudeDMS._minutes").alias("longitude_minutes"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:latLong.nxce:longitude.nxce:longitudeDMS._seconds").alias("longitude_seconds"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:boundaryPosition.nxce:namedFix").alias("fix"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:reportedAltitude.nxce:assignedAltitude.nxce:simpleAltitude").alias("altitude"),
        df.col("fdm:boundaryCrossingUpdate.nxcm:routeOfFlight._legacyFormat").alias("legacyFlightPlan")
    ).filter("_msgType = 'boundaryCrossingUpdate'");

    savePath = "boundaryCrossingUpdate";
    saveToCsv(boundaryCrossingUpdate, savePath);
  }

  private static void extractDepartureInformationMessages(Dataset<Row> df) {
    String savePath;
    Dataset<Row>
    departureInformation = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:departureInformation.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:departureInformation.nxcm:flightAircraftSpecs._VALUE").alias("acType"),
        df.col("fdm:departureInformation.nxcm:flightAircraftSpecs._specialAircraftQualifier").alias("specialAircraftQualifier"),
        df.col("fdm:departureInformation.nxcm:flightAircraftSpecs._equipmentQualifier").alias("equippage"),
        df.col("fdm:departureInformation.nxcm:ncsmFlightTimeData.nxcm:eta._timeValue").alias("arrivalTime"),
        df.col("fdm:departureInformation.nxcm:ncsmFlightTimeData.nxcm:eta._etaType").alias("arrivalTimeEstimated"),
        df.col("fdm:departureInformation.nxcm:ncsmFlightTimeData.nxcm:etd._timeValue").alias("departureTime"),
        df.col("fdm:departureInformation.nxcm:ncsmFlightTimeData.nxcm:etd._etdType").alias("departureTimeEstimated")
    ).filter("_msgType = 'departureInformation'");

    savePath = "departureInformation";
    saveToCsv(departureInformation, savePath);
  }

  private static void extractArrivalInformationMessages(Dataset<Row> df) {
    String savePath;
    Dataset<Row> arrivalInformation = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:arrivalInformation.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:arrivalInformation.nxcm:timeOfArrival._VALUE").alias("arrivalTime"),
        df.col("fdm:arrivalInformation.nxcm:timeOfArrival._estimated").alias("arrivalTimeEstimated")
    ).filter("_msgType = 'arrivalInformation'");

    savePath = "arrivalInformation";
    saveToCsv(arrivalInformation, savePath);
  }

  private static void extractTrackInformationMessages(Dataset<Row> df) {
    Dataset<Row>
        trackInformation = df.select(
        df.col("_sourceTimeStamp").alias("timeStamp"),
        df.col("_sourceFacility").alias("facility"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:aircraftId").alias("acid"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:facilityIdentifier").alias("fid"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:computerId.nxce:idNumber").alias("cid"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:gufi").alias("gufi"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:igtd").alias("igtd"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:departurePoint.nxce:airport").alias("departurePoint"),
        df.col("fdm:trackInformation.nxcm:qualifiedAircraftId.nxce:arrivalPoint.nxce:airport").alias("arrivalPoint"),
        df.col("fdm:trackInformation.nxcm:speed").alias("speed"),
        df.col("fdm:trackInformation.nxcm:reportedAltitude.nxce:assignedAltitude.nxce:simpleAltitude")
            .alias("simpleAltitude"),
        df.col("fdm:trackInformation.nxcm:position.nxce:latitude.nxce:latitudeDMS._degrees").alias("latitude_degrees"),
        df.col("fdm:trackInformation.nxcm:position.nxce:latitude.nxce:latitudeDMS._direction")
            .alias("latitude_direction"),
        df.col("fdm:trackInformation.nxcm:position.nxce:latitude.nxce:latitudeDMS._minutes").alias("latitude_minutes"),
        df.col("fdm:trackInformation.nxcm:position.nxce:latitude.nxce:latitudeDMS._seconds").alias("latitude_seconds"),
        df.col("fdm:trackInformation.nxcm:position.nxce:longitude.nxce:longitudeDMS._degrees")
            .alias("longitude_degrees"),
        df.col("fdm:trackInformation.nxcm:position.nxce:longitude.nxce:longitudeDMS._direction")
            .alias("longitude_direction"),
        df.col("fdm:trackInformation.nxcm:position.nxce:longitude.nxce:longitudeDMS._minutes")
            .alias("longitude_minutes"),
        df.col("fdm:trackInformation.nxcm:position.nxce:longitude.nxce:longitudeDMS._seconds")
            .alias("longitude_seconds")
    ).filter("_msgType = 'trackInformation'");

    String savePath = "trackInformation";
    saveToCsv(trackInformation, savePath);
  }

  private static void loadProperties(String pathToPropertiesFile) throws IOException {

    try (FileInputStream parametersFile = new FileInputStream(pathToPropertiesFile)) {
      properties.load(parametersFile);
      hadoopInputURI = properties.getProperty("hadoopInputURI");
      rootSavePath = properties.getProperty("rootSavePath");
      fileNameConstant = properties.getProperty("fileNameConstant", "TFMS_FLOW_");
      if ( hadoopInputURI == null || rootSavePath == null)
        throw new IOException("Properties File is incomplete.");
    }

  }

  private static String createPathFrom(Calendar calendar) {
    return String.format("/%1$tY/%1$tm/%1$td/", calendar);
  }

  private static String createFileNamePatternFrom(Calendar calendar){
    return String.format(fileNameConstant+"%1$tY%1$tm%1$td", calendar);
  }

  private static void saveToCsv(Dataset<Row> data, String pathToSave) {

    Calendar processDateCalendar = Calendar.getInstance();
    //We are Processing data from yesterday so subtract one day from this date.
    processDateCalendar.add(Calendar.DATE, -1);
    String datePath = createPathFrom(processDateCalendar);

    data.write()
        .format("com.databricks.spark.csv")
        .option("header", "false")
        .mode(SaveMode.Overwrite)
        .save(rootSavePath + datePath + pathToSave);

  }

}
