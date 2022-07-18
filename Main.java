/**
 * @author Cameron Vandermeersch
 * @version 1.0
 * Date July 1, 2022
 */

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		BrickBreakerGame gamePlay = new BrickBreakerGame();
		JFrame gameObject = new JFrame();
		gameObject.setBounds(10, 10, 700, 750);
		gameObject.setTitle("Welcome to Brick Breaker!");
		gameObject.setVisible(true);
		gameObject.setResizable(false);
		gameObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameObject.add(gamePlay);
		gameObject.setVisible(true);
	}
}
