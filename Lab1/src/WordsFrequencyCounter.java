import java.io.*;
import java.util.HashMap;
import java.util.*;
//import java.lang.Character;


public class WordsFrequencyCounter {
    private File fileIn;
    private File fileOut;
    private HashMap<String, Integer> frequency;
    private int wordsNumber;

    WordsFrequencyCounter(File fin, File fout) {
        fileIn = fin;
        fileOut = fout;
    }

    public File writeInfo() {
        this.countFrequency();
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileOut);
            Set<String> words = frequency.keySet();
            writer.write("Word,Frequency,Frequency(in %)\r\n");
            for (String word : words) {
                writer.write(word + "," + frequency.get(word) + "," + (float)frequency.get(word)/wordsNumber*100 + "\r\n");
            }
        } catch (Exception e) {
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
        return fileOut;
    }

    private void countFrequency() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));
            frequency = new HashMap<String, Integer>();
            String strLine;
            wordsNumber = 0;
            while ((strLine = reader.readLine()) != null){
                StringBuilder str = new StringBuilder(strLine);
                int current_begin = 0;
                String word = "";
                for (int i = 0; i <= str.length(); i++) {
                    if (i != str.length() && Character.isLetterOrDigit(str.charAt(i))) {
                        word += str.charAt(i);
                    } else {
                        if(!frequency.containsKey(word)) {
                            frequency.put(word, 1);
                        } else {
                            frequency.put(word, frequency.get(word) + 1);
                        }
                        wordsNumber++;
                        word = "";
                    }
                }

            }
            ArrayList<HashMap.Entry<String,Integer>> sortedEntries = new ArrayList<HashMap.Entry<String,Integer>>(frequency.entrySet());

            Collections.sort(sortedEntries, new MapSort());
            /* LinkedHashMap for non-ordered keyset */
            frequency = new LinkedHashMap<String, Integer>();
            for (HashMap.Entry<String,Integer> i : sortedEntries) {
                frequency.put(i.getKey(),i.getValue());
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

    /* Comparator for collection.sort */
    private class MapSort implements Comparator<HashMap.Entry<String,Integer>> {
        public int compare(HashMap.Entry<String,Integer> e1, HashMap.Entry<String,Integer> e2) {
            return e2.getValue().compareTo(e1.getValue());
        }
    }
}
