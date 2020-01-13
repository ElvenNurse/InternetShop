package mate.academy.internetshop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger.debug("Hello from Log4j 2");
        logger.debug("{}", () -> getNumber());

    }

    static int getNumber() {
        return 5;
    }
}
