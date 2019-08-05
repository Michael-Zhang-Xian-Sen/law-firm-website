package nju.software.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.*;

public class log4jTest {
    public static void main(String[] args) {
        File file = new File("src/main/webapp/resources/log4j2/log4j2.xml");
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            final ConfigurationSource source = new ConfigurationSource(in);
            Configurator.initialize(null,source);

            Logger logger = LogManager.getLogger("mylog");
            logger.info("logger init and record...");

            for(int i=0;i<50000;i++){
                logger.trace("trace level");
                logger.debug("debug level");
                logger.info("info level");
                logger.warn("warn level");
                logger.error("error level");
                logger.fatal("fatal level");
            }

            Thread.sleep(1000*61);

            logger.trace("trace level");
            logger.debug("debug level");
            logger.info("info level");
            logger.warn("warn level");
            logger.error("error level");
            logger.fatal("fatal level");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
