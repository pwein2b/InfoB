package antRace;

public class AntRace implements AntFields {

	/**
	 * Main Method for Task 8.2
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		AntField field = new AntField(FIELD4);

		Ant ant = new Ant(field, 2, 4, 1);

		Thread testThread = new Thread(ant);

		testThread.start();

		try {
			testThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(field.toString());
	}
}
