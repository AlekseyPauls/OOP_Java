package CalcException;

import java.util.logging.Logger;

public class DefineExc extends CommandExc {
    private String info;
    private static Logger log = Logger.getLogger(DefineExc.class.getName());

    public DefineExc() {}

    public DefineExc(String s) {
        info = s;
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in command Define: " + info;
    }
}
