/**
 * Every instance of <code>Fraction</code> represents a fraction with numerator
 * and denominator.
 *
 * @author Lars Huning, Philipp Weinbrenner
 * @version 2022-05-06
 */

import java.util.regex.Matcher;

public class Fraction {

   /**
    * Creates greatest common divisor for a and b.
    *
    * @param a
    * @param b
    * @return the greatest common divisor for a and b.
    */
   public static int gcd(int a, int b) {
      return b == 0 ? a : gcd(b, a % b);
   }
   
   /**
    * Numerator of the Fraction
    */
   private final int numerator;
   
   /**
    * Denominator of the Fraction
    */
   private final int denominator;

   /**
    * Creates a Fraction object with numerator and denominator 1, reduces the
    * fraction with creation.
    *
    * @param numerator
    */
   public Fraction(int numerator) {
      this(numerator, 1);
   }

   /**
    * Creates a Fraction object with numerator and denominator. Reduces the 
    * fraction in the constructor. 
    * Denominator == 0 is not allowed. In such a case, the program terminates
    * with an error message
    *
    * @param numerator   the numerator
    * @param denominator the denominator, must not be 0.
    */
   public Fraction(int numerator, int denominator) {
      if (denominator == 0) {
          System.out.println("denominator == 0 is not possible");
          System.out.println("Terminating program");
          System.exit(-1);
      }

      /*
       * creates greatest common divisior.
       */
      int gcd = Fraction.gcd(numerator, denominator);
      
      /*
       * check sign, make denominator positive --> the sign of the fraction
       * is always stored only in the numerator. 
       */
      if (denominator / gcd < 0) {
         gcd *= -1;
      }

      this.numerator = numerator / gcd;

      this.denominator = denominator / gcd;
   }

   /**
    * Divides this Fraction with another Fraction. Terminates the program in 
    * case numerator of another is zero (via constructor of the newly created
    * Fraction).
    *
    * @param another Fraction to divide this Fraction by
    * @return Quotient as a new Fraction
    *
    */
   public Fraction divide(Fraction another) {
      return new Fraction(this.numerator * another.denominator,
            this.denominator * another.numerator);
   }

   /**
    * Adds another Fraction to this Fraction.
    *
    * @param addend Another Fraction to add
    * @return Sum as a new Fraction.
    */
   public Fraction add(Fraction addend) {
      return new Fraction(this.numerator * addend.denominator + this.denominator * addend.numerator,
                      this.denominator * addend.denominator);
   }

   /**
    * Substracts another Fraction from this Fraction.
    *
    * @param subtrahend The fraction to be subtracted
    * @return A new Fraction representing the result of the operation.
    */
   public Fraction subtract (Fraction subtrahend) {
      return new Fraction (this.numerator * subtrahend.denominator - this.denominator * subtrahend.numerator,
                      this.denominator * subtrahend.denominator);
   }
   
   /**
    * Note: "Default" getters and setters do not always require JavaDoc, 
    * as their purpose is obvious 
    */
   public int getDenominator() {
      return this.denominator;
   }

   public int getNumerator() {
      return this.numerator;
   }

   /**
    * Multiplies this Fraction with another Fraction
    *
    * @param factor Fraction to multiply this Fraction with
    * @return The product as a new Fraction
    */
   public Fraction multiply(Fraction factor) {
      return new Fraction(this.numerator * factor.numerator, this.denominator
            * factor.denominator);
   }

   /**
    * Multiplies this Fraction with all other Fraction instances given by
    * the parameter factors
    *
    * @param factors Fraction instances to multiply this Fraction with
    * @return The product as a new Fraction
    */
   public Fraction multiply(Fraction... factors) {
      Fraction result = this;
      
      //variable parameters may be treated like an array inside the method
      for (int i = 0; i < factors.length; i++) {
         result = result.multiply(factors[i]);
      }
      return result;
   }

   /**
    * Multiplies this Fraction with int n.
    *
    * @param n factor to multiply with
    * @return The product as a new Fraction
    */
   public Fraction multiply(int n) {
      return new Fraction(this.numerator * n, this.denominator);
   }

   /**
    * Returns a string representation of this Fraction such as
    * numerator/denominator. As the constructor has already made sure that 
    * a negative Fraction is represented by a negative numerator and a positive
    * denominator, negative fractions are always displayed with the minus sign
    * at the numerator (Advantage: consistent notation, for example if anyone
    * decides to parse the results of this method with a string parser)
    *
    * @return This Fraction as a String
    */
   public String toString() {
      return numerator + "/" + denominator;
   }

   /**
    * Parse a string represantation of a fraction.
    * The string representation should be as returned by toString(), meaning that a potential
    * negative sign should be only in front of the numerator, and there should be no spaces.
    *
    * @param string The fraction string to parse
    * @return The new Fraction instance
    * @throws IllegalArgumentException if the string does not contain a valid fraction represantation.
    */
   public static Fraction parseFraction (String string) {
      final String regex = "(-?\\d+)/([1-9]\\d*)";
      if (!string.matches(regex))
         throw new IllegalArgumentException("Ungültige Fraction-Darstellung");

      String[] components = string.split("/");
      int num = Integer.parseInt(components[0]);
      int den = Integer.parseInt(components[1]);
      return new Fraction(num, den);
   }
}
