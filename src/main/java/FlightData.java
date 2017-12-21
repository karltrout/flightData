
import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.LongType;
import static org.apache.spark.sql.types.DataTypes.StringType;

import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrameNaFunctions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import scala.collection.mutable.WrappedArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

/**
 * just seeing what we can do...
 * Created by karltrout on 7/9/17.
 * to run :
    > cd  /Users/karltrout/Documents/Development/java/flightData/target
    > /usr/local/spark/bin/spark-submit --class FlightData --master local[4] --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar
 on YARN:
    > $SPARK_HOME/bin/spark-submit --class FlightData --master yarn --num-executors 4 --executor-cores 1 --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar -p /data/java/flightData/src/main/resources/flightdata.properties

 */
public class FlightData {

  private static Logger logger = Logger.getLogger(FlightData.class);
  private static String outputType;
  private static String rootSavePath;
  private static String airport;
  private static int inMONTH;
  private static int inYEAR;
  private static int inDAY;
  private static String threadedTrack;
  private static String threadedFlight;
  private static String TT_FILE_NAME;
  private static String TF_FILE_NAME;
  private static Boolean LOCAL = false;

  /***
   * @param args as of now the Properties .config file path. -p /path/to/properties/file.
   */
  public static void main(String[] args) {

    if (args.length < 2 || args[0].compareToIgnoreCase("-p") != 0) {
      logger.info("given args : " + args[0] + " : " + args[1]);
      logger.error("No properties file supplied.");
      logger.info("Usage: FlightData -p /path/to/parameters.file.");
      System.exit(0);
    }

    String propertiesFilePath = args[1];
    assignFields(propertiesFilePath);

    SparkSession spark = SparkSession.builder().getOrCreate();
    init(spark);

    Calendar processDateCalendar = Calendar.getInstance();
    processDateCalendar.set(inYEAR, inMONTH, inDAY);
    String datePath = String.format("%1$tY/%1$tm/%1$td/", processDateCalendar);
    logger.info("The processing date is : " + datePath);

    String tfFile = threadedFlight + datePath + TF_FILE_NAME;
    logger.debug("###>>> THE PROD TF FILE PATH: " + tfFile);

    if( LOCAL ) {
      tfFile =
          "hdfs://localhost:9000/user/karltrout/DATA/03302017/TF/ThreadedFlight_2.3.0-F_ASSOCIATED_20170330_part-00072.avro";
    }

    Dataset<Row> threadedFlightDataSet = spark.read().format("com.databricks.spark.avro").load(tfFile);
    DataFrameNaFunctions dataFrameNaFunctions = new DataFrameNaFunctions(threadedFlightDataSet);
    String[] nonNullColumns = {"threaded_metadata.departure_airport", "threaded_metadata.aircraft_type"};
    Dataset<Row> threadedFlightDS = dataFrameNaFunctions.drop(nonNullColumns);

    String ttFile = threadedTrack + datePath + TT_FILE_NAME;
    logger.debug("###>>> THE PROD TT FILE PATH: " + ttFile);

    if( LOCAL ) {
      ttFile =
          "hdfs://localhost:9000/user/karltrout/DATA/03302017/TT/ThreadedTrack_2.3.0-F_ASSOCIATED_20170330_part-00072.avro";
    }

    Dataset<Row> threadedTrackDataSet = spark.read().format("com.databricks.spark.avro").load(ttFile);
    Dataset<Row> allFlightColumns = threadedFlightDS.join(threadedTrackDataSet, "tt_id");

    Dataset<Row>
        flightData =
        allFlightColumns.select(col("tt_id"),
                                col("threaded_track"),
                                col("threaded_metadata.aircraft_id").alias("acid"),
                                col("threaded_metadata.aircraft_type").alias("acType"),
                                col("threaded_metadata.departure_airport").alias("depArpt"))
            .filter("threaded_metadata.departure_airport like '%" + airport + "%'");

    Dataset<Row> reducedFlightData = flightData
        .withColumn("Altitudes", callUDF("udfReduceHits", col("threaded_track")))
        .select(
            col("tt_id"),
            col("acType"),
            col("acid"),
            col("depArpt"),
            col("Altitudes"),
            explode(col("Altitudes"))
        );

    Dataset<Row> data = reducedFlightData.select(col("tt_id"),
                                                 col("acid"),
                                                 col("acType"),
                                                 col("depArpt"),
                                                 col("col.time"),
                                                 col("col.climb_rate"),
                                                 col("col.latitude"),
                                                 col("col.longitude"),
                                                 col("col.pressure_altitude"));

    if (outputType.equalsIgnoreCase("parquet")) {
      String saveParPath = rootSavePath + datePath + airport + "/output_data.parquet";
      saveToParquet(reducedFlightData, saveParPath);
    } else {
      String saveCsvPath = rootSavePath + datePath + airport + "/output_data.csv";
      saveToCsv(data, saveCsvPath);
    }
  }

