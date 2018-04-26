import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class test extends JFrame implements ActionListener, 
Runnable, MouseListener
{
	private static final long serialVersionUID = -2417758397965039613L;
	private final int EMPTY = 0;
	private final int MINE = 1;
	private final int CHECKED = 2;
	private int MINE_COUNT = 10;
	private int BUTTON_BORDER = 50;
	private int MINE_SIZE = 10;
	private final int START_X = 20;
	private final int START_Y = 100;
	private boolean flag;
	private Dimension d = new Dimension(START_X+40+BUTTON_BORDER*MINE_SIZE, START_Y+60+BUTTON_BORDER*MINE_SIZE);         // the size of this software interface
	private Point p = new Point(700, 100);                 // the location of this software interface
	private JButton[][] jbutton;
	private JRadioButton simplebutton, middlebutton, difficultbutton;
	private ButtonGroup model;
	private JLabel jlabel;
	private JLabel showtime;
	private int[][] map;
	private int[][] mv = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
	
	public test(String title)
	{
		super(title);
		this.setLayout(null);
		jbutton = new JButton[MINE_SIZE][MINE_SIZE];
		map = new int[MINE_SIZE][MINE_SIZE];
		jlabel = new JLabel();
		showtime = new JLabel();
		simplebutton = new JRadioButton("Simple");
		middlebutton = new JRadioButton("Middle");
		difficultbutton = new JRadioButton("Difficult");
		model = new ButtonGroup();
		
		simplebutton.setBounds(START_X, START_Y-40, 100, 30);
		middlebutton.setBounds(START_X+100, START_Y-40, 100, 30);
		difficultbutton.setBounds(START_X+200, START_Y-40, 100, 30);
		
		simplebutton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {}
				});
		
		
		
		model.add(simplebutton);
		model.add(middlebutton);
		model.add(difficultbutton);
		model.setSelected(simplebutton, true);
	}
	
	public void makeMine()
	{
		int i=0, tx, ty;
		for(; i<MINE_COUNT;)
		{
			tx = (int)(Math.random() * MINE_SIZE);
			ty = (int)(Math.random() * MINE_SIZE);
			if(map[tx][ty] == EMPTY)
			{
				map[tx][ty] = MINE;
				i++;
			}
		}
	}
	
	public void makeButton()
	{
		for(int i=0; i<MINE_SIZE; i++)
		{
			for(int j=0; j<MINE_SIZE; j++)
			{
				jbutton[i][j] = new JButton();
				jbutton[i][j].addActionListener(this);
				jbutton[i][j].addMouseListener(this);
				jbutton[i][j].setName(Integer.toString(i)+','+Integer.toString(j));
				jbutton[i][j].setBounds(j*BUTTON_BORDER+START_X, i*BUTTON_BORDER+START_Y, BUTTON_BORDER, BUTTON_BORDER);
				this.add(jbutton[i][j]);
			}
		}
	}
	
	public void init()
	{
		this.add(simplebutton);
		this.add(middlebutton);
		this.add(difficultbutton);
		
	}
	
	private void showMessage(String title, String text)
	{
		jlabel.setText(title+text);
		System.out.println("in function showMessage():"+text);
	}
	
	private void showMine()
	{
		for(int i=0; i<MINE_SIZE; i++)
		{
			for(int j=0; j<MINE_SIZE; j++)
			{
				if(map[i][j]==MINE)
					jbutton[i][j].setText("*");
			}
		}
	}
	
	private void countMineAround(int x, int y, int d)     // Only when the button of this location is not a mine
	{
		map[x][y] = CHECKED;
		int tx, ty, count = 0;
		for(int i=0; i<8; i++)                            // count the mines around the button
		{
			tx = x + mv[i][0];
			ty = y + mv[i][1];
			if(tx>=0 && tx<MINE_SIZE && ty>=0 && ty<MINE_SIZE)
			{
				if(map[tx][ty]==MINE)
					count++;
			}
		}
		
		if(count==0)
		{
			for(int i=0; i<8; i++)
			{
				tx = x + mv[i][0];
				ty = y + mv[i][1];
				if(tx>=0 && tx<MINE_SIZE && ty>=0 && ty<MINE_SIZE && map[tx][ty]!=CHECKED)
				{
					countMineAround(tx, ty, d+1);
				}
			}
		}else
		{
			jbutton[x][y].setText(count+"");
		}
		
		jbutton[x][y].setEnabled(false);
		return;
	}
	
	private void checkSuccess()
	{
		int count = 0;
		for(int i=0; i<MINE_SIZE; i++)
		{
			for(int j=0; j<MINE_SIZE; j++)
			{
				if(jbutton[i][j].isEnabled())
				{
					count++;
				}
			}
		}
		if(count==MINE_COUNT)
		{
			String tmp_str = showtime.getText();
			tmp_str.replaceAll("[^0-9]", "");
			showtime.setText(null);
			showMessage("Success:", "You've won the game! The time used is "+tmp_str+"s.");
			flag = true;
			showMine();
		}
	}
	
	public static void main(String[] args)
	{
		test demo = new test("Mine Sweep Game!");
		demo.init();
		demo.run();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// overwrite the function actionPerformed()
		Object obj = e.getSource();
		int x, y;
		if((obj instanceof JButton) == false)
		{
			showMessage("Error:", "Internal Error!");
			return;
		}
		String[] tempstring = ((JButton)obj).getName().split(",");
		x = Integer.parseInt(tempstring[0]);            // convert string to integer
		y = Integer.parseInt(tempstring[1]);
		
		if(map[x][y]==MINE)                             // if the location of this button buried a mine
		{
			showMessage("Death:", "You've stepped on a mine!");
			flag = true;
			showMine();
			return;
		}else
		{
			countMineAround(x, y, 0);
			checkSuccess();
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		/**
		if(e.getButton()==1)
		{
			Object obj = e.getSource();
			if((obj instanceof JButton)==false)
			{
				showMessage("Error:", "Interal Error!");
				return;
			}
			String[] tmp_str = ((JButton)obj).getName().split(",");
			int x = Integer.parseInt(tmp_str[0]);
			int y = Integer.parseInt(tmp_str[1]);
		}
		*/
	}
	
	public void run()
	{
		flag = false;
		jlabel.setText("欢迎测试，一共有"+MINE_COUNT+"个雷");
		jlabel.setVisible(true);
		jlabel.setBounds(20, 20, 500, 30);
		this.add(jlabel);
		showtime.setText("time used：0 s");
		showtime.setBounds(350, 20, 100, 30);
		this.add(showtime);
		makeMine();
		makeButton();
		this.setSize(d);
		this.setLocation(p);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
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
