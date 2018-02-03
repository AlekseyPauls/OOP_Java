import CalcException.BadConfigExc;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommandFactory {
    private HashMap<String, String> config;
    private static Logger log = Logger.getLogger(CommandFactory.class.getName());

    CommandFactory() {
        config = new HashMap<String, String>();
        log.info("CommandFactory initializing");
        getConfig(new File("config.csv"));
    }

    public Calculator.Command getCommand(String name) {
        Object com = new Object();
        try {
            Class cl = Class.forName(config.get(name));
            try {
                com = cl.newInstance();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            Calculator.Command c;
            c = (Calculator.Command)com;
            return c;
        } catch(ClassNotFoundException e) {
            throw new BadConfigExc("can not find class from config");
        }
    }

    private void getConfig(File conf) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(conf)));
            String strLine;
            strLine = reader.readLine();
            if (!strLine.equals("CommandName,ClassName")){
                throw new BadConfigExc("wrong config format");
            }
            while ((strLine = reader.readLine()) != null){
                String[] words = strLine.split(",");
                if (words.length != 2){
                    throw new BadConfigExc("wrong config format");
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
            log.info("CommandFactory object was successfully created");
        }
    }
}
