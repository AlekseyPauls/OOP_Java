package CalcException;

public class SqrtExc extends CommandExc {
    public SqrtExc() {}

    public String getMsg() {
        return "Error in command Sqrt: negative number";
    }
}
