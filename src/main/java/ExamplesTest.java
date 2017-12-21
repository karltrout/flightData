
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class ExamplesTest {

    private static Logger logger = LoggerFactory.getLogger(ExamplesTest.class);

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        logger.info("Path: "+String.format("/%1$tY/%1$tm/%1$td/", calendar));
        logger.info("FileName: "+ String.format("TFMS_FLOW_%1$tY%1$tm%1$td", calendar));

    }

}

