/**
 * Main class for Minesweeper
 *
 * @author Philipp Weinbrenner
 */

import swingcontrol.Window;

public class Minesweeper {
	public static void main(String[] args) {
		int rows = 10, cols = 10, mines = 8;
		if(args.length != 3) {
			System.out.println("Invocation: Minesweeper <rows> <cols> <mines>");
			System.out.println("Incovrrect number of arguments. Using default values");
		} else {
			rows = Integer.parseInt(args[0]);
			cols = Integer.parseInt(args[1]);
			mines = Integer.parseInt(args[2]);
		}
		new Window(rows, cols, mines);
	}
}
