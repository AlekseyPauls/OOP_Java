import CalcException.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;


public class Calculator {

    private static Logger log = Logger.getLogger(Calculator.class.getName());

    private ArrayList<String[]> commandsList;
    private CommandFactory factory;
    private ExecContext context;
    private static final HashMap<String, Integer> commandsNames;
    static {
        commandsNames = new HashMap<String, Integer>() {
            {
                put("PUSH", 1);
                put("POP", 0);
                put("+", 0);
                put("-", 0);
                put("*", 0);
                put("/", 0);
                put("SQRT", 0);
                put("PRINT", 0);
                put("DEFINE", 2);
            }
        };
    }

    public Calculator() {
        commandsList = new ArrayList<String[]>();
        log.info("Initializing Calculator");
        context = new ExecContext();
        factory = new CommandFactory();
        log.info("Calculator object was created");
    }

    public Calculator(File prog) {
        commandsList = new ArrayList<String[]>();
        log.info("Initializing Calculator");
        parseProg(prog);
        context = new ExecContext();
        factory = new CommandFactory();
        log.info("Calculator object was created");
    }

    public void calc() {
        log.info("Start calculating");
        for (String[] command : commandsList) {
            Command executor = factory.getCommand(command[0]);
            if (command.length == 1) {
                executor.exec(context, command);
            } else if (command.length == 2) {
                executor.exec(context, command);
            } else if (command.length == 3) {
                executor.exec(context, command);
            }
        }
        log.info("Calculation was successfully done");
    }

    public void calc(String com) {
        log.info("Start calculating");
        parseCommand(com);
        String[] command = commandsList.get(commandsList.size() - 1);
        Command executor = factory.getCommand(command[0]);
        if (command.length == 1) {
            executor.exec(context, command);
        } else if (command.length == 2) {
            executor.exec(context, command);
        } else if (command.length == 3) {
            executor.exec(context, command);
        }
        log.info("Calculation was successfully done");
    }

    private void parseProg(File prog) {

        FileReader reader = null;
        log.info("Start parsing program");
        try {
            reader = new FileReader(prog);
            Scanner sc = new Scanner(reader);
            String s = "";
            while (sc.hasNextLine()) {
                s = sc.nextLine();
                parseCommand(s);
            }
        } catch (IOException e) {
            log.info("Error in writing file" + e.getLocalizedMessage());
            System.err.println("Error in writing file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    log.info("Can't close reader object at parsing function");
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    private void parseCommand(String s) {
        String[] words = s.split(" ");
        if (commandsNames.containsKey(words[0])) {
            if (commandsNames.get(words[0]) == 0 && words.length == 1) {
                commandsList.add(words);
            } else if (commandsNames.get(words[0]) == 1 && words.length == 2)
            {
                commandsList.add(words);
            } else if (commandsNames.get(words[0]) == 2 && words.length == 3) {
                commandsList.add(words);
            } else {
                throw new ParsingProgExc("wrong count of arguments for command '" + words[0] + "'. " +
                        "This command must contain " + commandsNames.get(words[0]) + " arguments");
            }
        } else {

            throw new ParsingProgExc("Command " + words[0] + " is unacceptable");
        }
    }

    private class ExecContext {
        private Stack<String> vars; // Variables
        private HashMap<String, Float> defs; // Defines
        private float value;

        ExecContext() {
            vars = new Stack<String>();
            defs = new HashMap<String, Float>();
        }

        public float getVar() {
            if (vars.empty()) {
                throw new StackExc();
            }
            float f = defs.get(vars.pop());
            log.info("Value '" + f + "' was gotten from stack");
            return f;
        }

        public void addVar(String var) {
            if(defs.containsKey(var)) {
                log.info("Variable '" + var + "' was added on stack");
                vars.push(var);
            } else {
                throw new PushExc("using undeclared definition");
            }
        }

        public void addVar(float var) {
            defs.put("~" + Float.toString(var), var); // '~' show that definition equal real value
            vars.push("~" + Float.toString(var));
            log.info("Value '" + var + "' was added on stack");
        }

        public void addDef(String def, float var) {
            defs.put(def, var);
            log.info("Define: '" + def + " ->" + var);
        }
    }

    public void printCommandList() {
        System.out.println("Available commands:");
        for (String command : commandsNames.keySet()) {
            System.out.println(command + " (" + commandsNames.get(command) + " arguments)");
        }
        log.info("Command list was printed");
    }

    public void makeConfig() {
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("config.csv"));
            writer.write("CommandName,ClassName\r\n");
            writer.write("PUSH" + "," + Push.class.getName() + "\r\n");
            writer.write("POP" + "," + Pop.class.getName() + "\r\n");
            writer.write("+" + "," + Add.class.getName() + "\r\n");
            writer.write("/" + "," + Div.class.getName() + "\r\n");
            writer.write("*" + "," + Mult.class.getName() + "\r\n");
            writer.write("-" + "," + Sub.class.getName() + "\r\n");
            writer.write("SQRT" + "," + Sqrt.class.getName() + "\r\n");
            writer.write("PRINT" + "," + Print.class.getName() + "\r\n");
            writer.write("DEFINE" + "," + Define.class.getName() + "\r\n");
            /* If new commands will be added, repeat this writing with them */
        } catch (IOException e) {
            System.err.println("Error in writing file: " + e.getLocalizedMessage());
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static abstract class Command {
        abstract void exec(Object obj, String[] args);
    }

    /* Commands list */

    public static class Push extends Command {
        public Push() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            if (isDigit(args[1])) {
                con.addVar(Float.parseFloat(args[1]));
            } else {
                con.addVar(args[1]);
            }
        }
    }

    public static class Pop extends Command {
        public Pop() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.value = con.getVar();
        }
    }

    public static class Add extends Command {
        public Add() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f = con.getVar() + con.getVar();
            if (f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY) {
                throw new OverflowExc("+");
            }
            log.info("Command '+' was done");
            con.addVar(f);
        }
    }

    public static class Sub extends Command {
        public Sub() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f1 = con.getVar();
            float f = con.getVar() - f1;
            if (f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY) {
                throw new OverflowExc("-");
            }
            log.info("Command '-' was done");
            con.addVar(f);
        }
    }

    public static class Mult extends Command {
        public Mult() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f = con.getVar() * con.getVar();
            if (f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY) {
                throw new OverflowExc("*");
            }
            log.info("Command '*' was done");
            con.addVar(f);
        }
    }

    public static class Div extends Command {
        public Div() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f1 = con.getVar();
            float f = con.getVar();
            if (f1 == 0) {
                throw new DivExc();
            }
            f = f/f1;
            if (f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY) {
                throw new OverflowExc("/");
            }
            log.info("Command '/' was done");
            con.addVar(f);
        }
    }

    public static class Sqrt extends Command {
        public Sqrt() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f = con.getVar();
            if (f < 0) {
                throw new SqrtExc();
            }
            log.info("Command 'Sqrt' was done");
            con.addVar((float)Math.sqrt(f));
        }
    }

    public static class Print extends Command {
        public Print() {}
        public void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f = con.getVar();
            System.out.println(f);
            con.addVar(f);
            log.info("Command 'Print' was done");
        }
    }

    public static class Define extends Command {
        public Define() {}
        public void exec (Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            if (isDigit(args[2])) {
                con.addDef(args[1], Float.parseFloat(args[2]));
            } else {
                throw new DefineExc("second argument must be float");
            }
        }
    }
}
