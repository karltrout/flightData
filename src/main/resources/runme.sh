export SPARK_HOME=/usr/hdp/current/spark2-client/
/usr/hdp/current/spark2-client/bin/spark-submit --class TfmsFlowData --master yarn --executor-memory 8g --driver-memory 8g --num-executors 8 --executor-cores 8 --jars ./spark-xml_2.12-0.4.1.jar,./spark-csv_2.11-1.0.1.jar,./spark-sql_2.11-2.1.1.jar ./flightData-1.0-SNAPSHOT.jar --params ./config.properties

/usr/hdp/current/spark2-client/bin/spark-submit --class FlightData --master yarn --executor-memory 8g --driver-memory 8g --num-executors 8 --executor-cores 8 --jars ./spark-avro.jar,./spark-csv_2.11-1.0.1.jar,./spark-sql_2.11-2.1.1.jar ./flightData-1.0-SNAPSHOT.jar -p ./flightdata.properties

rm *.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/trackInformation/*.csv >> trackInformation.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/arrivalInformation/*.csv >> arrivalInformation.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/boundaryCrossingUpdate/*.csv >> boundaryCrossingUpdate.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/departureInformation/*.csv >> departureInformation.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/flightPlanAmendmentInformation/*.csv >> flightPlanAmendmentInformation.csv
hdfs dfs -text /data/tfmsFlow/output/2017/12/13/flightPlanInformation/*.csv >> flightPlanInformation.csv
tar -cvzf tgfFlowData.tar.gz *.csv