  /**
   * Reads in the Properties file and sets the necessary fields.
   *
   * @param propertiesFilePath String path to the file.
   */
  private static void assignFields(String propertiesFilePath) {
    try {
      Properties properties = Helper.loadProperties(propertiesFilePath);

      rootSavePath    = properties.getProperty("rootSavePath");
      outputType      = properties.getProperty("outputType");
      airport         = properties.getProperty("airport");
      inMONTH         = Integer.parseInt(properties.getProperty("inMONTH"));
      inYEAR          = Integer.parseInt(properties.getProperty("inYEAR"));
      inDAY           = Integer.parseInt(properties.getProperty("inDAY"));
      threadedTrack   = properties.getProperty("threadedTrack");
      threadedFlight  = properties.getProperty("threadedFlight");
      TT_FILE_NAME    = properties.getProperty("TT_FILE_NAME");
      TF_FILE_NAME    = properties.getProperty("TF_FILE_NAME");
      LOCAL           = Boolean.parseBoolean(properties.getProperty("LOCAL"));
    }
    catch (IOException exception) {
      logger.error(exception.getMessage());
      logger.error("Properties file " + propertiesFilePath + " does not exist or is not configured properly.");
      logger.info("Usage: FlightData -p /path/to/parameters.file.");
      System.exit(0);
    }
  }

  /**
   * Init the Spark data structures and load User Defined Functions.
   * @param spark the current Spark Session
   */
  private static void init(SparkSession spark) {

      spark.udf().register("udfMaxAlt", (Dataset<Row> data) -> max(col("pressure_alt")), DataTypes.DoubleType);

      StructType struct = new StructType();
      struct = struct.add("time", LongType).add("latitude", DoubleType).add("longitude", DoubleType).add("pressure_altitude", DoubleType)
          .add("along_track_distance", DoubleType)
          .add("ground_speed", DoubleType)
          .add("track_heading", DoubleType)
          .add("track_curvature", DoubleType)
          .add("ground_acceleration", DoubleType)
          .add("climb_rate", DoubleType)
          .add("cross_track_residual", DoubleType)
          .add("along_track_residual", DoubleType)
          .add("vertical_track_residual", DoubleType)
          .add("cross_track_bias", DoubleType)
          .add("along_track_bias", DoubleType)
          .add("vertical_track_bias", DoubleType)
          .add("active_sensors", DataTypes.createArrayType(StringType));
      ArrayType climb = DataTypes.createArrayType(struct, true);


      spark.udf().register(
          "udfReduceHits", (UDF1<WrappedArray, Object>) FlightData::collectAndMinimizeClimbData, climb);

    }

