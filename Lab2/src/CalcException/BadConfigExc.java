package CalcException;

import java.util.logging.Logger;

public class BadConfigExc extends CalcException {
    private String info;
    private static Logger log = Logger.getLogger(BadConfigExc.class.getName());

    public BadConfigExc() {}

    public BadConfigExc(String s) {
        info = s;
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in Factory: " + info;
    }
}
