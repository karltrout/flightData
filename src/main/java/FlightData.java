
import org.apache.spark.sql.*;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;

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

    public static void main(String[] args){

        SparkSession spark = SparkSession.builder().getOrCreate();

       // Dataset<Row> unassociatedTTDataSet = spark.read().format("com.databricks.spark.avro")
       //         .load("hdfs://localhost:9000/user/karltrout/TT/03302017/ThreadedTrack_2.3.0-F_UNASSOCIATED_20170330_part-00072.avro");

       // Dataset<Row> flight = unassociatedTTDataSet.where("tt_id='2017033000000980_2.3.0-F'");
       // Dataset<Row> flat_flight = flight
       //         .select(col("tt_id"),explode(col("threaded_track")).alias("tracks"))
       //         .select("tt_id", "tracks.time", "tracks.climb_rate");


        Dataset<Row> smallFlightDShdfs = spark.read().format("com.databricks.spark.avro")
                .load("hdfs://localhost:9000/user/karltrout/TF/03302017/ThreadedFlight_2.3.0-F_SMALL_20170330_part-00072.avro");

        DataFrameNaFunctions dataFrameNaFunctions = new DataFrameNaFunctions(smallFlightDShdfs);

        String[] nonNullColumns = {"threaded_metadata.departure_airport","threaded_metadata.aircraft_type"};
        Dataset<Row> smallFlightDS = dataFrameNaFunctions.drop(nonNullColumns);

        Dataset<Row> smallTrackDS = spark.read().format("com.databricks.spark.avro")
                .load("hdfs://localhost:9000/user/karltrout/TT/03302017/ThreadedTrack_2.3.0-F_SMALL_20170330_part-00072.avro");

        Dataset<Row> allFlightColumns = smallFlightDS.join(smallTrackDS, "tt_id");

        Dataset<Row> flightData = allFlightColumns.select(col("threaded_track"),col("threaded_metadata.aircraft_id").alias("acId"), col("threaded_metadata.aircraft_type").alias("acType"), col("threaded_metadata.departure_airport").alias("depArpt"));
        Dataset<Row> AcTypeAndTrack = flightData.select(col("acId"),col( "acType"),col( "depArpt"), explode(col("threaded_track")).alias("tracks"))
                .select("acId","acType","depArpt", "tracks.time", "tracks.climb_rate", "tracks.latitude", "tracks.longitude", "tracks.pressure_altitude");

        /*
            >>> tfSmalldf = spark.read.format("com.databricks.spark.avro").load("hdfs://localhost:9000/user/karltrout/TF/03302017/ThreadedFlight_2.3.0-F_SMALL_20170330_part-00072.avro")
            >>> smallTfDf= spark.read.format("com.databricks.spark.avro").load("hdfs://localhost:9000/user/karltrout/TT/03302017/ThreadedTrack_2.3.0-F_SMALL_20170330_part-00072.avro")
            >>> cleanSmallDf=tfSmalldf.dropna(how='any',subset=["threaded_metadata.departure_airport","threaded_metadata.aircraft_type"])
            >>> cleanSmallTfDf = smallTfDf.join(cleanSmallDf, smallTfDf.tt_id == cleanSmallDf.tt_id)

            >>> smallTable = cleanSmallTfDf.select(smallTfDf.tt_id, smallTfDf.threaded_track,cleanSmallDf.threaded_metadata['aircraft_id'].alias("acId"), cleanSmallDf.threaded_metadata['aircraft_type'].alias("acType"), cleanSmallDf.threaded_metadata['departure_airport'].alias("depArpt"))
            >>> AcTypeAndTrack = smallTable.select("acId", "acType", "depArpt", explode("threaded_track"))
            >>> climbRates=AcTypeAndTrack.select("acId", "acType", "depArpt",AcTypeAndTrack["col.time"],AcTypeAndTrack["col.latitude"],AcTypeAndTrack["col.longitude"],AcTypeAndTrack["col.pressure_altitude"],AcTypeAndTrack["col.climb_rate"])
            >>> climbRates.where("acid == 'BISON03'").collect()
         */

        String savePath = "/tmp/AcTypeAndTrack.csv";

        saveToCsv(AcTypeAndTrack, savePath);

    }

    private static void saveToCsv(Dataset<Row> data, String pathToSave){
        data.coalesce(10).write()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .mode(SaveMode.Overwrite)
                .save(pathToSave);
    }

}
