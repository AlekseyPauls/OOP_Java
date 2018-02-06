import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            File fileIn = new File(args[0]);
            File fileOut = new File(args[0].substring(0, args[0].length() - 3) + "csv");
            WordsFrequencyCounter wfc = new WordsFrequencyCounter(fileIn, fileOut);
            wfc.writeInfo();
        } catch (Exception e) {
            System.err.println("Error while opening file: " + e.getLocalizedMessage());
        }

    }
}
