package bg.nbu.java;

import javax.swing.SwingUtilities;

/**
 * The main class of the 
 * @author Vladimir Petkov F74482
 */
public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.showCalculator();
			}
		});
	}
}
