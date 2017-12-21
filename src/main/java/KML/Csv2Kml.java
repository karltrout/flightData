package KML;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.lang.System.exit;

public class Csv2Kml {

    private static final Logger logger = Logger.getLogger(Csv2Kml.class);

    public static void main (String[] args){
        if ( args.length < 4 ){
            logger.error("Error : Csv2Kml -p /path2csvFile -o outputFile.kml");
            exit(0);
        }

        String csvFileStringPath =  args[1];
        String kmlFileName = args[3];
        try {

            File csvFile = new File(csvFileStringPath);
            if (! csvFile.exists()) throw new FileNotFoundException();

            List<String> lines = Files.readAllLines(csvFile.toPath());
            String[] columnNames;
            boolean headers = false;
            for (String line : lines) {
                if (line.startsWith("tt_id") && !headers) // we are a header line;
                {
                    headers = true;
                    columnNames = line.split(",");
                }
                else continue;

            }


        } catch (FileNotFoundException exception){
            logger.error("File "+csvFileStringPath+ " does not exists. exiting.");
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
