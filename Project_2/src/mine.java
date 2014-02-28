//******************
//CS 342 Project Two-Minesweeper
//Authors
//Tianniu Lei
//Ryan Szymkiewicz
//mine.java
//This file contains methods related to the mines
//get 10 mine positions and decrement/increment how many mines are found
//******************




import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mine{
	
	private Integer[] locations = new Integer[100];//integer array of 100 possible locations
	public int[] bombLocs = new int[10];//array of 10 actual locations
	public int currentMines=10;//mines left to be found
	
	public void setMines(int numMines){
		
		
		for(int i=0; i<100; i++){
			locations[i]=i;
			
		}//set up array containing 100 possible locations
		
		//create an array list of possible locations
		ArrayList<Integer> locationsList = new ArrayList<Integer>(Arrays.asList(locations));
		
		//shuffle the array list 
		Collections.shuffle(locationsList);
		
		//get 10 random locations from shuffled list
		for(int j=0; j<10; j++){
		  bombLocs[j]=locationsList.get(j);
		  
		}
		
		
		
		
		
		
	}
	
	//return array containing bomb locations
	public int[] getMines(){
		
		return bombLocs;
	}
	
	//decrement and increment mines--helps to meet 1/2 game winning conditions
	public void decMines(){
		currentMines=currentMines-1;
	}
	
	public void incMines(){
		currentMines=currentMines+1;
	}
	
	//get current number of mines
	public int getCurrentMines(){
		return currentMines;
	}
	
	
}