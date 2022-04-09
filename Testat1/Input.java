/**
 * Eine Klasse, welche eine Zahl einliest und dann von 1 aufwaerts bis zur eingelesenen Zahl Hochzaehlt
 *
 * @author Markus Kuehn
 * @version 2022-08-04
 */
public class Input{
    public static void main(String[] args){

        checkInput(args);
        int x = Integer.parseInt(args[0]);
        for(int i = 1; i <= x; i++){
            System.out.println(i);
        }
    }

    /**
     *
     * @param input der zu ueberpruefende string
     * @return true wenn der string den in 1.2 formulierten anforderungen entspricht
     */
    public static boolean checkInput(String[] input){
        if(input.length < 1){
            System.out.println("Es muss exakt ein Argument uebergeben werden");
            System.exit(1);
        }
        else if(input.length > 1){
            System.out.println("Es darf nicht mehr als ein Argument uebergeben werden");
            System.exit(1);
        }
        int x = Integer.parseInt(input[0]);
        if(x <= 0){
            System.out.println("Die eingegebene Zahl muss groeßer als 0 sein");
            System.exit(1);
        }
        return true;
    }
}