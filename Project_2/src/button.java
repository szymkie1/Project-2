//******************
//CS 342 Project Two-Minesweeper
//Authors
//Tianniu Lei
//Ryan Szymkiewicz
//button.java
//The button.java class extends JButton,  and functions as the mine
//buttons for the minesweeper program.  By creating a new button instead of
//using JButtons,  we are able to add a greater amount of control and functionality
//******************





import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.Timer;


@SuppressWarnings("serial")//some swing elements are apparently deprecated.  This
//suppresses warnings related to that-does not affect functionality
public class button extends JButton{
	
	//set and get x and y coordinates in 2D grid representation-
	//initialized with nested for loops in mineGUI class
	int xCoord=-1;
	public void setXcoord(int x){
		xCoord=x;
	}
	
	int yCoord=-1;
	public void setYcoord(int y){
		yCoord=y;
	}
	
	public int getXcoord(){
		return xCoord;
	}
	
	public int getYcoord(){
		return yCoord;
	}
	
	//get and set position.  This represents a spot on the board from 0-99
	//and helps with setting bomb locations
	int pos=-1;
	public void setPos(int z){
		pos=z;
	}
	
	public int getPos(){
		return pos;
	}
	
	//set and get game states.  There are 3 states mainly used for right clicks
	//1 == untouched
	//2 == "M" set-flag
	//3 == "?" set
	int state=0;
	public void setState(int a){
		state=a;
	}
	
	public int getState(){
		return state;
	}
	
	//these functions set and get the bomb locations.  The locations are set in
	//the mine.java class.  This allows for a simple comparison to determine if a square
	//should explode
	int isBomb=0;
	public void setBomb(int b){
		isBomb=b;
	}
	
	public int getBomb(){
		return isBomb;
	}
	
	//these functions get and set adjacent bombs--useful when completing game algorithm
	int adjacent=0;
	public void setAdjacentBombs(int n){
		adjacent=n;
	}
	
	public int getAdjacentBombs(){
		return adjacent;
	}
	
	//since our mine squares are buttons,  their text must be string.  use this instead
	//of typing longer information each time
	public String getAdjacentBombsString(){
		return Integer.toString(adjacent);
	}
	
	
	}
	
	
	
	
