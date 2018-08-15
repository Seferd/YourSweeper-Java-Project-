import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.annotation.Resource;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;



public class yourSweeper {
	static String time;
	static int initialTime;
	static String x;
}


public class yourSweeper {	
	static String time = JOptionPane.showInputDialog("Time prefer (Max 300)");
	final static String maxTime=time;
	static int initialTime= Integer.parseInt(time);//Initial the time
	static String x = JOptionPane.showInputDialog("Type in a number (Max 20)");
	
	static int numM;
	static int displayMines;//Number to display the number of mine
	static Timer tm;
	static int height;
	static int width;

	private JFrame frame;
	private JToolBar toolBar;
	private static JLabel lblMines;
	private static JLabel lbltime;
	JButton btnStart;
	private static JTextField gameover;
	private JLabel lblImage;
	private JButton btnEasy;
	private JButton btnMedium;
	private JButton btnHard;
	private JButton btnCustom;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					yourSweeper window = new yourSweeper(args);
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
	public yourSweeper(String[] args) {
		if(args.length==0) {initialize();}
		else {startGame();}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 762, 571);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnEasy = new JButton("Easy");
		btnEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				x = "10";
				numM = 10;
				displayMines = numM;
				time = "120";
				width = 460;
				height = 500;
				frame.dispose();
				String[] ret = new String[2];
				main(ret);
			}
		});
		btnEasy.setBounds(71, 394, 115, 29);
		frame.getContentPane().add(btnEasy);
		
		btnMedium = new JButton("Medium");
		btnMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				x = "20";
				numM = 50;
				displayMines = numM;
				time = "240";
				width = 860;
				height = 900;
				frame.dispose();
				String[] ret = new String[2];
				main(ret);
			}
		});
		btnMedium.setBounds(318, 394, 115, 29);
		frame.getContentPane().add(btnMedium);
		
		btnHard = new JButton("Hard");
		btnHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				x = "30";
				numM = 100;
				displayMines = numM;
				time = "480";
				width = 1260;
				height = 1300;
				String[] ret = new String[2];
				main(ret);
				frame.dispose();
			}
		});
		btnHard.setBounds(559, 394, 115, 29);
		frame.getContentPane().add(btnHard);
		
		btnCustom = new JButton("Custom");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				time = JOptionPane.showInputDialog("Time prefer (Max 300)");
				numM = Integer.parseInt(JOptionPane.showInputDialog("Mines? (Max 100)" ));
				displayMines = numM;
				x = JOptionPane.showInputDialog("Type in a number (Max 20)");
				width = 2000;
				height = 1000;
				String[] ret = new String[2];
				main(ret);
				frame.dispose();
			}
		});
		btnCustom.setBounds(318, 462, 115, 29);
		frame.getContentPane().add(btnCustom);
		
		lblImage = new JLabel("Image");
		lblImage.setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/sweeper.jpg")));
		lblImage.setBounds(0, 0, 750, 524);
		frame.getContentPane().add(lblImage);

	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void startGame() {
		int size = Integer.parseInt(x);
		initialTime = Integer.parseInt(time);//Initial the time
		final JButton[][] grid = new JButton[size][size];//Create Jbuttons base on the sizes
		final Tile[][] lGrid = new Tile[size][size];//Create type Tile 
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.MAGENTA);
		frame.setBounds(100, 100, width, height);
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
		
		gameover = new JTextField();
		gameover.setForeground(Color.RED);
		gameover.setFont(new Font("Tahoma", Font.BOLD, 13));
		gameover.setEditable(false);
		gameover.setBounds(186, 0, 444, 22);
		frame.getContentPane().add(gameover);
		gameover.setColumns(10);

		for(int i = 0;i<size;i++) {//Generate mines column by column
			final int num1 = i;
			for(int x = 0;x<size;x++) {
				final int num2 = x;

				lGrid[i][x] = new Tile(x,i);
				grid[i][x] = new JButton("");

				grid[i][x].setBounds(20+40*x, 20+40*i, 40, 40);
				grid[i][x].setFont(new Font("Tahoma", Font.PLAIN, 11));
				frame.getContentPane().add(grid[i][x]);
				grid[i][x].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/blank.png")));

				tm=new Timer(1000,new ActionListener() {//Timer method

					public void actionPerformed(ActionEvent arg0) {
						lbltime.setText("              Time:"+initialTime);
						initialTime--;
						 if(initialTime==-1) {
							tm.stop();
							endGame(numM);
							frame.dispose();
							main(new String[0]);
							
							
						}
						 else if(numM==0) {
							 tm.stop();
							 System.out.println("You won!");
							 System.out.println("Score time:"+(initialTime+1)+" seconds");
							 frame.dispose();
							 main(new String[0]);
							 
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
								frame.dispose();
								main(new String[0]);


							}
							
							else {
								grid[num1][num2].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/empty.png")));
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
			double x = Math.random()*size;
			double y = Math.random()*size;
			if(lGrid[(int)x][(int)y].isMine)num3--;
			lGrid[(int)x][(int)y].isMine = true;

		}
	}

	public static void flag(JButton[][] button,Tile[][] tile,int r,int c,boolean undo) {//flag method
		if(!undo&&displayMines>0&&tile[r][c].isEnabled) {//if the flag hit the target
			tile[r][c].disarmed=true;
			button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/flagged.png")));
			tile[r][c].setEnabled(false);
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
			button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/blank.png")));
			tile[r][c].setEnabled(true);
			displayMines++;
			if(tile[r][c].isMine)numM++;
			lblMines.setText("Mines: "+displayMines);

		}

	}


	public static void reveal(JButton[][] button,Tile[][] tile,int r, int c) {//Revaeal method

		button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/empty.png")));
		tile[r][c].setEnabled(false);
		boolean foundMine = false;
		boolean hasLooped = false;
		int mineCount=0;

		for(int a = -1;a<2;a++) {
			if(r+a>=0&&r+a<=Integer.parseInt(x)-1) {
				for(int b = -1;b<2;b++) {
					if(c+b>=0&&c+b<=Integer.parseInt(x)-1) {
						
						if(!tile[r+a][c+b].isMine&&tile[r+a][c+b].isEnabled()&&!foundMine) {
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
		switch(mineCount) {
			case 1: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/one.png")));
				break;
			case 2: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/two.png")));
				break;
			case 3: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/three.png")));
				break;
			case 4: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/four.png")));
				break;
			case 5: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/five.png")));
				break;
			case 6: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/six.png")));
				break;
			case 7: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/seven.png")));
				break;
			case 8: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/eight.png")));
				break;
			default: button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/empty.png")));
		}
	}
	public static void endGame(int mines) {
		JOptionPane.showMessageDialog(null, "Game Over.. " +mines+" Mines left "+initialTime+" Seconds remaining");
		//System.out.println("Game Over... with: "+mines+" mines left");
		gameover.setText("Game Over... with: "+mines+" mines left");
		
		
		String name=JOptionPane.showInputDialog(null,"Enter your name:");
		try {
			setscore(name,initialTime);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		readscore();
		
	}
	public static void setscore(String name,int time) throws IOException {
		
		String output="";
		ArrayList<String> stringToDis=new ArrayList<String>();
		String pathname=System.getProperty("user.dir");
		String fqfn=pathname+"\\Resources\\Score.txt";
		String newfqfn=pathname+"\\Resources\\NewScore.txt";
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(fqfn));
			PrintWriter pw=new PrintWriter(newfqfn);
			String newLine=("Name: "+name+", Time spend: "+initialTime+"seconds, Diffcult level: "+x+" x "+x+", Mine Number: "+numM+", Max Time: "+maxTime +"\n");
			String line;
			while(true) {
				line=br.readLine();
				
				if(line==null) {
					break;
					
					
				}
				pw.println(line);
				
				
			}
			line=newLine;
			pw.print(line);
			
			//Name:ha,Time spend:30 seconds,Diffcult level: 20 X 20 ,Mine Number:20,Max Time:3
			br.close();
			pw.close();
			File oldFile=new File(fqfn);
			oldFile.delete();
			File newFile=new File(newfqfn);
			newFile.renameTo(oldFile);
		
	
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	};
	public static void readscore(){
	
		String output="";
		String pathname=System.getProperty("user.dir");
		String fqfn=pathname+"\\Resources\\Score.txt";
		String newfqfn=pathname+"\\Resources\\NewScore.txt";
		ArrayList<String> stringToDis=new ArrayList<String>();
		
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(fqfn));
			while(true) {
				String line=br.readLine();
			
				stringToDis.add(line);
				
				if(line==null) {
					break;
				}
				
			}
			br.close();
           for(int i=0;i<stringToDis.size()-1;i++) {
        	   String scratch=stringToDis.get(i).toString();
        	   output+=scratch+"\n";
           }
			
			
			JOptionPane.showMessageDialog(null, output, "Score Board", JOptionPane.PLAIN_MESSAGE);
			
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
