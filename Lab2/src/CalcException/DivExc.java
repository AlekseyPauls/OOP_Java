package CalcException;

import java.util.logging.Logger;

public class DivExc extends CommandExc {
    private static Logger log = Logger.getLogger(DivExc.class.getName());

    public DivExc() {
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in command Div: division by zero";
    }
}
