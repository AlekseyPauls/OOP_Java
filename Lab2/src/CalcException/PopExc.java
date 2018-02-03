package CalcException;

import java.util.logging.Logger;

public class PopExc extends CommandExc {
    private static Logger log = Logger.getLogger(PopExc.class.getName());

    public PopExc() {
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in command Pop: stack have no elements";
    }
}
