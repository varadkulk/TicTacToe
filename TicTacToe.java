import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TicTacToe {
	int w = 1000;
	int h = 1000;
	BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	Point2D mouse = new Point2D.Double(0, 0);
	JLabel l = new JLabel(new ImageIcon(img));
	boolean currentx;
	int[][] places = new int[3][3];

	TicTacToe() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				places[i][j] = 0;
		}
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				mouse = me.getPoint();
				drawImage();
			}
		};
		l.addMouseListener(listener);
		setup();
		drawImage();
		JOptionPane.showMessageDialog(null, l);
	}

	public void setup() {
		Graphics2D g = img.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 1000);
		currentx = true;
		drawGrid();
	}

	public void drawGrid() {
		Graphics2D g = img.createGraphics();
		if (isWin(1)) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 40);
			g.setColor(Color.blue);
			g.drawString("Winner: X", 455, 25);
		} else if (isWin(10)) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 40);
			g.setColor(Color.red);
			g.drawString("Winner: O", 455, 25);
		} else if (istie()) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 40);
			g.setColor(Color.white);
			g.drawString("It is a tie", 455, 25);
		} else {
			g.setColor(Color.black);
			g.fillRect(500, 10, 20, 20);
			if (currentx)
				g.setColor(Color.blue);
			else
				g.setColor(Color.red);
			g.drawString("Turn: ", 469, 25);
			if (currentx)
				g.drawString("X", 500, 25);
			else
				g.drawString("O", 500, 25);
		}
		for (int i = 0; i <= 3; i++) {
			g.fillRect(50, (50 + (i * 300)), 910, 10);
			g.fillRect((50 + (i * 300)), 50, 10, 910);
		}
	}

	public void drawImage() {
		Graphics2D g = img.createGraphics();
		if (!((isWin(1)) || (isWin(10)) || istie())) {
			int x = (int) mouse.getX();
			int y = (int) mouse.getY();
			int i = -1, j = -1;
			if (x > 50 && x < 350) {
				i = 0;
				if (y > 50 && y < 350)
					j = 0;
				else if (y > 350 && y < 650)
					j = 1;
				else if (y > 650 && y < 950)
					j = 2;
			} else if (x > 350 && x < 650) {
				i = 1;
				if (y > 50 && y < 350)
					j = 0;
				else if (y > 350 && y < 650)
					j = 1;
				else if (y > 650 && y < 950)
					j = 2;
			} else if (x > 650 && x < 950) {
				i = 2;
				if (y > 50 && y < 350)
					j = 0;
				else if (y > 350 && y < 650)
					j = 1;
				else if (y > 650 && y < 950)
					j = 2;
			}

			if (currentx)
				printx(g, i, j);
			else
				printo(g, i, j);
			drawGrid();
		}
		l.setIcon(new ImageIcon(img));
	}

	public void printx(Graphics2D g, int i, int j) {
		if (i != -1 && j != -1) {
			if (places[i][j] == 0) {
				g.setColor(Color.blue);
				g.fillPolygon(new int[] { (100 + (i * 300)), (120 + (i * 300)), (300 + (i * 300)), (280 + (i * 300)) },
						new int[] { (100 + (j * 300)), (100 + (j * 300)), (300 + (j * 300)), (300 + (j * 300)) }, 4);
				g.fillPolygon(new int[] { (100 + (i * 300)), (120 + (i * 300)), (300 + (i * 300)), (280 + (i * 300)) },
						new int[] { (300 + (j * 300)), (300 + (j * 300)), (100 + (j * 300)), (100 + (j * 300)) }, 4);
				currentx = false;
				places[i][j] = 1;
			}
		}
	}

	public void printo(Graphics2D g, int i, int j) {
		if (i != -1 && j != -1) {
			if (places[i][j] == 0) {
				g.setColor(Color.red);
				g.fillOval(100 + (i * 300), 100 + (j * 300), 200, 200);
				g.setColor(Color.black);
				g.fillOval(110 + (i * 300), 110 + (j * 300), 180, 180);
				currentx = true;
				places[i][j] = 10;
			}
		}
	}

	public boolean isWin(int player) {

		return ((places[0][0] + places[0][1] + places[0][2] == player * 3)
				|| (places[1][0] + places[1][1] + places[1][2] == player * 3)
				|| (places[2][0] + places[2][1] + places[2][2] == player * 3)
				|| (places[0][0] + places[1][0] + places[2][0] == player * 3)
				|| (places[0][1] + places[1][1] + places[2][1] == player * 3)
				|| (places[0][2] + places[1][2] + places[2][2] == player * 3)
				|| (places[0][0] + places[1][1] + places[2][2] == player * 3)
				|| (places[2][0] + places[1][1] + places[0][2] == player * 3));
	}

	public boolean istie() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (places[i][j] == 0)
					return false;
			}

		}
		return true;
	}

	public static void main(String[] args) {
		new TicTacToe();
	}
}
