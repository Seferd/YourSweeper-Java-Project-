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
import javax.swing.JLabel;

public class YourSweeper {
	
	final static int xSize = 30;
	final static int ySize = 30;
	static int numM = 100;
	static int displayMines = numM;

	private JFrame frame;
	private JToolBar toolBar;
	private static JLabel lblMines;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YourSweeper window = new YourSweeper();
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
	public YourSweeper() {
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
		toolBar.setBounds(0, 0, 125, 22);
		frame.getContentPane().add(toolBar);
		
		lblMines = new JLabel("Mines: "+displayMines);
		toolBar.add(lblMines);
		
		JButton[][] grid = new JButton[xSize][ySize];
		Tile[][] lGrid = new Tile[xSize][ySize];
		
		
		for(int i = 0;i<ySize;i++) {
			int num1 = i;
			for(int x = 0;x<xSize;x++) {
				int num2 = x;
				
				lGrid[i][x] = new Tile(x,i);
				grid[i][x] = new JButton("");
				
				grid[i][x].setBounds(20+40*x, 20+40*i, 40, 40);
				grid[i][x].setFont(new Font("Tahoma", Font.PLAIN, 11));
				frame.getContentPane().add(grid[i][x]);
				grid[i][x].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						System.out.println(arg0.getButton());
						if(arg0.getButton()==1) {
							if(lGrid[num1][num2].isMine) {
								endGame(numM);
							}else {
								grid[num1][num2].setEnabled(false);
								reveal(grid,lGrid,num1,num2);
							}
						}else if(arg0.getButton()==3) {
							if(!lGrid[num1][num2].disarmed) {
								flag(grid,lGrid,num1,num2,false);
							}else if(lGrid[num1][num2].disarmed) {
								flag(grid,lGrid,num1,num2,true);
							}
						}
					}
				});
				
			}
		}
		for(int num3 = 0; num3<numM;num3++) {
			double x = Math.random()*xSize;
			double y = Math.random()*ySize;
			
			lGrid[(int)x][(int)y].isMine = true;
			
		}
	}
	public static void flag(JButton[][] button,Tile[][] tile,int r,int c,boolean undo) {
		if(!undo&&displayMines>0) {
			tile[r][c].disarmed=true;
			button[r][c].setText("X");
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
	public static void reveal(JButton[][] button,Tile[][] tile,int r, int c) {
		
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
