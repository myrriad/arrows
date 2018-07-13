package phlaxyr.largetents;

import org.apache.logging.log4j.Logger;

public class Lumber {
    public static Logger jack;
    static void registerLogger(Logger l) {
    	jack = l;
    }
}
