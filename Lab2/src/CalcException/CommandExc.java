package CalcException;

public class CommandExc extends CalcException {
    public CommandExc() {}

    public String getMsg() {
        return "Error in command";
    }
}
