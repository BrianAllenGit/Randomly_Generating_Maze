package Final;

import javax.swing.JFrame;

import java.awt.Color;
public class Final extends JFrame {

    public Final() {
        add(new Board()); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        if (Board.x == 20)
        	setSize(1050,1050);
        if (Board.x == 40)
        	setSize(1700,1050);

        setLocationRelativeTo(null);
        setTitle("I love Java");
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
	    Board maze;
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 8;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 8;
        maze = new Board(x, y);
        new Final();
        maze.show();
        
      
}
}

