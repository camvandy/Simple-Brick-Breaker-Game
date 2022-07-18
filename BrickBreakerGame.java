
/**
 * @author Cameron Vandermeersch
 * @version 1.0
 * Date July 1, 2022
 */
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener {
	private GameOutline map;
	private boolean gamePlaySet = false;
	private int player1 = 310;
	private int ballDirectionY = -2;
	private int ballDirectionX = -1;
	private int ballPositionY = 345;
	private int ballPositionX = 125;
	private int totalBricks = 48;
	private int gameScore = 0;
	private Timer timer;
	private int gameDelay = 10;

	public BrickBreakerGame() {
		map = new GameOutline(4, 12);
		timer = new Timer(gameDelay, this);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		timer.start();
	}

	public void paint(Graphics game) {
		// Setting the Background Color.
		game.setColor(Color.black);
		game.fillRect(1, 1, 700, 600);

		// Fill/Draw The Game Map.
		map.drawBoard((Graphics2D) game);

		// Create the Game Borders
		game.setColor(Color.MAGENTA);
		game.fillRect(0, 0, 700, 4);
		game.fillRect(0, 0, 4, 600);
		game.fillRect(697, 0, 4, 600);

		// Game Scores
		game.setColor(Color.white);
		game.setFont(new Font("Open Sans", Font.BOLD, 30));
		game.drawString("" + gameScore, 592, 32);

		// Game Paddle Size and Color.
		game.setColor(Color.blue);
		game.fillRect(player1, 550, 122, 8);

		// Game Ball
		game.setColor(Color.white);
		game.fillOval(ballPositionX, ballPositionY, 20, 20);

		// Game Over Win!
		if (totalBricks <= 0) {
			ballDirectionX = 0;
			ballDirectionY = 0;
			gamePlaySet = false;
			game.setColor(Color.GREEN);
			game.setFont(new Font("Open Sans", Font.BOLD, 30));
			game.drawString("Congratulations! You Won :)", 150, 300);
			game.setColor(Color.GREEN);
			game.setFont(new Font("Open Sans", Font.BOLD, 20));
			game.drawString("Press (Enter) To Restart The Game", 180, 350);
		}
		if (ballPositionY > 570) {
			gamePlaySet = false;
			ballDirectionX = 0;
			ballDirectionY = 0;
			game.setColor(Color.GREEN);
			game.setFont(new Font("Open Sans", Font.BOLD, 30));
			game.drawString("Game Over, Final Scores: " + gameScore, 150, 300);
			game.setColor(Color.GREEN);
			game.setFont(new Font("Open Sans", Font.BOLD, 20));
			game.drawString("Press (Enter) To Restart The Game", 180, 350);
		}
		game.dispose();
	}

	public void keyPressed(KeyEvent move) {
		if (move.getKeyCode() == KeyEvent.VK_LEFT) {
			if (player1 < 12) {
				player1 = 12;
			} else {
				moveLeft();
			}
		}

		if (move.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (player1 >= 602) {
				player1 = 602;
			} else {
				moveRight();
			}
		}

		if (move.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!gamePlaySet) {
				gamePlaySet = true;
				totalBricks = 48;
				player1 = 310;
				gameScore = 0;
				ballDirectionY = -2;
				ballPositionX = 120;
				ballPositionY = 350;
				ballDirectionX = -1;
				map = new GameOutline(4, 12);
				repaint();
			}
		}
	}

	public void moveRight() {
		gamePlaySet = true;
		player1 += 30;
	}

	public void moveLeft() {
		gamePlaySet = true;
		player1 -= 30;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (gamePlaySet) {
			if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(player1, 550, 30, 8))) {
				ballDirectionX = -2;
				ballDirectionY = -ballDirectionY;
			} else if (new Rectangle(ballPositionX, ballPositionY, 20, 20)
					.intersects(new Rectangle(player1 + 30, 550, 40, 8))) {
				ballDirectionY = -ballDirectionY;
			} else if (new Rectangle(ballPositionX, ballPositionY, 20, 20)
					.intersects(new Rectangle(player1 + 70, 550, 30, 8))) {
				ballDirectionX = ballDirectionX + 1;
				ballDirectionY = -ballDirectionY;
			}
			X: for (int c1 = 0; c1 < map.boardMap.length; c1++) {
				for (int c2 = 0; c2 < map.boardMap[0].length; c2++) {
					if (map.boardMap[c1][c2] > 0) {
						int brickWidth = map.boardWidth;
						int brickHeight = map.boardHeight;
						int brickPositionY = c1 * map.boardHeight + 50;
						int brickPositionX = c2 * map.boardWidth + 80;

						Rectangle rectangle = new Rectangle(brickPositionX, brickPositionY, brickWidth, brickHeight);
						Rectangle brickRectangle = rectangle;
						Rectangle ballRectangle = new Rectangle(ballPositionX, ballPositionY, 20, 20);

						if (ballRectangle.intersects(brickRectangle)) {
							map.setBoardValue(0, c1, c2);
							totalBricks--;
							gameScore += 1;
							if (ballPositionX + 19 <= brickRectangle.x
									|| ballPositionX + 1 >= brickRectangle.x + brickRectangle.width) {
								ballDirectionX = -ballDirectionX;
							} else {
								ballDirectionY = -ballDirectionY;
							}
							break X;
						}
					}
				}
			}
			ballPositionY += ballDirectionY;
			ballPositionX += ballDirectionX;
			if (ballPositionX > 670) {
				ballDirectionX = -ballDirectionX;
			}
			if (ballPositionY < 0) {
				ballDirectionY = -ballDirectionY;
			}
			if (ballPositionX < 0) {
				ballDirectionX = -ballDirectionX;
			}
			repaint();
		}
	}
}
