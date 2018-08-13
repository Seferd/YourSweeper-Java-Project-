import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class yourSweeper {
	static int initialTime=300;//Initial the time
	final static int xSize = 10;//Number of Tiles on x-axis
	final static int ySize = 10;//Number of Tiles on y-axis
	static int numM = 1;//Number of Mines
	static int displayMines = numM;//Number to display the number of mine
	static Timer tm;

	private JFrame frame;
	private JToolBar toolBar;
	private static JLabel lblMines;
	private static JLabel lblTIme;
	private static JLabel lbltime;
	JButton btnStart;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					yourSweeper window = new yourSweeper();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}


		});
	}

	/**
	 * Create the application.
	 */
	public yourSweeper() {
		initialize();


	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 548, 513);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 500, 22);
		frame.getContentPane().add(toolBar);

		lblMines = new JLabel("Mines: "+displayMines);
		toolBar.add(lblMines);

		lbltime = new JLabel();
		lbltime.setText("              Time:"+initialTime);
		toolBar.add(lbltime);









		final JButton[][] grid = new JButton[xSize][ySize];//Create Jbuttoms base on the sizes
		final Tile[][] lGrid = new Tile[xSize][ySize];//Create type Tile 


		for(int i = 0;i<ySize;i++) {//Generate mines column by column
			final int num1 = i;
			for(int x = 0;x<xSize;x++) {
				final int num2 = x;

				lGrid[i][x] = new Tile(x,i);
				grid[i][x] = new JButton("");

				grid[i][x].setBounds(20+40*x, 20+40*i, 40, 40);
				grid[i][x].setFont(new Font("Tahoma", Font.PLAIN, 11));
				frame.getContentPane().add(grid[i][x]);

				tm=new Timer(1000,new ActionListener() {//Timer method

					public void actionPerformed(ActionEvent arg0) {
						lbltime.setText("              Time:"+initialTime);
						initialTime--;
						 if(initialTime==-1) {
							tm.stop();
							JOptionPane.showMessageDialog(null,"Time out");
							
						}
						 else if(numM==0) {
							 tm.stop();
							 System.out.println("You won!");
							 System.out.println("Score time:"+(initialTime+1)+" seconds");
							 
						 }
					}

				});


				grid[i][x].addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent arg0) {//Generate mouse click event to determine the player whether hit the mine or not.
						System.out.println(arg0.getButton());

						if(arg0.getButton()==1) {//left click event
							tm.start();//left click to start the game

							if(lGrid[num1][num2].isMine) {//if the player hit the mine target, end the game
								tm.stop();//if hit the mine,time stop
								endGame(numM);


							}
							
							else {
								grid[num1][num2].setEnabled(false);//if the tile is not the mine,disable the bottom
								reveal(grid,lGrid,num1,num2);//Reveal method.



							}
						}else if(arg0.getButton()==3) {//Right click event
							if(!lGrid[num1][num2].disarmed) {// set the flag off
								flag(grid,lGrid,num1,num2,false);
							}else if(lGrid[num1][num2].disarmed) {//set the flag on
								flag(grid,lGrid,num1,num2,true);
							}
						}
						
						
					}
					
				});
				

			}
		}
		for(int num3 = 0; num3<numM;num3++) {//Generate the mines
			double x = Math.random()*xSize;
			double y = Math.random()*ySize;

			lGrid[(int)x][(int)y].isMine = true;

		}
	}

	public static void flag(JButton[][] button,Tile[][] tile,int r,int c,boolean undo) {//flag method
		if(!undo&&displayMines>0) {//if the flag hit the target
			tile[r][c].disarmed=true;
			button[r][c].setText("X");//Flag location show "x"
			button[r][c].setEnabled(false);
			if(tile[r][c].isMine) {
				numM--;
				if(numM==0) {
					endGame(numM);
				}
			}
			displayMines--;
			lblMines.setText("Mines: "+displayMines);
		}else if(undo) {
			tile[r][c].disarmed=false;
			button[r][c].setText(null);
			button[r][c].setEnabled(true);
			displayMines++;
			lblMines.setText("Mines: "+displayMines);

		}

	}


	public static void reveal(JButton[][] button,Tile[][] tile,int r, int c) {//Revaeal method

		button[r][c].setEnabled(false);
		boolean foundMine = false;
		boolean hasLooped = false;
		int mineCount=0;

		for(int a = -1;a<2;a++) {
			if(r+a>=0&&r+a<=ySize-1) {
				for(int b = -1;b<2;b++) {
					if(c+b>=0&&c+b<=xSize-1) {
						if(!tile[r+a][c+b].isMine&&button[r+a][c+b].isEnabled()&&!foundMine) {
							if(hasLooped) reveal(button,tile,r+a,c+b);
						}else if(tile[r+a][c+b].isMine) {
							mineCount++;
							foundMine=true;	
						}
					}
				}
			}
			if(a==1&&!hasLooped) { a=-2;hasLooped = true;mineCount=0;}
		}	
		button[r][c].setText(""+mineCount);
	}
	public static void endGame(int mines) {
		System.out.println("Game Over... with: "+mines+" mines left");
	}


}
