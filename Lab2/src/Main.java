import CalcException.CalcException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File program = new File(args[0]);
        try {
            //Calculator calc = new Calculator(program);
            Calculator calc = new Calculator();
            //calc.printCommandList();
            //calc.makeConfig();
            //calc.calc();
            calc.calc("PUSH 1");
            calc.calc("PUSH 2");
            calc.calc("+");
            calc.calc("PRINT");
        } catch (CalcException e) {
            System.out.println(e.getMsg());
        }
    }
}
