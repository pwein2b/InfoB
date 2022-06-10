/**
 * Implementation of a Reader
 * getLineNumber() doesn't have to be implemented since it is already implemented in the superclass
 *
 * @author Markus Kuehn
 */


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Searchreader extends LineNumberReader {

    private int numberofocurrences;

    private Pattern searchchars;


    /**
     * Konstruktor fuer unsere Suchfunktion
     * Wir wandeln unsere Regex in ein Objekt der Klasse Pattern um
     *
     * @param read the reader we want to expand
     * @param chars the characters we want to search for
     */
    public Searchreader(Reader read, String chars){
        super(read);
        this.searchchars = Pattern.compile(chars);
        this.numberofocurrences = 0;
    }

    /**
     * This Method reads a line in the given data and matches it with the regex
     *
     * The method readLine of LineNumberReader already excludes termination characters from being
     * returned
     * it also return null if the end of the stream has been reached, so there is no need for an implementation here
     *
     * @return the line that was being read
     * @throws IOException
     */
    public String readLine() throws IOException{
        String line = super.readLine();
        if(line == null){
            System.out.println("No matches found");
        } else{
            Matcher a = this.searchchars.matcher(line);
            while (a.find()){
                numberofocurrences++;
            }
        }
        return line;
    }

    /**
     * Returns the amount of matches of the regex in the file
     *
     * @return the number of ocurrences of the specified regex
     */
    public int getAmountOfMatches(){
        return this.numberofocurrences;
    }
}
