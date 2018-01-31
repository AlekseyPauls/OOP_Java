import java.io.*;
import java.util.HashMap;

public class CommandFactory {
    private HashMap<String, String> config;

    CommandFactory() {
        config = new HashMap<String, String>();
        getConfig(new File("config.csv"));
    }

    public Command getCommand(String name) {
        Object com = new Object();
        //Command com = new Command();
        try {
            Class cl = Class.forName(config.get(name));
            try {
                com = cl.newInstance();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            Command c;
            c = (Command)com;
            return c;
        } catch(ClassNotFoundException e) {

        } //catch(InstantiationException e) {
//
//        } catch(IllegalAccessException e) {
//
//        }
        Object ob = new Object();
        return (Command)ob;
    }

    private void getConfig(File conf) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(conf)));
            String strLine;
            strLine = reader.readLine();
            if (!strLine.equals("CommandName,ClassName")){
                //throw
            }
            while ((strLine = reader.readLine()) != null){
                String[] words = strLine.split(",");
                if (words.length != 2){
                    //throw
                }
                config.put(words[0], words[1]);
            }

        } catch (Exception e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
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

    static abstract class Command {
        abstract void exec(Object obj, String[] args);
    }
}
