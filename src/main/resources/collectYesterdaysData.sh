#!/bin/bash
export SPARK_HOME=/usr/hdp/current/spark2-client/

D=$(date --date="yesterday")  
DAY=$(date -d "$D" '+%-d')
MONTH=$(date -d "$D" '+%-m')
YEAR=$(date -d "$D" '+%Y')

cd /home/tgfuser/flightData/CSVOutput
mkdir -p $YEAR/$MONTH/$DAY/
cd $YEAR/$MONTH/$DAY/

echo " Processing data for $D"
echo " Current Directory $(pwd)"
echo " Collecting data into  Hadoop CSV Directory for yesterdays Data."

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/trackInformation/_SUCCESS
if [ $? == 0 ]
then
        echo " Collecting trackInformation.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/trackInformation/*.csv >> trackInformation.csv
fi

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/arrivalInformation/_SUCCESS 
if [ $? == 0 ]
then
        echo " Collecting arrivalInformation.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/arrivalInformation/*.csv >> arrivalInformation.csv
fi

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/boundaryCrossingUpdate/_SUCCESS
if [ $? == 0 ]
then
        echo " Collecting boundaryCrossingUpdate.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/boundaryCrossingUpdate/*.csv >> boundaryCrossingUpdate.csv
fi

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/departureInformation/_SUCCESS
if [ $? == 0 ]
then
        echo " Collecting departureInformation.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/departureInformation/*.csv >> departureInformation.csv
fi

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanAmendmentInformation/_SUCCESS
if [ $? == 0 ]
then
        echo " Collecting flightPlanAmendmentInformation.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanAmendmentInformation/*.csv >> flightPlanAmendmentInformation.csv
fi

hdfs dfs -test -e /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanInformation/_SUCCESS
if [ $? == 0 ]
then
        echo " Collecting flightPlanInformation.csv files"
        /usr/bin/time -f "Elapsed Time = %E " hdfs dfs -text /data/faa/tfmsFlight/output/$YEAR/$MONTH/$DAY/flightPlanInformation/*.csv >> flightPlanInformation.csv
fi

echo " Creating Tar file for all CSV Files."

if [ -e tgfFlowData.tar.gz ]
then
	rm tgfFlowData.tar.gz
fi

tar -cvzf tgfFlowData.tar.gz *.csv
rm *.csv

echo "Process Complete."
/usr/sbin/sendmail karl.ctr.trout@faa.gov < /home/tgfuser/CYD_Cron.log
