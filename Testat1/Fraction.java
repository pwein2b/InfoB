public class Fraction{
	//1. Konstruktor für ganze zahlen
	// 2. Konstruktor fue Brueche
	int numerator; //zaehler
	int denominator; //nenner
	int gcd = 1;
	
	//Verkettete Konstruktoren
	Fraction(int numerator, int denominator){
		GCD(numerator, denominator);
			
	}

	Fraction(int numerator){
		this(numerator, 1);
		
	}

	//GCD Methode
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
	//Multipliziert eine Fraction mit einem integer
	Fraction multiply(int factor){
		int x = this.numerator * factor;
		return new Fraction(x, this.denominator);
	}
	//Multipliziert eine Fraction mit einer anderen
	Fraction multiply(Fraction factor){
		int x = this.numerator * factor.numerator;
		int y = this.denominator * factor.denominator;
		return new Fraction(x, y);
	}
	//Teilt eine Fraction durch eine Fraction
	Fraction divide(Fraction divisor){
		int x = this.numerator * divisor.denominator;
		int y = this.denominator * divisor.numerator;
		return new Fraction(x,y);
	}

	//Multipliziert eine Liste von Fractions
	Fraction multiply(Fraction ...factors){
		int x = this.numerator;
		int y = this.denominator;
		for(int i = 0;i < factors.length; i++){
			x = x * factors[i].numerator;
			y = y * factors[i].denominator;
		}
		return new Fraction(x, y);
	}

	//overriding the toString method
	//This Method gets automatically called by System.out.print, so the output in the Test class stays the same
	public String toString(){
		return this.numerator + "/" + this.denominator;
	}

}

