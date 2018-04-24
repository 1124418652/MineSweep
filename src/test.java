import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class test extends JFrame implements ActionListener, 
Runnable, MouseListener
{
	private final int EMPTY = 0;
	private final int MINE = 1;
	private final int CHECKED = 2;
	private final int MINE_COUNT = 10;
	private final int BUTTON_BORDER = 50;
	private final int MINE_SIZE = 10;
	private final int START_X = 20;
	private final int START_Y = 50;
	private boolean flag;
	private JButton[][] jbutton;
	private JLabel jlabel;
	private JLabel showtime;
	private int[][] map;
	
	public void actionPerformed(ActionEvent e)
	{
		// overwrite the function actionPerformed()
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void run()
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method
	}
	
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method
	}
	
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method
	}
	
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method
	}
}
