/**
 * @author Cameron Vandermeersch
 * @version 1.0
 * Date July 1, 2022
 */
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class GameOutline {
	public int boardWidth;
	public int boardHeight;
	public int boardMap[][];

	public void drawBoard(Graphics2D game) {
		for (int c = 0; c < boardMap.length; c++) {
			for (int x = 0; x < boardMap[0].length; x++) {
				if (boardMap[c][x] > 0) {
					game.setColor(Color.red);
					game.fillRect(x * boardWidth + 75, c * boardHeight + 45, boardWidth, boardHeight);
					game.setStroke(new BasicStroke(3));
					game.setColor(Color.black);
					game.drawRect(x * boardWidth + 75, c * boardHeight + 45, boardWidth, boardHeight);
				}
			}
		}
	}

	public void setBoardValue(int passedValue, int row, int column) {
		boardMap[row][column] = passedValue;
	}

	public GameOutline(int row, int column) {
		boardMap = new int[row][column];
		for (int len = 0; len < boardMap.length; len++) {
			for (int len2 = 0; len2 < boardMap[0].length; len2++) {
				boardMap[len][len2] = 1;
			}
		}
		boardWidth = 550 / column;
		boardHeight = 190 / row;
	}
}
