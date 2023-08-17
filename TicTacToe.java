/*Name: Aisha Lalli
 * Program: TicTacToe.java
 * Date:12/13/2017
 * Description:tic tac to graphical user interface game where user plays against computer, result is always a tie or computer wins
 */
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Font;
import java.awt.GridLayout;

public class TicTacToe {
	JFrame myWindow;
	JButton b[][]=new JButton[3][3];
	int randomNum, xPlays=0, oPlays=0, totalPlays=0, playerNum, i, j, tempi, tempj, empty=0;
	boolean compTurn, goOn=true, oInserted=false, error;
	String name= "", winner="No one";
	
	TicTacToe(){ // constructor
		myWindow = new JFrame("Tic Tac Toe"); // window title
		myWindow.setSize(500,500); // window size
		myWindow.addWindowListener(new WindowAdapter() 	{
			public void windowClosing(WindowEvent e) {System.exit(0);}});
		myWindow.setLayout(new GridLayout(3,3)); // arranges buttons automatically
		
		for(int i=0; i<3;i++)
			for(int j = 0; j<3; j++) {
				b[i][j]=new JButton("-"); // fill all buttons with -
				b[i][j].setFont(new Font("Courier", Font.BOLD, 40));
				b[i][j].addActionListener(new ButtonEvents()); // connect buttons to ButtonEvents class
				myWindow.add(b[i][j]); // add buttons to the window
			}
		myWindow.setVisible(true); // make window visible
		selectPlayer();
		JOptionPane.showMessageDialog(null, "Your are X, Computer is O\n Let's Begin!", "HI THERE...", JOptionPane.INFORMATION_MESSAGE); // game rules
		if(compTurn==true) // if computer starts first, call method here
			compPlay();
	} // end constructor
	public static void main(String[] args) {
		new TicTacToe();
	}
	class ButtonEvents implements ActionListener{ // implement ActionListener to handle buttons being clicked
		@Override
		public void actionPerformed(ActionEvent e) {// use e to detect which button was clicked
			if(compTurn==false && goOn==true) { // user can click a button when it's his turn and while the game is not over yet
				for (int i=0; i<3; i++) { // loop goes through... repeats if box is not available
					for (int j=0;j <3; j++) {
						if(b[i][j]==e.getSource()) { // check which button was clicked
							if(b[i][j].getText().equals("-")) {//if it is empty
								b[i][j].setText("x"); // make button equal to x
								error=false;
							} // indicate that button chosen was valid
							else { // button chosen is already filled
								JOptionPane.showMessageDialog(null, "This Button is Not Available","ERROR", JOptionPane.ERROR_MESSAGE); // 
								error=true;
							}
						}
					} // indicate that button chosen is invalid
				} // End outer for loop
				if(error==false) { // continue with program because button chosen is valid
					totalPlays++; // increment the number of total plays
					countWinner(); // check if x has won
					nextPlayer(); // change player from x to o => to computer
					if(compTurn==true && goOn==true) { // total play is not 9 yet// changed it from endGame() see if it works????????
						compPlay(); // computer will play
						countWinner();} // computer is done playing and winner is checked
				}
			} // End if user's turn to play
		} // End actionPerformed()
	} // End ButtonEvents()
	void selectPlayer() { // method to select a player in the beginning of the game
		Random rand = new Random();
		randomNum = rand.nextInt(2); // create a new random number
		playerNum = randomNum; // set the value to playernum which will be used in nextplayer()
		if (randomNum==0) 
			name = "Computer Plays First";
		else // 1
			name ="You Play First";
		if(totalPlays==0) {
			JOptionPane.showMessageDialog(null, name ,"First Player Generator", JOptionPane.INFORMATION_MESSAGE);
		}
		if (randomNum==0) // set variable to t or f depending on decision
			compTurn=true;
		else
			compTurn=false;
		reset(); // set xplay and oplay to 0
	}
	void reset() {xPlays=0; oPlays=0;} 
	void nextPlayer() {	// change players
		playerNum++; // add 1
		if(playerNum%2==0) // check remainder
			compTurn=true; // if no remainder = 0 comp turn
		else // rem = 1 user turn
			compTurn=false;
	}
	void compPlay(){ // program plays
		totalPlays++; // increment total number of plays
		if (randomNum==0) { // offensive play
			if(totalPlays==3)
				oWeirdMove(); // if o is center and x is a corner and next move is o, should not be wasted on a corner in the same diagonal 
			else if(totalPlays<3) // pick a corner or center
				favButtons(); // center and corner buttons 
			else // totalPlays > 3
				oxPossMove(); 
		} // offensive play ends		
		else { // randomNum==1 // defensive play
			if(totalPlays<=2) 
				favButtons();
			else 
				oxPossMove();
		}
		nextPlayer(); // go to next player
	}
	boolean endGame() { // return true if game is over
		if(xPlays==3 || oPlays==3 || totalPlays==9) {// one player has a full line and has won
			JOptionPane.showMessageDialog(null,  winner + " is the Winner" ,"Game Results", JOptionPane.INFORMATION_MESSAGE);		
			return true;}
		else // there is no winner yet and there are possible moves
			return false;
	}
	void favButtons() { // initially comp will fill as many of these buttons as poss, before offensive and defensive moves are initiated
		if(b[1][1].getText().equals("-")) // center
			b[1][1].setText("o");
		else if(b[0][0].getText().equals("-")) // corners
			b[0][0].setText("o");
		else if(b[0][2].getText().equals("-"))
			b[0][2].setText("o");
		else if(b[2][0].getText().equals("-"))
			b[2][0].setText("o");
		else if(b[2][2].getText().equals("-"))
			b[2][2].setText("o");
	}
	void oWeirdMove() { // this move is for when computer starts and 
		boolean found=false;
		for (i=0;i<3;i++) { // diagonal 1
			j=i;
			countPlays();} 
		if(xPlays==1) { // if player has a corner button in diag 1
			j=2; // go to diag 2 and put o in a corner that is available
			for(i=0;i<3;i++) { // diagonal 2
				if(b[i][j].getText().equals("-")) {
					b[i][j].setText("o");
					found = true; 
					break;}
				j--;}
			reset();}
		if(found ==false) { // an x was not found i the first diagonal loop
			j=2; // go look in second loop for an x in the corner if it was not found in forst loop
			for(i=0;i<3;i++) { // diagonal 2
				countPlays();
				j--;}
			if(xPlays==1) {
				for (i=0;i<3;i++) { // diagonal 1
					j=i;
					if(b[i][j].getText().equals("-")) {
						b[i][j].setText("o");
						found = true;
						break;}}
				reset();}
		}
		if(found==false) // user used a non corner box
			favButtons(); 
	}
	void countPlays() { // method to count number of o or x plays //// row count column count or diagonal counts, x and o plays reach a max of 3 then reset 
		if(b[i][j].getText().equals("x")) 
			xPlays++;
		else if(b[i][j].getText().equals("o")) 
			oPlays++;
	}
	void getEmpty() { // get empty number of buttons 
		if(b[i][j].getText().equals("-")) {
			tempi=i; tempj=j; empty++;}
	}
	void oPossWin() { // check the row colomn or diagonal where o can win and win it
		if(oPlays==2 && empty==1) {
			b[tempi][tempj].setText("o");
			oPlays++;
			oInserted=true; // use this to skip the rest of loops in oxpossmoves
			selectWinner();}
		else
			oInserted=false;
	}
	void xPossWin() { // check the row columns diagonals where x can win a nd block it
		if(xPlays==2 && empty==1) {
			b[tempi][tempj].setText("o");
			oPlays++;
			oInserted=true;
			selectWinner();}
		else
			oInserted=false;
	}
	void selectWinner() { // check if there are 3 plays in a row
		if(xPlays==3) { 
			winner="x";goOn=false;}
		else if(oPlays==3) {
			winner="o";goOn=false;}
		else 
			reset();		
		if(endGame()==true) 
			goOn = false;
	}
	void countWinner() { // go through loops to check for winner
		if(totalPlays>=5) {
			for(i=0;i<3;i++) { // go horizontally
				for(j=0;j<3;j++) 
					countPlays();
				selectWinner();
				if(goOn==false)
					break;
			}
			if(goOn==true) {
				for(j=0;j<3;j++) { // go vertically down
					for(i=0;i<3;i++)  
						countPlays();
					selectWinner();
					if(goOn==false)
						break;
				}
			}
			if(goOn==true) {
				for (i=0;i<3;i++) { // diagonal 1
					j=i;
					countPlays();
				}
				selectWinner();
			}
			if(goOn==true) {
				j=2;
				for(i=0;i<3;i++) { // diagonal 2
					countPlays();
					j--;
				}
				selectWinner();
			}
		}
	}
	void oxPossMove() { // first check if o can win then check if x needs to be blocked
		for(i=0;i<3;i++) { // go horizontally
			for(j=0;j<3;j++) {
				countPlays();
				if(b[i][j].getText().equals("-"))
					getEmpty();
			}
			oPossWin();reset();empty=0;
			if(oInserted==true)
				break;
		}
		if(oInserted==false) {
			for(j=0;j<3;j++) { // go vertically down
				for(i=0;i<3;i++) { 
					countPlays();
					if(b[i][j].getText().equals("-"))
						getEmpty();
				}
				oPossWin();reset();empty=0;
				if(oInserted==true)
					break;
			}
		}
		if(oInserted==false) {
			for (i=0;i<3;i++) { // diagonal 1
				j=i;
				countPlays();
				if(b[i][j].getText().equals("-"))
					getEmpty();
			}
			oPossWin();reset();empty=0;		
		}
		if(oInserted==false) {
			j=2;
			for(i=0;i<3;i++) { // diagonal 2
				countPlays();
				if(b[i][j].getText().equals("-"))
					getEmpty();
				j--;
			}
			oPossWin();reset();empty=0;		
		}
		if(oInserted==false) {
			for(i=0;i<3;i++) { // go horizontally
				for(j=0;j<3;j++) {
					countPlays();
					if(b[i][j].getText().equals("-"))
						getEmpty();
				}
				xPossWin();reset();empty=0;
				if(oInserted==true)
					break;
			}
		}
		if(oInserted==false) {
			for(j=0;j<3;j++) { // go vertically down
				for(i=0;i<3;i++) { 
					countPlays();
					if(b[i][j].getText().equals("-"))
						getEmpty();
				}
				xPossWin();reset();empty=0;
				if(oInserted==true)
					break;
			}
		}
		if(oInserted==false) {
			for (i=0;i<3;i++) { // diagonal 1
				j=i;
				countPlays();
				if(b[i][j].getText().equals("-"))
					getEmpty();
			}
			xPossWin();reset();empty=0;		
		}
		if(oInserted==false) {
			j=2;
			for(i=0;i<3;i++) { // diagonal 2
				countPlays();
				if(b[i][j].getText().equals("-"))
					getEmpty();
				j--;
			}
			xPossWin();reset();empty=0;		
		}
		if(oInserted==false)  // not there was no o or x possible wins, so the last empty spot that was checked is filled
			b[tempi][tempj].setText("o");
	}
} // End TicTacToe class

