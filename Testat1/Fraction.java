/**
 * Eine Klasse, welche Fractions bildet und mit diesen die in aufGabe 1.5 geforderten operationen ausführen kann
 *
 * Diese Klasse Testet automatisch anhand von 3 Vorgefertigten Fractions die in der Klasse Fraction enthaltenden Methoden
 *
 * @author Markus Kuehn
 * @version 2022-08-04
 */
public class Fraction{

    int numerator;
    int denominator;
    int gcd = 1;

    /**
     * Verkettete Konstruktoren fuer die Fractions
     * Wird nur eine zahl angegeben, wird automatisch der verkettete Konstruktor mit einer 1 im Nenner aufgerufen
     *
     * @param numerator der Zaehler der Fraction
     * @param denominator der Nenner der Fraction
     */
    Fraction(int numerator, int denominator){
        GCD(numerator, denominator);

    }

    Fraction(int numerator){
        this(numerator, 1);

    }

    /**
     * Methode um die Fraction auf den größten gemeinsamen Nenner zu bringen
     *
     * @param numerator der Zaehler der Fraction
     * @param denominator der Nenner der Fraction
     */
    void GCD(int numerator,int denominator){
        int maxdiv = 1;
        if (numerator == denominator){
            this.numerator = 1;
            this.denominator = 1;
        }else if(numerator < denominator){
            maxdiv = numerator;
        }else if(numerator > denominator){
            maxdiv = denominator;
        }
        for(int i = maxdiv; i >= 1; i--){
            if(numerator % i == 0 && denominator % i == 0){
                this.gcd = i;
                this.numerator = numerator / i;
                this.denominator = denominator / i;
                break;
            }
        }
    }

    /**
     * Methode zur Multiplikation eines Bruchs mit einer ganzen Zahl
     *
     * @param factor eine ganze Zahl
     * @return Eine neue Fraction
     */
    Fraction multiply(int factor){
        int x = this.numerator * factor;
        return new Fraction(x, this.denominator);
    }

    /**
     * Methode zur Multiplikation eines Bruchs mit einem anderen Bruch
     *
     * @param factor eine Fraction
     * @return Eine neue Fraction
     */
    Fraction multiply(Fraction factor){
        int x = this.numerator * factor.numerator;
        int y = this.denominator * factor.denominator;
        return new Fraction(x, y);
    }

    /**
     * Methode zur division einer Fraction mit einer anderen Fraction
     *
     * @param divisor Eine andere Fraction, welche als divisor verwendet wird
     * @return Eine neue Fraction
     */
    Fraction divide(Fraction divisor){
        int x = this.numerator * divisor.denominator;
        int y = this.denominator * divisor.numerator;
        return new Fraction(x,y);
    }

    /**
     *Methode zur Multiplikation einer Fraction
     *
     * @param factors eine beliebige Anzahl Fractions als Faktoren
     * @return Eine neue Fraction
     */
    Fraction multiply(Fraction ...factors){
        int x = this.numerator;
        int y = this.denominator;
        for(int i = 0;i < factors.length; i++){
            x = x * factors[i].numerator;
            y = y * factors[i].denominator;
        }
        return new Fraction(x, y);
    }

    /**
     * Ueberladen der ToString Methode
     *
     * @return Einen als String dargestellten Bruch
     */
    public String toString(){
        return this.numerator + "/" + this.denominator;
    }

}