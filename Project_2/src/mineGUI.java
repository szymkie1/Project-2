//******************
//CS 342 Project Two-Minesweeper
//Authors
//Tianniu Lei
//Ryan Szymkiewicz
//mineGUI.java
//this file contains the bulk of the game
//the interface UI is contained in this file along
//with the algorithm for determining and filling mine squares
//parts of this code (GUI related) were influenced by code posted by
//Prof. Troy and also from the Java Swing GUI Tutorials
//******************



import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.*;




@SuppressWarnings("serial")

//mineGUI class for the user interface-extend JFrame to set up interface
//and set up ability to get input by interacting with the game board
public class mineGUI extends JFrame implements ActionListener{
	
	
	//"Global" variables that will be used in this file.
	//explained in detail later on
	private button mineButtons[][] = new button[10][10];	
	private JMenuBar topMenu;
	private JMenu gameMenu,hMenu;
	private JMenuItem gReset, gTopTen, gExit, hHelp, hAbout;
	private mine mines = new mine();
	private int[] mineLocs = new int[10];
	private JButton resetButton;
	private JLabel timeElapsed,buttonsLeft,timeLabel,buttonLabel;
	private int currTime;
	private Timer time;
	private int numLClicks=0;
	
	
	public mineGUI(){
		
		super("Minesweeper");//set up new JFrame for game
		setSize(500,350);//set game size to 500x350
		
		
		
		topMenu = new JMenuBar();//create menu bar
		setJMenuBar(topMenu);//set menu bar on frame
		
		gameMenu = new JMenu("Game");//create game menu
		topMenu.add(gameMenu);//add game menu
		
		//Initialize each member of menu item.
		//add them to the menu,set a mnemonic for keyboard interaction depending on
		//title name.
		//add actionListener to register human interaction
		//repeat for next items
		gReset = new JMenuItem("Reset");
		gameMenu.add(gReset);
		gReset.setMnemonic(KeyEvent.VK_R);
		gReset.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
		gReset.addActionListener(this);
		gameMenu.addSeparator();
		
		gTopTen = new JMenuItem("Top Ten");
		gameMenu.add(gTopTen);
		gTopTen.setMnemonic(KeyEvent.VK_T);
		gTopTen.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.ALT_MASK));
		gTopTen.addActionListener(this);
		gameMenu.addSeparator();
		
		gExit = new JMenuItem("Exit");
		gExit.setMnemonic(KeyEvent.VK_X);
		gExit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.ALT_MASK));
		gExit.addActionListener(this);
		gameMenu.add(gExit);
		
		//create menu 2 of 2
		hMenu = new JMenu("Help");
		topMenu.add(hMenu);
		
		//same as other menu,  just different names
		hHelp = new JMenuItem("Help");
		hHelp.setMnemonic(KeyEvent.VK_H);
		hHelp.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, ActionEvent.ALT_MASK));
		hHelp.addActionListener(this);
		hMenu.add(hHelp);
		hMenu.addSeparator();
		
		hAbout = new JMenuItem("About");
		hAbout.setMnemonic(KeyEvent.VK_A);
		hAbout.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
		hAbout.addActionListener(this);
		hMenu.add(hAbout);
		
		
		
		//the UI will have 2 JPanels-1 grid layout and 1 plain panel
		//that will display game information
		
		JPanel mboard = new JPanel(new GridLayout(10,10));
		
		JPanel top = new JPanel();
		
		
		
		//create panel that will contain previous two panels
		//place that in a box layout so that they can be placed on top of each other
		//without having to do much reallignment
		JPanel uiBoard = new JPanel();
		uiBoard.setLayout(new BoxLayout(uiBoard, BoxLayout.Y_AXIS));
		
		
		
		//these initialize the information that will be displayed in the top panel.
		//time,reset button and buttons left
		
		timeLabel = new JLabel("Time: ");
		top.add(timeLabel);
		
		timeElapsed = new JLabel("0");
		top.add(timeElapsed);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		top.add(resetButton);
		
		buttonLabel = new JLabel("Buttons Left: ");
		top.add(buttonLabel);
		
		buttonsLeft = new JLabel(" 2 ");
		top.add(buttonsLeft);
		
		
		//create timer to keep track of game time
		int delay=1000;
		time = new Timer(delay, new timeInfo());
		
		
		
		//call mine class to determine where the mines will be placed
		//returns mineLocs which is a 10 element array of locations
		mines.setMines(10);
		mineLocs=mines.getMines();
		
		
	
		//initialize each button of the grid in a 2D array
		//set preferred size to prevent resizing issues
		//add button to board
		//set coordinates and position,  and also add mouse listener to each button
		
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
			mineButtons[i][j] = new button();
			mineButtons[i][j].setPreferredSize(new Dimension(20,20));
			mboard.add(mineButtons[i][j]);
			mineButtons[i][j].setXcoord(i);
			mineButtons[i][j].setYcoord(j);
			int position=(i*10)+j;
			mineButtons[i][j].setPos(position);
			mineButtons[i][j].addMouseListener(new buttonListener());
			
			
			}
			
		}
		
		//use mineLocs array and the button array to set the mines in 10 squares
		for(int a=0; a<10; a++){
			for(int b=0; b<10; b++){
				for(int c=0; c<10; c++){
				if(mineLocs[a]==mineButtons[b][c].getPos()){
					mineButtons[b][c].setBomb(1);
				}
				}
			}
		}
		
		//add the top panel and mine board to the container panel
		uiBoard.add(top);
		uiBoard.add(mboard);
		
		//add container panel to JFrame
		add(uiBoard);
		
		
		
		countAdjacentMines();
		
		
		
		setVisible(true);
		
		
	
	}
	
	
	
	private void doClear(button[][] mineButtons, int x, int y) {
		System.out.println("x:" + x + " y:" + y);
		if (x < 0 || x >= 10 || y < 0 || y >= 10) {
			return;
		}
		button currButton = mineButtons[x][y];
		if (!currButton.isEnabled()) {
			return;
		}

		currButton.setBackground(Color.lightGray);
		currButton.setEnabled(false);

		if (mineButtons[x][y].getBomb() == 1) {
			time.stop();
			currButton.setText("X");
			System.out.println("clicked on button with bomb, exiting...");
			// end game
		} else {
			if (mineButtons[x][y].getAdjacentBombs() != 0)
				mineButtons[x][y].setText(mineButtons[x][y]
						.getAdjacentBombsString());
			else
				mineButtons[x][y].setText(" ");

			if (currButton.getAdjacentBombs() == 0) {
				doClear(mineButtons, x - 1, y - 1); // top left
				doClear(mineButtons, x, y - 1); // top middle
				doClear(mineButtons, x + 1, y - 1); // top right
				doClear(mineButtons, x - 1, y); // mid left
				doClear(mineButtons, x + 1, y); // mid right
				doClear(mineButtons, x - 1, y + 1); // bot left
				doClear(mineButtons, x, y + 1); // bot middle
				doClear(mineButtons, x + 1, y + 1); // bot right
			}
		}
	}

	private void countAdjacentMines() {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (mineButtons[i][j].getBomb() != 1) {
					int count = 0;
					for (int p = i - 1; p <= i + 1; p++) {
						for (int q = j - 1; q <= j + 1; q++) {
							if (0 <= p && p < mineButtons.length && 0 <= q
									&& q < mineButtons.length) {
								if (mineButtons[p][q].getBomb() == 1)
									count++;
							} // end if
						} // end for
					} // end for

					mineButtons[i][j].setAdjacentBombs(count);
				} // end if

			} // end for loop rows
		} // end for loop columns
	} // end setAdjacentBombs
	
	
	//this class implements the time keeping requirement for the program
	//the game starts keeping time once a left button is clicked,
	//the time is set to display in the format min:second
	//time is incremented each second and displayed
	private class timeInfo implements ActionListener{
		
		public void actionPerformed(ActionEvent a){
			int minute=currTime/60;
			int seconds=currTime-(minute*60);
			timeElapsed.setText(Integer.toString(minute) +":"+Integer.toString(seconds));
			currTime++;
		}
		
	}
	
	


	//this actionPerformed class takes actionEvents from the menu items and determines 
	//what should occur.  each menu item has its own function based on what it is needed
	//to do
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == gReset){
			System.out.println("Reset Pressed");
			
		}
		
		else if(e.getSource() == gTopTen){
			System.out.println("Top Ten");
		}
		else if(e.getSource() == gExit){
			System.out.println("Exit");
			System.exit(0);
		}
		else if(e.getSource() == hHelp){
			JOptionPane.showMessageDialog(mineGUI.this,
					"The game will start when you left click on the game board \n"
					+ "Left clicking will reveal a mine or the number of adjacent mines \n"
					+ "Right clicking will first indicate that that location hides a mine \n"
					+ "Right clicking again will indicate that there might be a mine there\n"
					+ "To win the game,  mark the 10 mines and clear the other 90 squares \n"
	                ,"Help", JOptionPane.PLAIN_MESSAGE );
			
		}
		else if(e.getSource() == hAbout){
			JOptionPane.showMessageDialog(mineGUI.this,
	                  "CS 342 Project Two-Minesweeper \n"
					+" Authors: \n"
	                +"Tianniu Lei(tlei2) \n"
					+"Ryan Szymkiewicz(szymkie1) \n"
					,"About", JOptionPane.PLAIN_MESSAGE );
		}
		else if(e.getSource() == resetButton){
			System.out.println("Reset Button Pressed.");
			
		}
		else{
			System.out.println(e);
		}
		
	}
	
	
	//mouseListener for each button.  This allows for interaction when the grid locations
	//have been pressed.  All code is conducted from mousePressed,  but other
	//compier needed functions are included,  but not used
	class buttonListener implements MouseListener{
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			
			
			//right click options-Windows/Unix-use right click
			//macs-check if control button is pressed
			if(SwingUtilities.isRightMouseButton(e) || e.isControlDown() ){
				
				//cast event to a button so that it can be changed depending on current
				//state-nothing,M or ?
				button currButton = (button)e.getSource();
				
			
				//if 0  then set to M...decrement mines left to be found if spot actually
				//contains a bomb
				if(currButton.getState() == 0){
					currButton.setText("M");
					currButton.setState(1);
					if(currButton.getBomb() == 1){
						mines.decMines();
					}
					
				}
				
				//change to ? state-increment mines left to be found if spot actually 
				//contains one
				else if(currButton.getState() == 1){
					currButton.setText("?");
					if(currButton.getBomb() == 1){
						mines.incMines();
					   }
					currButton.setState(2);
				}
				//return state back to nothing
				else if(currButton.getState() == 2){
					currButton.setText("");
					currButton.setState(0);
				}
				else{
					System.out.println("Problem here-Right Mouseclick");
				}
				
				
				
			}
			
			//left click functions
			else if(SwingUtilities.isLeftMouseButton(e)){
				
				//if no clicks before and we got here,  start timer
				if(numLClicks == 0){
					time.start();
				}
				numLClicks++;
				
				
				
				
				button currButton = (button) e.getSource();
				// if the button hasn't been grayed out, do these actions
				if (currButton.getBackground() != Color.lightGray) {
					if (currButton.getState() == 1
							|| currButton.getState() == 2) {
						// already "M", do nothing
						System.out
								.println("pressed on button that's already marked as bomb, no action done.");
					} else if (currButton.getState() == 0) {
						doClear(mineButtons, currButton.getXcoord(),
								currButton.getYcoord());
					} else {
						System.out.println("Problem here");
					}
				}

				else {
					System.out.println("Problem Here in Left Mouse click");
				}
			}
			// else, do nothing
			else {
				System.out
						.println("user clicked a grayed out button, does nothing");
			}
			}
			
			
		

		@Override
		public void mouseReleased(MouseEvent e) {
			
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
			
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
			
			
		}

		
		
		
	}
	
}