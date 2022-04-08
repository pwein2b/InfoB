public class Input{
	public static void main(String[] args){
		int x = Integer.parseInt(args[0]);
		checkInput(args);
		for(int i = 1; i <= x; i++){
			System.out.println(i);
		}
	}
	//Method Checks the input for mistakes
	public static boolean checkInput(String[] input){
		int x = Integer.parseInt(input[0]);
		if(input.length > 1){
			System.out.println("Es darf nicht mehr als ein Argument übergeben werden");	
			System.exit(-1);
		}
		if(x <= 0){
			System.out.println("Die eingegebene Zahl muss größer als 0 sein");
			System.exit(-1);
		}
		return true;
	}
}
