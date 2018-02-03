package CalcException;

import java.util.logging.Logger;

public class ParsingProgExc extends CalcException {
    private String info;
    private static Logger log = Logger.getLogger(ParsingProgExc.class.getName());

    public ParsingProgExc() {}

    public ParsingProgExc(String s) {
        info = s;
        log.info(getMsg());
    }

    public String getMsg() {
        return "Error in parsing program: " + info;
    }
}
