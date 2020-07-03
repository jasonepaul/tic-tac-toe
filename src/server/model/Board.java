package server.model;

/**
 * Provides fields and methods for the Tic-Tak-Toe board.
 * @author ?
 * @version 1.0
 * @since Oct 6, 2019
 */
public class Board implements Constants{

	/**
	 * 2D array for the board whose elements contain an 'X', 'O', or space character.
	 */
	private char[][] theBoard;
	
	/**
	 * The total number of X's and O's that are on the board.
	 */
	private int markCount;

	/**
	 * Constructs a board and initializes each square (element of theBoard)
	 * with a space character, and sets the markCount to 0.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Gets the mark of a square on the board. 
	 * @param row The row of the mark.
	 * @param col The column of the mark.
	 * @return The mark at the row and column (X, O, or space character).
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
	
	/**
	 * Returns whether all of the boards squares are full.
	 * @return True if the board is full, false if it's not full.
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Calls checkWinner to see whether X has won.
	 * @return True if X player won, false if X player didn't win.
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Calls checkWinner to see whether O has won.
	 * @return True if O player won, false if O player didn't win.
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Displays the board to the console.
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Adds a mark to the board, and increments the mark count.
	 * @param row The row to add the mark to.
	 * @param col The column to add the mark to.
	 * @param mark The mark to add to the board.
	 */
	public void addMark(int row, int col, char mark) {
		theBoard[row][col] = mark;
		markCount++;
	}
	
	/**
	 * Clears the board by assigning a space character to each square.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * getter to return the number of marks on the board.
	 * @return The number of marks on the board.
	 */
	public int getMarkCount() {
		return this.markCount;
	}
	
	/**
	 * Checks whether one of the players has won the game.
	 * @param mark The character that's being checked for a win.
	 * @return Int 1 if the character has won, int 0 otherwise. 
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Helper function for display(). Displays the column headers to the console.
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}

	/**
	 * Helper function for display(). Displays horizontal line to the console.
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**
	 * Helper function for display(). Displays column lines and spaces to the
	 * console.
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
