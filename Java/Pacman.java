import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

////////////////////////////////////////////////////////////////////////////////
//Right now, this program draws a black screen with 2 cyan lines representing
//part of PAC-MAN's maze.
//
////////////////////////////////////////////////////////////////////////////////
//Complete this program so that it will display the classic arcade icon of PAC-MAN.
//Pacman must be a yellow 3/4 circle with his mouth open to the right.
//Pacman must have a diameter of 300 pixels and be perfectly centered on the window (800, 600).
//You will also need to draw 3 round "snacks" for PAC-MAN.
//PAC-MAN's snacks are solid white circles with a diameter of 80 pixels.
//The 3 circles must be spaced evenly apart and be perfectly centered vertically.
//
////////////////////////////////////////////////////////////////////////////////
public class Pacman extends JPanel {

    // Unique version ID for this class to ensure saved objects can be loaded safely
    private static final long serialVersionUID = 1L;

    // main method to launch the program as a standalone application - no need to modify
    public static void main(String[] args) {
        Pacman panel = new Pacman();
        panel.setPreferredSize(new Dimension(800, 600)); // content size window dimensions
        JFrame frame = new JFrame("Pacman"); // Title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Draw Pacman. Pacman must be a yellow 3/4 circle with his mouth open to
     * the right. Pacman must have a diameter of 300 pixels and be perfectly
     * centered on the window (800, 600).
     *
     * @param g the Graphics object used for drawing shapes, text, and images
     */
    public void drawPacman(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(250, 150, 300, 300, 45, 270);

    }

    /**
     * Draw 3 round "snacks" for Pacman. Pacman's snacks are solid white circles
     * with a diameter of 80 pixels. The 3 circles must be spaced evenly apart
     * and be perfectly centered vertically.
     *
     * @param g the Graphics object used for drawing shapes, text, and images
     */
    public void drawSnacks(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(500, 260, 80, 80);
        g.fillOval(600, 260, 80, 80);
        g.fillOval(700, 260, 80, 80);
    }

    /**
     * Draws a black screen with 2 cyan lines representing part of Pacman's
     * maze.
     *
     * @param g the Graphics object used for drawing shapes, text, and images
     */
    public void drawMaze(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.CYAN);
        g.fillRect(0, 80, 800, 20);
        g.fillRect(0, 500, 800, 20);
    }

    /**
     * Overrides JPanel's paintComponent method to perform custom drawing.
     *
     * @param g the Graphics object used for drawing shapes, text, and images
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel before drawing
        drawMaze(g);
        drawPacman(g);
        drawSnacks(g);
    }
}
