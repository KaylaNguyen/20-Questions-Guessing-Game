package GuessingGame;

import javax.swing.JFrame;

/**
 * GuessingGameApplication is the main GUI class of the game
 */
public class GuessingGameApplication {
	/**
	 * Main method
	 **/
	public static void main(String[] args) {
		JFrame frame = new JFrame("Guessing Game Unlimited"); // declare a jframe
		frame.getContentPane().add(new GuessingGameView()); // invoke the constructor
		frame.setSize(700, 700); // set size of frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set exit
		frame.setVisible(true); // set visible
	}
}
