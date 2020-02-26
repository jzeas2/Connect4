package zeasJ_A3;
import java.util.Scanner;

/**
 * This project allows the user to play connect 4 against a computer. 
 * @author jonathanzeas
 *
 */

public class zeasJonathanAssignment3 {
	public static void main(String[] args) {
		/**
		 Runs the main function.
		 */
		//initialize variables
		int[][] board = new int [6][7];
		String location = "";
		int plyrLocation = 0;
		int cpuLocation = 0;
		int winner = 0;
		boolean plyrTurn = true;
		//welcome player to game
		System.out.println("Welcome to Connect 4!");
		//while there's no winner, run through an iteration of player and CPU turn
		while (winner == 0) {
			printBoard(board);
			plyrLocation = location(board, location, plyrLocation);
			board = boardEdit(board, cpuLocation, plyrLocation, plyrTurn);
			plyrTurn = false;
			//keeps computer from taking their turn if the player already won
			winner = checkWinner(board);
			if (winner == 0) {
				//runs cpu turn
				cpuLocation = cpuLocation(board);
				board = boardEdit(board, cpuLocation, plyrLocation, plyrTurn);
				plyrTurn = true;
				winner = checkWinner(board);
			}
		}
		//prints the board one final time
		printBoard(board);
		//declares the winner
		if (winner == 1) {
			rest();
			System.out.println("\nCongratulations, you won!");
		} else {
			System.out.println("\nSorry, you lost.");
		}
		
	}
	public static int location(int board [][], String location, int locationInt) {
		/**
		 Asks the player to enter the location where they'd like to drop their next piece.
		 */
		locationInt = 9;
		Scanner input = new Scanner(System.in);
		//asks user for input
		System.out.println("\nPlease enter the number where you'd like to drop your next piece.");
		location = input.next();
		//try to convert location to an integer
		try {
			locationInt = Integer.parseInt(location);
			//if there's no empty space, don't allow player to go there
			if (board[0][locationInt] !=0) {
				locationInt = 9;
			}
		//if not able to convert to number, or if number isn't an acceptable column value, don't allow player to go there
		} catch(NumberFormatException e) {
			locationInt = 9;
		}catch(IndexOutOfBoundsException e) {
			locationInt = 9;
		}
		//if location value is not 0-6, keep asking for new value until it is.
		while (locationInt !=1 && locationInt!= 2 &&locationInt!= 3 &&locationInt!= 4 &&locationInt!= 5 &&locationInt!= 6 &&locationInt!= 0) {
			System.out.println("\nPlease enter a valid location to drop your next piece.");
			location = input.next();
			try {
				locationInt = Integer.parseInt(location);
				if (board[0][locationInt] !=0) {
					locationInt = 9;
				}
			} catch(NumberFormatException e) {
				locationInt = 9;
			}catch(IndexOutOfBoundsException e) {
				locationInt = 9;
			}

		}
		//once player enters a valid column, confirm their location, wait 1 second, then return their input.
		System.out.println("\nYou've dropped a piece in column " + Integer.toString(locationInt) + ".");
		rest();
		return locationInt;
	}
	public static void printBoard(int board [][]) {
		/**
		 Prints the board for the player.
		 */
		System.out.println("\nHere is a copy of the board:\n");
		rest();
		// prints column indicator
		System.out.println("Column -  0 1 2 3 4 5 6\n");
		//for each row, print a string of spaces to line up the rows, then print each column value, separated by a space
		for(int i = 0; i<6; i++)
		{
			System.out.print("         ");
		    for(int j = 0; j<7; j++)
		    {
		    	System.out.print(" ");
				// if there's a 1 in the location, print P for Player
				if (board[i][j] == 1) {
					System.out.print("P");
				// if there's a 2 in the locaiton, print C for CPU
				} else if (board[i][j] == 2){
					System.out.print("C");
				} else {
					System.out.print("0");
				}
		    }
		    System.out.println();
		}

	}
	public static int cpuLocation(int board[][]) {
		/**
		 * Picks where the computer is going to play. First sees if it can win in any direction, then if player is going to win in 
		 * any direction, and finally if it has 2 in a row.
		 */
		//if cpu can win, pick that spot
		//parses each value in the array
		for(int row = 0; row<6; row++)
		{
		    for(int column = 0; column<7; column++)
		    {
		    	//finds value in each spot
		    	int value = board[row][column];
		    	//if that value is 2 (cpu) will check values surrounding it to see if there's a way it can win.
		        if (value == 2) {
		        	//up from spot, fourth empty
		        	try {
		        		//try and take integer values from each spot 3 up. If it's unable to do that, then it's not a viable option.
		        		int value2 = board[row-1][column];
		        		int value3 = board[row-2][column];
		        		int value4 = board[row-3][column];
	        			//if CPU has a piece in all 3 spots, and the fourth is empty, play in that column
		        		if (value == value2 && value2 == value3 && value4 == 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + column + ".");
		        			return column;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	// up diagonal left, fourth value empty
		        	try {
		        		int value2 = board[row-1][column-1];
		        		int value3 = board[row-2][column-2];
		        		int value4 = board[row-3][column-3];
		        		int value5 = board[row-2][column-3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5 != 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-3)+ ".");
		        			return column-3;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal left, value back empty
		        	try {
		        		//try and take integer values from each spot 3 up. If it's unable to do that, then it's not a viable option.
		        		int value2 = board[row-1][column-1];
		        		int value3 = board[row-2][column-2];
		        		int value4 = board[row+1][column+1];
		        		//try and get the value of the cell under value 4 and check if it's empty.
		        		try {
		        		int value5 = board[row][column+1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5 != 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1)+ ".");
		        			return column+1;
		        		}
		        		//if there's no value under value4, it must be at the bottom of the array, so continue as though there is a piece there
		        		} catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1)+ ".");
			        			return column+1;
			        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal right, fourth value empty
		        	try {
		        		int value2 = board[row-1][column+1];
		        		int value3 = board[row-2][column+2];
		        		int value4 = board[row-3][column+3];
		        		int value5 = board[row-2][column+3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
		        			return column+3;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal right, value back empty
		        	try {
		        		int value2 = board[row-1][column+1];
		        		int value3 = board[row-2][column+2];
		        		int value4 = board[row+1][column-1];
		        		try {
		        		int value5 = board[row+2][column-1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1)+ ".");
		        			return column-1;}
		        		} catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1)+ ".");
			        			return column-1;}	
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//left to right, fourth value empty
		        	try {
		        		int value2 = board[row][column+1];
		        		int value3 = board[row][column+2];
		        		int value4 = board[row][column+3];
		        		try {
		        		int value5 = board[row+1][column+3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
		        			return column+3;
		        		}
		        		} catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
			        			return column+3;
		        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	// left to right, value back empty
		        	try {
		        		int value2 = board[row][column+1];
		        		int value3 = board[row][column+2];
		        		int value4 = board[row][column-1];
		        		try {
		        		int value5 = board[row+1][column-1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1)+ ".");
		        			return column-1;
		        		}
		        		}catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1)+ ".");
			        			return column-1;
			        		}	
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        }
		        
		    }
		}
		//if player will win, pick that spot
		//parses each value in the array
		for(int row = 0; row<6; row++)
		{
		    for(int column = 0; column<7; column++)
		    {
		    	int value = board[row][column];
		        if (value == 1) {
		        	//up from spot, fourth empty
		        	try {
		        		//try and take integer values from each spot 3 up. If it's unable to do that, then it's not a viable option.
		        		int value2 = board[row-1][column];
		        		int value3 = board[row-2][column];
		        		int value4 = board[row-3][column];
		        		if (value == value2 && value2 == value3 && value4 == 0) {
		        			//if player has a piece in all 3 spots, and the fourth is empty, play in that column
			        		System.out.println("\nThe CPU Chose to play a piece in column " + column+ ".");
		        			return column;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	// up diagonal left, fourth value empty
		        	try {
		        		int value2 = board[row-1][column-1];
		        		int value3 = board[row-2][column-2];
		        		int value4 = board[row-3][column-3];
		        		//value5 used for checking the spot underneath where the CPU wants to play, to make sure there's a piece there.
		        		int value5 = board[row-2][column-3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5 != 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-3)+ ".");
		        			return column-3;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal left, value back empty
		        	try {
		        		int value2 = board[row-1][column-1];
		        		int value3 = board[row-2][column-2];
		        		int value4 = board[row+1][column+1];
		        		try {
		        		int value5 = board[row+2][column+1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1)+".");
		        			return column+1;
		        		}
		        			
		        		}catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1)+".");
			        			return column+1;
			        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal right, fourth value empty
		        	try {
		        		int value2 = board[row-1][column+1];
		        		int value3 = board[row-2][column+2];
		        		int value4 = board[row-3][column+3];
		        		int value5 = board[row-2][column+3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
		        			return column+3;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//up diagonal right, value back empty
		        	try {
		        		int value2 = board[row-1][column+1];
		        		int value3 = board[row-2][column+2];
		        		int value4 = board[row+1][column-1];
		        		try {
		        		int value5 = board[row+2][column-1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
		        			return column-1;
		        		} 
		        		}catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
			        			return column-1;
		        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//left to right, fourth value empty
		        	try {
		        		int value2 = board[row][column+1];
		        		int value3 = board[row][column+2];
		        		int value4 = board[row][column+3];
		        		try {
		        		int value5 = board[row+1][column+3];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5 != 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
		        			return column+3;
		        		}
		        		} catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+3)+ ".");
			        			return column+3;
			        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	// left to right, value back empty
		        	try {
		        		int value2 = board[row][column+1];
		        		int value3 = board[row][column+2];
		        		int value4 = board[row][column-1];
		        		try {
		        		int value5 = board[row+1][column-1];
		        		if (value == value2 && value2 == value3 && value4 == 0 && value5!=0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
		        			return column-1;
		        		}
		        		} catch (IndexOutOfBoundsException e) {
			        		if (value == value2 && value2 == value3 && value4 == 0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
			        			return column-1;
			        		}
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        }
		        
		    }
		}
		//if cpu has 2 in a row, pick the third spot
		//parses each value in the array
		for(int row = 0; row<6; row++)
		{
		    for(int column = 0; column<7; column++)
		    {
		    	int value = board[row][column];
		    	//straight up, third empty
		        if (value == 2) {
		        	try {
		        		//try and take integer values from each spot 2 up. If it's unable to do that, then it's not a viable option.
		        		int value2 = board[row-1][column];
		        		int value3 = board[row-2][column];
		        		if (value == value2 && value3 == 0) {
			        		System.out.println("\nThe CPU Chose to play a piece in column " + column + ".");
		        			return column;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        //up to right, third empty
			        	try {
			        		int value2 = board[row-1][column+1];
			        		int value3 = board[row-2][column+2];
			        		int value4 = board[row-1][column+2];
			        		if (value == value2 && value3 == 0 && value4!=0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+2) + ".");
			        			return column+2;
			        		}
			        	} catch (IndexOutOfBoundsException e) {
			        	}
		        //up to right, one back empty
			        	try {
			        		int value2 = board[row-1][column+1];
			        		int value3 = board[row+1][column-1];
			        		try {
			        		int value4 = board[row+2][column-1];
			        		if (value == value2 && value3 == 0 && value4!=0) {
				        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
			        			return column-1;
			        		}
			        		}
			        		catch (IndexOutOfBoundsException e) {
				        		if (value == value2 && value3 == 0) {
					        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
				        			return column-1;
				        		}
			        		}
			        	} catch (IndexOutOfBoundsException e) {
			        	}
			// up to left, third empty
				        	try {
				        		int value2 = board[row-1][column-1];
				        		int value3 = board[row-2][column-2];
				        		int value4 = board[row-1][column-2];
				        		if (value == value2 && value3 == 0 && value4!=0) {
					        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-2)+ ".");
				        			return column-2;
				        		}
				        	} catch (IndexOutOfBoundsException e) {
				        	}
	        //up to left, one back empty
					    	 try {
					        	int value2 = board[row-1][column-1];
					        	int value3 = board[row+1][column+1];
					        	try {
				        		int value4 = board[row+2][column+1];
					        	if (value == value2 && value3 == 0 && value4!=0) {
					        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1) + ".");
					        		return column+1;
					        		}
					        	} catch (IndexOutOfBoundsException e) {
						        	if (value == value2 && value3 == 0) {
						        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+1) + ".");
						        		return column+1;
						        		}
					        	}
					        	} catch (IndexOutOfBoundsException e) {
					        	}
			//straight across, third empty
					    	  try {
						        	int value2 = board[row][column+1];
						        	int value3 = board[row][column+2];
						        	try {
					        		int value4 = board[row+1][column+2];
						        	if (value == value2 && value3 == 0 && value4!=0) {
						        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+2)+ ".");
						        		return column+2;
						        		}
						        	}catch (IndexOutOfBoundsException e) {
							        	if (value == value2 && value3 == 0) {
							        		System.out.println("\nThe CPU Chose to play a piece in column " + (column+2)+ ".");
							        		return column+2;
							        		}	
						        	}
						        	} catch (IndexOutOfBoundsException e) {
						        	}
			//straight across, one back empty
					    	  try {
						        	int value2 = board[row][column+1];
						        	int value3 = board[row][column-1];
						        	try {
					        		int value4 = board[row+1][column-1];
						        	if (value == value2 && value3 == 0 && value4!=0) {
						        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
						        		return column-1;
						        		}
						        	} catch (IndexOutOfBoundsException e) {
							        	if (value == value2 && value3 == 0) {
							        		System.out.println("\nThe CPU Chose to play a piece in column " + (column-1) + ".");
							        		return column-1;
							        		}
						        	}
						        	} catch (IndexOutOfBoundsException e) {
						        	}
					       }
				        


		        }
		}
		//otherwise, pick a random spot.
		//initialize variables
		int counter = 0;
		int numOfColumns = 0;
		String listOfColumns = "";
		//for values 0-6
		while (counter < 7) {
			//if the top value in that column is empty, add that column number to a list of columns, and add 1 to the number of columns
			if (board[0][counter] == 0) {
				numOfColumns++;
				listOfColumns = listOfColumns + counter;
			}
			counter++;
		}
		//creates random double between 0 and numOfColumns, not including numOfColumns
		double cpuLocation = Math.random() * numOfColumns;
		//changes double to int
		int readyForFirstColumn = (int)cpuLocation;
		//counts down from readyForFirstColumn to 0, removing one item from listOfColumns each time
		while (readyForFirstColumn > 0) {
			readyForFirstColumn--;
			listOfColumns = listOfColumns.substring(1);
		}
		//once readyForFirstColumn = 0, gets value of first number in listOfColumns
		readyForFirstColumn = Character.getNumericValue(listOfColumns.charAt(0));
		//prints out that the cpu chose that value, then returns it to main function
		System.out.println("\nThe CPU Chose to play a piece in column " + readyForFirstColumn+".");
		rest();
		return readyForFirstColumn;
	}
	public static int[][] boardEdit(int board [][], int cpuLocation, int plyrLocation, boolean plyrTurn) {
		/**
		 * Edits board after each turn
		 */
		//initialize variables
		boolean addedValue = false;
		int lowest = 5;
		//if it was the player's turn, takes player's column and places a "1" in the lowest empty cell in that column
		if (plyrTurn == true) {
		while (addedValue == false) {
			if (board[lowest][plyrLocation] == 0) {
				board[lowest][plyrLocation] = 1;
				addedValue = true;
			} else {
				lowest--;
			}
		}
		//if it was the CPU turn, takes player's column and places a "2" in the lowest empty cell in that column
		} else {
			while (addedValue == false) {
				if (board[lowest][cpuLocation] == 0) {
					board[lowest][cpuLocation] = 2;
					addedValue = true;
				} else {
					lowest--;
		}
			}
		}
		return board;
	}
	public static int checkWinner(int board[][]) {
		/**
		 * Checks the board to see if there's a winner after each turn.
		 */
		int winner = 0;
		// for each value in the board
		for(int row = 0; row<6; row++)
		{
		    for(int column = 0; column<7; column++)
		    {
		    	int value = board[row][column];
		    	//as long as the cell isn't empty
		        if (value != 0) {
		        	try {
		        		//check diagonal up and to right
		        		int value2 = board[row-1][column-1];
		        		int value3 = board[row-2][column-2];
		        		int value4 = board [row-3][column-3];
		        		//if all 4 values are equal, return their value as the winner.
		        		if (value == value2 && value2 == value3 && value3 == value4) {
		        			winner = value;
		        		} 
		        		}catch(IndexOutOfBoundsException e) {
		        		}
		        		// check diagonal up and to left
		        	try {
		        		int value2 = board[row-1][column+1];
		        		int value3 = board[row-2][column+2];
		        		int value4 = board[row-3][column+3];
		        		if (value == value2 && value2 == value3 && value3 == value4) {
		        			winner = value;
		        		}
		        		//check straight across
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	try {
		        		int value2 = board[row][column+1];
		        		int value3 = board[row][column+2];
		        		int value4 = board[row][column+3];
		        		if (value == value2 && value2 == value3 && value3 == value4) {
		        			winner = value;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        	//check straight down
		        	try {
		        		int value2 = board[row+1][column];
		        		int value3 = board[row+2][column];
		        		int value4 = board[row+3][column];
		        		if (value == value2 && value2 == value3 && value3 == value4) {
		        			winner = value;
		        		}
		        	} catch (IndexOutOfBoundsException e) {
		        	}
		        }
		    }
		}
		//if nobody won, return 0
		return winner;
	}
	public static void rest() {
		/**
		 * Game takes a 1 second break where this is inserted makes for smoother gameplay in the console
		 */
		try
		{
		    Thread.sleep(1000);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
	}
}
