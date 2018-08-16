import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JFileChooser;

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
import java.util.prefs.Preferences;

import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;



public class yourSweeper {	
	private static Preferences prefs;
	static String time;
	static int initialTime;
	static String x;
	static int maxTime;
	static int numM;
	static int mineLeft;
	static int maxMine;
	static int displayMines;//Number to display the number of mine
	static Timer tm;
	static int height;
	static int width;
	static boolean win;
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
	private JButton btnViewScores;
	private JButton btnClearScore;
	private static String fqfn;
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
	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
	prefs = Preferences.userRoot().node("YourSweeper");
	fqfn=prefs.get("ScoreFile1", "none");
	System.out.println(fqfn);
	if(fqfn.equals("none")){
				JOptionPane.showMessageDialog(null,"Please select the score.txt in the main folder");
				JFileChooser jfc=new JFileChooser();
				int response = jfc.showOpenDialog(null);
				if(response!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null,"Please select the score.txt file" );
					return;}

				File theFile=jfc.getSelectedFile();
				String filePath=theFile.getAbsolutePath();
				prefs.put("ScoreFile1", filePath);
				fqfn=prefs.get("ScoreFile1", filePath);
				System.out.println(fqfn);
			
			
			
		}
	try {
		BufferedReader br=new BufferedReader(new FileReader(fqfn));
	} catch (FileNotFoundException e1) {
		JOptionPane.showMessageDialog(null,"Welcome back!");
		JOptionPane.showMessageDialog(null,"Your saving file is missed,Please relocate your new saving file");
		JFileChooser jfc=new JFileChooser();
		int response = jfc.showOpenDialog(null);
		if(response!=JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null,"Please select the score.txt file" );
			return;}

		File theFile=jfc.getSelectedFile();
		String filePath=theFile.getAbsolutePath();
		prefs.put("ScoreFile1", filePath);
		fqfn=prefs.get("ScoreFile1", filePath);
		JOptionPane.showMessageDialog(null,"Thank you!Now please enjor your game");
		System.out.println(fqfn);
		
	}

		
	
		
		frame = new JFrame();
		frame.setBounds(100, 100, 762, 571);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnEasy = new JButton("Easy");
		btnEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				x = "10";
				numM = 10;
				maxMine=numM;
				displayMines = numM;
				time = "120";
				maxTime = 120;
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
				maxMine=numM;
				time = "240";
				maxTime = 240;
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
				maxMine=numM;
				time = "480";
				maxTime = 480;
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
				maxTime = Integer.parseInt(time);
				numM = Integer.parseInt(JOptionPane.showInputDialog("Mines? (Max 100)" ));
				displayMines = numM;
				maxMine=numM;
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

		btnViewScores = new JButton("View Scores");
		btnViewScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					readscore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnViewScores.setBounds(605, 0, 135, 29);
		frame.getContentPane().add(btnViewScores);

		lblImage = new JLabel("Image");
		lblImage.setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/sweeper.jpg")));
		lblImage.setBounds(0, 0, 750, 524);
		frame.getContentPane().add(lblImage);

		btnClearScore = new JButton("Clear Score");
		btnClearScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					delectScore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		});
		btnClearScore.setBounds(450,0, 115, 29);
		frame.getContentPane().add(btnClearScore);

	}
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
							JOptionPane.showMessageDialog(null, "       Time Out!","Game Over",JOptionPane.PLAIN_MESSAGE);
							endGame(numM,grid,lGrid);
							frame.dispose();
							main(new String[0]);



						}
						else if(numM==0) {
							tm.stop();
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
								endGame(numM,grid,lGrid);
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
					endGame(numM,button,tile);
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


	public static void reveal(JButton[][] button,Tile[][] tile,int r, int c) {//Reveal method

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
	public static void endGame(int mines,JButton[][] button,Tile[][] tile) {
		//JOptionPane.showMessageDialog(null, "Game Over.. " +mines+" Mines left "+initialTime+" Seconds remaining");
		//System.out.println("Game Over... with: "+mines+" mines left");
		for(int r=0;r<Integer.parseInt(x);r++) {
			for(int c=0;c<Integer.parseInt(x);c++) {
				if(tile[r][c].isMine)button[r][c].setIcon(new ImageIcon(yourSweeper.class.getResource("/Resources/mine.png")));
			}
		}
		mineLeft=mines;
		gameover.setText("Game Over... with: "+mines+" mines left");
		String name=null;
		if(mines==0) {name=JOptionPane.showInputDialog(null,"             "+"Victory!!!!"+"\n"+" Please enter your name!!!");}
		else {
			name=JOptionPane.showInputDialog(null,"             "+"Try again!!!!"+"\n"+" Please enter your name!!!");
		}
		try {
			setscore(name,initialTime,mineLeft);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			readscore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void setscore(String name,int time,int mineLeft) throws IOException {
		fqfn=prefs.get("ScoreFile1", "filePath");
		File oldFile=new File(fqfn);
		System.out.println("SetScore:"+fqfn);
		String newfqfn=fqfn+"new";
		System.out.println(fqfn);

		try {
			BufferedReader br=new BufferedReader(new FileReader(fqfn));
			PrintWriter pw=new PrintWriter(newfqfn);
			String newLine=null;
			if(mineLeft!=0) {
				newLine=("Name: "+name+", Time spend: "+(initialTime+1)+" seconds, Diffcult level: "+x+" x "+x+", Mines left: "+mineLeft+", Max Time: "+maxTime +" Max Mine Number: "+maxMine+"\n");}
			else {
				newLine=("  |C l e a r !|    "+"Name: "+name+", Time spend: "+(initialTime+1)+" seconds, Diffcult level: "+x+" x "+x+" "+", Max Time: "+maxTime +" Max Mine Number: "+maxMine+"\n");
			}
			String line;			
			while(true) {
				line=br.readLine();
				if(line==null) { break;}
				pw.println(line);
			}
			line=newLine;
			pw.println(line);
			br.close();
			pw.close();
			oldFile.delete();
			File newFile=new File(newfqfn);
			newFile.renameTo(oldFile);
		} catch (Exception e) {
			System.out.println("Error");
		}
		
			





		



	};
	private void delectScore() throws IOException {
		fqfn=prefs.get("ScoreFile1", "filePath");
		File oldFile=new File(fqfn);
		System.out.println("SetScore:"+fqfn);

		try {
			BufferedReader br=new BufferedReader(new FileReader(fqfn));
			PrintWriter pw=new PrintWriter(fqfn);
			String newLine=null;
			String line;			
			while(true) {
				line=br.readLine();
				if(line==null) { break;}
				pw.println(line);
			}
			pw.close();
			br.close();
		
		}catch (Exception e) {
			System.out.println("Error");
		}
	}
	public static void readscore()throws IOException{
		
		System.out.println("View Bottom to read a file at:"+fqfn);
		String output="";
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
			JOptionPane.showMessageDialog(null, "Score File is not located,please reselect the score text file again");
			JFileChooser jfc=new JFileChooser();
			int response = jfc.showSaveDialog(null);
			if(response!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"Please select the score.txt file" );
				return;
			}
			File theFile=jfc.getSelectedFile();
			String filePath=theFile.getAbsolutePath();
			prefs.put("ScoreFile1", filePath);
			fqfn=prefs.get("ScoreFile1",filePath);

			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
