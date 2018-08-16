#!/bin/bash 
clear
vim flightdata.properties 
/usr/hdp/current/spark2-client/bin/spark-submit --class FlightData --master yarn --executor-memory 4g --driver-memory 4g --num-executors 8 --executor-cores 8 --jars ./spark-avro.jar,./spark-csv_2.11-1.0.1.jar,./spark-sql_2.11-2.1.1.jar ./flightData-1.0-SNAPSHOT.jar -p ./flightdata.properties
hdfs dfs -ls /data/flightData/output/2017/06/26/PHX/output_data.csv
hdfs dfs -text /data/flightData/output/2017/06/26/PHX/output_data.csv/*.csv >> Phx062617FlightData.csv
ls -ltr Phx062*
