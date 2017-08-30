
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.*;

import scala.collection.IndexedSeq;
import scala.collection.Iterator;
import scala.collection.mutable.WrappedArray;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.types.DataTypes.*;

import java.sql.Struct;
import java.util.ArrayList;


/**
 * just seeing what we can do...
 * Created by karltrout on 7/9/17.
 * to run :
    > cd  /Users/karltrout/Documents/Development/java/flightData/target
    > /usr/local/spark/bin/spark-submit --class FlightData --master local[4] --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar
 on YARN:
    > /usr/local/spark/bin/spark-submit --class FlightData --master yarn --num-executors 3 --executor-cores 1 --jars ./*.jar ./flightData-1.0-SNAPSHOT.jar

 */
public class FlightData {

  static Logger logger = Logger.getLogger(FlightData.class);

  public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().getOrCreate();

        init(spark);

        Dataset<Row> smallFlightDShdfs = spark.read().format("com.databricks.spark.avro")
            .load(
                "hdfs://localhost:9000/user/karltrout/DATA/03302017/TF/ThreadedFlight_2.3.0-F_ASSOCIATED_20170330_part-00072.avro");

        DataFrameNaFunctions dataFrameNaFunctions = new DataFrameNaFunctions(smallFlightDShdfs);

        String[] nonNullColumns = {"threaded_metadata.departure_airport", "threaded_metadata.aircraft_type"};
        Dataset<Row> smallFlightDS = dataFrameNaFunctions.drop(nonNullColumns);

        Dataset<Row> smallTrackDS = spark.read().format("com.databricks.spark.avro")
            .load(
                "hdfs://localhost:9000/user/karltrout/DATA/03302017/TT/ThreadedTrack_2.3.0-F_ASSOCIATED_20170330_part-00072.avro");

        Dataset<Row> allFlightColumns = smallFlightDS.join(smallTrackDS, "tt_id"); //.filter("threaded_metadata.aircraft_id like 'AAL424%'");

        Dataset<Row>
            flightData =
            allFlightColumns.select(col("tt_id"),
                                    col("threaded_track"),
                                    col("threaded_metadata.aircraft_id").alias("acid"),
                                    col("threaded_metadata.aircraft_type").alias("acType"),
                                    col("threaded_metadata.departure_airport").alias("depArpt"))
                .filter("threaded_metadata.departure_airport = 'PHX'").filter("threaded_metadata.aircraft_type = 'A320'");

       /*Dataset<Row>
            AcTypeAndTrack =
            flightData
                .select(col("acId"), col("acType"), col("depArpt"), explode(col("threaded_track")).alias("tracks"))
                .select("acId", "acType", "depArpt", "tracks.time", "tracks.climb_rate", "tracks.latitude",
                        "tracks.longitude", "tracks.pressure_altitude");
                        */

      logger.info("STRUCT:::"+col("threaded_track").toString());

      Dataset<Row> reducedFlightData = flightData
          //.withColumn("Max", callUDF("udfMaxAlt",col("threaded_track")))
          .withColumn("Altitudes", callUDF("udfReduceHits", col("threaded_track")))
          .select(
              col("tt_id"),
              col("acType"),
              col("acid"),
              col("depArpt"),
              col("Altitudes")
             // col("Max")
             // explode(col("Altitudes"))
          );


      logger.info(reducedFlightData.schema().toString());

     /* Dataset<Row> data = reducedFlightData.select(col("acid"),
                                                   col("acType"),
                                                   col("depArpt"),
                                                   col("col.time"),
                                                   col("col.climb_rate"),
                                                   col("col.latitude"),
                                                   col("col.longitude"),
                                                   col("col.pressure_altitude"));
*/
        String savePath = "/java_output/KPHX/output_data.csv";

      saveToParquet(reducedFlightData, savePath);

    }

    private static void init(SparkSession spark) {

      //spark.udf().register("udfMaxAlt", (Dataset<Row> data) -> max(col("pressure_alt")), DataTypes.DoubleType);

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


      spark.udf().register("udfReduceHits", ( WrappedArray data ) -> collectAndMinimizeClimbData(data), climb);

    }


    private static WrappedArray collectAndMinimizeClimbData(WrappedArray wrappedArray ) {

        boolean isClimbing = false;
        int levelCnt = 0;
        int cruiseCnt = 0;
        int maxCruiseCnt = 20;
        int accentStartInd = 0;
       // int recIdx = 5;
        int maxAlt = 0;
        //Starting record is in the 5th spot we will look back 5 records to get an indication of our direction
        ArrayList<Row> rowsArray =  new ArrayList<>();
        for (int recIdx = 5; recIdx < wrappedArray.size() -5  ; recIdx++) {

          Row row =(Row) wrappedArray.apply(recIdx);
          Row preRow =(Row) wrappedArray.apply(recIdx - 5);
          Row postRow =(Row) wrappedArray.apply(recIdx + 5);

          if (row.isNullAt(3) || preRow.isNullAt(3) || postRow.isNullAt( 3 ) )
              continue;

          Double curAlt = row.getDouble(3);

          Double preAlt = (curAlt - (Double)((Row) wrappedArray.apply(recIdx - 5)).getDouble(3));
          Double nextAlt = (Double) ((Row) wrappedArray.apply(recIdx + 5)).getDouble(3) - curAlt;

          //Assending Check
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
              int oneThird = (int) ((recIdx - accentStartInd) * 1 / 3);
              int twoThirds = (int) ((recIdx - accentStartInd) * 2 / 3);
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
            if (isClimbing && cruiseCnt > maxCruiseCnt) {
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
