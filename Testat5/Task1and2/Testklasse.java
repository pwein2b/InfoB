/**
 * Testklasse fuer Aufgabe 1
 *
 * @author Markus Kuehn
 */

package Task1and2;

public class Testklasse {


    /**
     * Main Methode zum Testen der Funktionen von Aufgabe 1
     * @param args
     */
    public static void main(String[] args){
        Testklasse a = new Testklasse();
        System.out.println("Starte Tests");
        a.TestCorrectClone();
        a.TestElementClone();
        System.out.println("Tests Abgeschlossen");
    }

    /**
     * Testet ob beide Klone auf die gleiche Referenz verweisen
     */
    private void TestCorrectClone(){
        MyList<String> a = new MyList<>();
        if(a.clone() == a){
            System.out.println("Der Klon ist kein echter Klon");
        }
    }

    /**
     * Testet ob Elemente mitgeklont werden und ob die beiden Listen sich gegenseitig beeinflussen
     */
    private void TestElementClone(){
        MyList<String> a = new MyList<>();
        a.add("1");
        a.add("2");
        MyList<String> b = a.clone();
        if(a.elem() != b.elem()){
            System.out.println("Fehler beim klonen der Elemente");
        }
        a.delete();
        if(a.elem() == b.elem()){
            System.out.println("Fehler beim löschen, Listen beeinflussen sich");
        }
        b.delete();
        if(a.elem() != b.elem()){
            System.out.println("Fehler beim löschen, Listen beeinflussen sich");
        }
    }
}
