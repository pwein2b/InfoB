/**
 * Task 7.3
 *
 * @author Markus Kuehn
 */

import jdk.jfr.StackTrace;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.AttributedString;
import java.util.InputMismatchException;
import java.util.regex.PatternSyntaxException;

public class Commandlinereader {

    private String regex;

    /**
     * Checking the regex and assigning a new searchreader to the input
     *
     * @param args
     */
    public static void main(String[] args){
        String regex;
        Searchreader b = null;

        if(args.length != 1){
            System.out.println("Es wurde keine gueltige regex eingegeben!");
            throw new RuntimeException();
        }
        regex = args[0];
        

        try {
            try {
                b = new Searchreader(new InputStreamReader(System.in), regex);
            } catch (PatternSyntaxException a) {
                System.out.println("The regex entered is not valid");
            }
            String stri;
            while ((stri = b.readLine()) != null) {
                if (b.getAmountOfMatches() > 0) {
                    System.out.println(b.getAmountOfMatches() + b.getLineNumber() + stri);
                }
            }
        } catch (IOException x){
            
        } finally {
            try {
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
