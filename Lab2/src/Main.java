import java.io.File;

public class Main {
    public static void main(String[] args) {
        File program = new File(args[0]);
        Calculator calc = new Calculator(program);
//        calc.makeConfig();
        calc.calc();
    }
}
