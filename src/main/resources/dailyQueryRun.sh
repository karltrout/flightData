#!/bin/bash
export SPARK_HOME=/usr/hdp/current/spark2-client/
hadoopDir='/data/faa/tfmsFlight/input/'
backupDir='/data/faa/tfmsFlight/dailybackup/'

D=$(date --date="yesterday")  
DAY=$(date -d "$D" '+%-d')
MONTH=$(date -d "$D" '+%-m')
YEAR=$(date -d "$D" '+%Y')

clear
echo "Processing data for $D"

cd /home/tgfuser/flightData
if [ -e ./flightData-1.0-SNAPSHOT.jar ] && [ -e ./spark-csv_2.11-1.0.1.jar ] && [ -e ./spark-sql_2.11-2.1.1.jar ] && [ -e ./spark-xml_2.11-0.4.1.jar ] 
then
	echo "Exporting data from Hadoop TFMSFlow into CSV Files."
	/usr/hdp/current/spark2-client/bin/spark-submit --class TfmsFlowData --master yarn --executor-memory 8g --driver-memory 8g --num-executors 8 --executor-cores 8 --jars ./spark-xml_2.11-0.4.1.jar,./spark-csv_2.11-1.0.1.jar,./spark-sql_2.11-2.1.1.jar ./flightData-1.0-SNAPSHOT.jar --params ./config.properties
else
	echo "Files do not exist exiting."
	exit 0;
fi

echo " Testing data Exists in CSV Directory for yesterdays Data."
SUCCESS=0
hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/trackInformation/_SUCCESS
SUCCESS=$(($? + $SUCCESS))

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/arrivalInformation/_SUCCESS 
SUCCESS=$(($? + $SUCCESS))

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/boundaryCrossingUpdate/_SUCCESS
SUCCESS=$(($? + $SUCCESS))

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/departureInformation/_SUCCESS
SUCCESS=$(($? + $SUCCESS))

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanAmendmentInformation/_SUCCESS
SUCCESS=$(($? + $SUCCESS))

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanInformation/_SUCCESS
SUCCESS=$(($? + $SUCCESS))

if [ $SUCCESS == 0 ]
then
	echo "::SUCCESS::" 
	echo "All TGF Files have been created, REMOVING HDFS FILES FROM $haddopDir"
else
	echo "::FAILURE::" 
	echo "THERE WAS AN ISSUE,  ALL FILES WERE NOT PRODUCED."
fi

echo "Moving HDFS Files from $hadoopDir* $backupDir"
D=$(date)  
DAY=$(date -d "$D" '+%d')
MONTH=$(date -d "$D" '+%m')
YEAR=$(date -d "$D" '+%Y')
echo "Files in input directory :"
hdfs dfs -ls $hadoopDir
echo "hdfs dfs -mv $hadoopDir/* $backupDir"
hdfs dfs -mv $hadoopDir/* $backupDir
echo "hdfs dfs -mv $backupDir/TFMS_FLIGHT_$YEAR$MONTH$DAY* $hadoopDir"
hdfs dfs -mv $backupDir/TFMS_FLIGHT_$YEAR$MONTH$DAY* $hadoopDir
hdfs dfs -ls $hadoopDir
echo "Files in input directory for today $YEAR$MONTH$DAY left :"
hdfs dfs -count $hadoopDir

echo "Process Complete."

/usr/sbin/sendmail karl.ctr.trout@faa.gov < /home/tgfuser/DQR_Cron.log
