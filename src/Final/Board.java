package Final;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {
	static JLabel label2;
	private Timer timer;
	private int[][] maze;
	private static int image_x, image_y;
	public static int coord_x = 50, coord_y=50, x, y;
	static Toolkit tk= Toolkit.getDefaultToolkit();
	private static Image image = tk.getImage("resources/craft.png");


	public  Board() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		label2 = new JLabel();
		add(label2);
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image, coord_x, coord_y, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void move() {
		coord_x += image_x;
		coord_y += image_y;
		if (x==10)
		{
			if (coord_x >450 && coord_y > 450)
			{
				JOptionPane.showMessageDialog(null, "Maze completed.");
				System.exit(0);
			}
		}
		else if (x==20)
		{
			if (coord_x >870 && coord_y > 900)
			{
				JOptionPane.showMessageDialog(null, "Maze completed.");
				System.exit(0);
			}
		}
		else if (x==40)
		{
			if (coord_x >1520 && coord_y > 950)
			{
				JOptionPane.showMessageDialog(null, "Maze completed.");
				System.exit(0);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		move();
		repaint();  
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				image_x = 0;
			}

			if (key == KeyEvent.VK_RIGHT) {
				image_x = 0;
			}

			if (key == KeyEvent.VK_UP) {
				image_y = 0;
			}

			if (key == KeyEvent.VK_DOWN) {
				image_y = 0;
			}
		}

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				image_x = -1; 
			}
			if (key == KeyEvent.VK_RIGHT) {
				image_x = 1;
			}

			if (key == KeyEvent.VK_UP) {
				image_y = -1; 
			}

			if (key == KeyEvent.VK_DOWN) {
				image_y = 1;
			}
		}
	}

	public Board(int x, int y) {
		if (JOptionPane.showConfirmDialog(null, "Would you like a 10x10? ") == JOptionPane.YES_OPTION)
		{
			Board.x=10;
			Board.y=10;
		}
		else
		{
			if (JOptionPane.showConfirmDialog(null, "Would you like a 20x20? ") == JOptionPane.YES_OPTION)
			{
				Board.x = 20;
				Board.y = 20;
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "40x20 it is then.");
				Board.x =  40;
				Board.y = 20;
			}
		}
		maze = new int[Board.x][Board.y];
		createMaze(0, 0);
	}

	public void show() {
		String hold = "<html>";
		int temp= 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				hold = hold +( (maze[j][i] & 1) == 0 ? "+----" : "+&#160;&#160;&#160;&#160;&#160;");
				temp = j;
			}
			hold = hold + "&#160+<br>";
			for (int j = 0; j < x; j++) {
				if (j==1 && i == 0)
				{
					hold = hold + "&#160;&#160;&#160;";
				}
				if (j==0 && i == 0)
				{
					hold = hold + "&#160;&#160;&#160;&#160;&#160;";
				}
				else
				{
					hold = hold + ((maze[j][i] & 8) == 0 ? "|&#160;&#160;&#160;&#160;&#160;&#160;" : "&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
				}        
				temp = j;
			}
			if (temp == (Board.y-1) && i == Board.x-1 )
			{
				hold = hold + "<br>";
			}
			else
			{
				hold = hold + "&#160;|<br>";
			}
		}
		for (int j = 0; j < x; j++) {
			hold = hold +  "+----";
		}
		hold = hold + "+<br></html>";
		label2.setText(hold);
		label2.setFont(new Font("Helvetica", Font.PLAIN, 19));
	}

	private void createMaze(int cx, int cy) {
		Direction[] Directions = Direction.values();
		Collections.shuffle(Arrays.asList(Directions));
		for (Direction Direction : Directions) {
			int nx = cx + Direction.dx;
			int ny = cy + Direction.dy;
			if (between(nx, x) && between(ny, y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= Direction.bit;
				maze[nx][ny] |= Direction.opposite.bit;
				createMaze(nx, ny);
			}
		}
	}

	private static boolean between(int lower, int upper) {
		return (lower >= 0) && (lower < upper);
	}

	private enum Direction {
		NORTH(1, 0, -1), SOUTH(2, 0, 1), EAST(4, 1, 0), WEST(8, -1, 0);
		int bit, dx, dy;
		private Direction opposite;

		static {
			NORTH.opposite = SOUTH;
			SOUTH.opposite = NORTH;
			EAST.opposite = WEST;
			WEST.opposite = EAST;
		}

		private Direction(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	}
}

