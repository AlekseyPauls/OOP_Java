package CalcException;

import java.util.logging.Logger;

public class PushExc extends CommandExc {
    private String info;
    private static Logger log = Logger.getLogger(PushExc.class.getName());

    public PushExc() {}

    public PushExc(String s) {
        info = s;
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in command Push: " + info;
    }
}
