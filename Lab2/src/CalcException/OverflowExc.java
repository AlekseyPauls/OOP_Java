package CalcException;

import java.util.logging.Logger;

public class OverflowExc extends CommandExc {
    private String comName;
    private static Logger log = Logger.getLogger(OverflowExc.class.getName());

    public OverflowExc() {}

    public OverflowExc(String name) {
        comName = name;
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in arithmetical command '" + comName + "': float number is overflow";
    }
}