  /**
   *  a UDF (User Defined Function) for spark which is used to adapt or modify data in Spark.
   *  The idea here is to take all of the track hits for a flight and process them into just the change points
   *  necessary to represent the accession rate.
   *  Each point is analysed and compared to its past values and vertical direction.
   *  it determines if we are ascending, leveling or descending. if there is a change, the point is saved as well as the last know
   *  point before the change. this allows us to represent the change as a bezier curve for the change.
   *
   * @param wrappedArray The Data array to process. ( I know, this doesn't add value! ).
   * @return  processed array.
   */
  private static WrappedArray collectAndMinimizeClimbData(WrappedArray wrappedArray ) {

      boolean isClimbing = false;
      int levelCnt = 0;
      int cruiseCnt = 0;
      int maxCruiseCnt = 20;
      int accentStartInd = 0;

      //Starting record is in the 5th spot we will look back 5 records to get an indication of our direction
      ArrayList<Row> rowsArray =  new ArrayList<>();
      for (int recIdx = 5; recIdx < wrappedArray.size() -5  ; recIdx++) {

        Row row     = (Row) wrappedArray.apply(recIdx);
        Row preRow  = (Row) wrappedArray.apply(recIdx - 5);
        Row postRow = (Row) wrappedArray.apply(recIdx + 5);

        if (row.isNullAt(3) || preRow.isNullAt(3) || postRow.isNullAt( 3 ) )
            continue;

        Double curAlt = row.getDouble(3);

        Double preAlt = (curAlt - (Double)((Row) wrappedArray.apply(recIdx - 5)).getDouble(3));
        Double nextAlt = (Double) ((Row) wrappedArray.apply(recIdx + 5)).getDouble(3) - curAlt;

        //Ascending Check
        if (nextAlt > 0.0 && nextAlt >= (preAlt * 0.65)) {

          if (levelCnt > 0) {
            rowsArray.add(((Row) wrappedArray.apply(recIdx - levelCnt)));
            rowsArray.add(((Row) wrappedArray.apply(recIdx)));
            levelCnt = 0;
          }

          if (accentStartInd == 0) {
            accentStartInd = recIdx;
          }
          cruiseCnt = 0;
          isClimbing = true;

        }
        //Descending Check
        else if (nextAlt < 0.0 && nextAlt < (-1 * curAlt * .0125)) {

          if (levelCnt > 0) {
            rowsArray.add(((Row) wrappedArray.apply(recIdx - levelCnt)));
            rowsArray.add(((Row) wrappedArray.apply(recIdx)));
            levelCnt = 0;
          }

          accentStartInd = 0;
          cruiseCnt = 0;

        }

        //Leveling Check
        else if (nextAlt > 0.0 && nextAlt < (preAlt * .65)) {

          if (levelCnt == 0 && accentStartInd != 0) {
            int oneThird = (recIdx - accentStartInd) / 3;
            int twoThirds = (recIdx - accentStartInd) * 2 / 3;
            rowsArray.add(((Row) wrappedArray.apply(recIdx - twoThirds)));
            rowsArray.add(((Row) wrappedArray.apply(recIdx - oneThird)));
            accentStartInd = 0;
          }

          levelCnt += 1;
          cruiseCnt = 0;

        }

        else {

          if (!isClimbing)
            continue;

          if (levelCnt > 0) {
            rowsArray.add(((Row) wrappedArray.apply(recIdx - levelCnt)));
            rowsArray.add(((Row) wrappedArray.apply(recIdx)));
            levelCnt = 0;
          }

          cruiseCnt += 1;
          if ( cruiseCnt > maxCruiseCnt ) {
            rowsArray.add(((Row) wrappedArray.apply(recIdx - maxCruiseCnt - 1)));
            break;
          }

        }
      }

      return  WrappedArray.make(rowsArray.toArray(new Row[rowsArray.size()]));

    }

    private static void saveToCsv(Dataset<Row> data, String pathToSave){
        data.coalesce(10).write()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .mode(SaveMode.Overwrite)
                .save(pathToSave);
    }

  private static void saveToParquet(Dataset<Row> data, String pathToSave){
    data.coalesce(10).write().mode(SaveMode.Overwrite).parquet(pathToSave);
  }

}
