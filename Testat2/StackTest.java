/**
 * Testklasse, welche zum Testen der in Aufgabe 2.2 geforderten Funktionen dient.
 *
 * @author Markus Kuehn
 *
 */

public class StackTest {

    /**
     * Main Methode welche den Copy Constructor von StringStack testet
     *
     * @param args
     */
    public static void main(String[] args){
        StackTest x = new StackTest();
        StringStack a = x.CreateStack();
        StringStack b = x.CopyStack(a);
        System.out.println("Checking if Stacks are equal...");
        for(int i = 4; i > 0; i--){
            x.CheckEquals(a,b);
            System.out.println("Element in original Stack:" +a.pop());
            System.out.println("Element in copied Stack:" + b.pop());
        }
        x.StackRefill(a,b);
        System.out.println("Deleting one Element in copied Stack and checking for equality...");
        b.pop();
        x.CheckEquals(a,b);
        System.out.println("Deleting one Element in original Stack and checking for equality...");
        a.pop();
        if (x.CheckEquals(a,b) == true){
            System.out.println("Equal");
        };
        System.out.println("Deleting Element from Original Stack and checking for equality...");
        a.pop();
        if (x.CheckEquals(a,b) == false){
            System.out.println("Not Equal");
        };
        System.out.println("All Tests have been completed");
    }

    /**
     * Methode um die Stacks nach der Kontrolle wieder aufzufuellen
     * Macht nur im Rahmen des Tests Sinn
     *
     * @param a erster Stack welcher wiederaufgefuellt wird
     * @param b zweiter Stack welcher wiederaufgefuellt wird
     */
    private void StackRefill(StringStack a, StringStack b){
        a.push("a");
        a.push("b");
        a.push("c");
        a.push("d");
        b.push("a");
        b.push("b");
        b.push("c");
        b.push("d");
    }

    /**
     * Methode um einen Stack zu kopieren
     *
     * @param a der StringStack welcher kopiert werden soll
     * @return Eine Kopie eines Stacks
     */
    private StringStack CopyStack(StringStack a) {
        StringStack b = new StringStack(a);
        return b;
    }

    /**
     * Eine Methode um einen TestStack zu erstellen
     *
     * @return einen neuen Stack mit Inhalt a,b,c,d
     */
    private StringStack CreateStack(){
        StringStack a = new StringStack();
        a.push("a");
        a.push("b");
        a.push("c");
        a.push("d");
        return a;
    }

    /**
     * Ueberprueft die StringStacks auf Gleichheit
     *
     * @param a Der erste StringStack
     * @param b Der Zweite StringStack
     * @return
     */
    private boolean CheckEquals(StringStack a, StringStack b){
        if( a.peek() == b.peek()){
            return true;
        } else {
            System.out.println("The first elements in each Stack are not Equal");
            return  false;
        }
    }
}
