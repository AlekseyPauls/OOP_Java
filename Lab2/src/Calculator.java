import com.sun.org.apache.xerces.internal.impl.dv.xs.AbstractDateTimeDV;
import sun.nio.ch.DirectBuffer;

import java.io.*;
import java.util.*;

import static java.lang.Float.isNaN;


public class Calculator {

    private ArrayList<String[]> commandsList;
    private static final HashMap<String, Integer> commandsNames;
    static {
        commandsNames = new HashMap<String, Integer>() {
            {
                put("PUSH", 1);
                put("POP", 0);
                put("ADD", 0);
                put("DIV", 0);
                put("MULT", 0);
                put("SUB", 0);
                put("SQRT", 0);
                put("PRINT", 0);
                put("DEFINE", 2);
            }
        };
    }

    public Calculator(File prog) {
        commandsList = new ArrayList<String[]>();
        parseProg(prog);
    }

    public void calc() {
        ExecContext context;
        context = new ExecContext();
        CommandFactory factory = new CommandFactory();
        for (String[] command : commandsList) {
            CommandFactory.Command executor = factory.getCommand(command[0]);
            if (command.length == 1) {
                executor.exec(context, command);
            } else if (command.length == 2) {
                executor.exec(context, command);
            } else if (command.length == 3) {
                executor.exec(context, command);
            }
        }
    }

    private void parseProg(File prog) {
        FileReader reader = null;
        try {
            reader = new FileReader(prog);
            Scanner sc = new Scanner(reader);
            String s = "";
            while (sc.hasNextLine()) {
                s = sc.nextLine();
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
                        //throw
                    }
                } else {
                    //throw
                }
            }
        } catch (Exception e) {
            System.err.println("Error in writing file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
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
            return defs.get(vars.pop());
        }
        public void addVar(String var) {
            if(defs.containsKey(var)) {
                vars.push(var);
            } else {
                //throw
            }
        }
        public void addVar(float var) {
            defs.put("~" + Float.toString(var), var); // '~' show that definition equal real value
            vars.push("~" + Float.toString(var));
        }
        public void addDef(String def, float var) {
            defs.put(def, var);
        }
    }

    public void makeConfig() {
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("config.csv"));
            writer.write("CommandName,ClassName\r\n");
            writer.write("PUSH" + "," + Push.class.getName() + "\r\n");
            writer.write("POP" + "," + Pop.class.getName() + "\r\n");
            writer.write("ADD" + "," + Add.class.getName() + "\r\n");
            writer.write("DIV" + "," + Div.class.getName() + "\r\n");
            writer.write("MULT" + "," + Mult.class.getName() + "\r\n");
            writer.write("SUB" + "," + Sub.class.getName() + "\r\n");
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

    /* Commands list */

    public static class Push extends CommandFactory.Command {
        public Push() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            if (!isNaN(Float.parseFloat(args[1])) && !Float.isInfinite(Float.parseFloat(args[1]))) {
                con.addVar(Float.parseFloat(args[1]));
            } else {
                con.addVar(args[0]);
            }
        }
    }

    public static class Pop extends CommandFactory.Command {
        public Pop() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.value = con.getVar();
        }
    }

    public static class Add extends CommandFactory.Command {
        public Add() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.addVar(con.getVar() + con.getVar());
        }
    }

    public static class Div extends CommandFactory.Command {
        public Div() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.addVar(con.getVar() - con.getVar());
        }
    }

    public static class Mult extends CommandFactory.Command {
        public Mult() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.addVar(con.getVar() * con.getVar());
        }
    }

    public static class Sub extends CommandFactory.Command {
        public Sub() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.addVar(con.getVar() / con.getVar());
        }
    }

    public static class Sqrt extends CommandFactory.Command {
        public Sqrt() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            con.addVar((float)Math.sqrt(con.getVar()));
        }
    }

    public static class Print extends CommandFactory.Command {
        public Print() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            float f = con.getVar();
            System.out.println(f);
            con.addVar(f);
        }
    }

    public static class Define extends CommandFactory.Command {
        public Define() {}
        void exec(Object obj, String[] args) {
            ExecContext con = (ExecContext)obj;
            if (!isNaN(Float.parseFloat(args[2])) && !Float.isInfinite(Float.parseFloat(args[2]))) {
                con.addDef(args[1], Float.parseFloat(args[2]));
            } else {
                //throw
            }

        }
    }
}


