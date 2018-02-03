package CalcException;

import java.util.logging.Logger;

public class StackExc extends CommandExc {
    private static Logger log = Logger.getLogger(StackExc.class.getName());

    public StackExc() {
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error: stack have no needed count of numbers to execute command or stack is empty";
    }
}
