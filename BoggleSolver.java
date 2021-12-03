package boggle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class BoggleSolver {

	ArrayList<String> dictionary = new ArrayList<>();
	HashSet<String> playWords = new HashSet<>();

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A - Z.)
	public BoggleSolver(String dictionaryName) {
		try {
			Scanner scr = new Scanner(new File(dictionaryName));
			while (scr.hasNext()) {
				dictionary.add(scr.next());
			}
		} catch (Exception e) {
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable
	// object
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		// TODO
		for (int r = 0; r < board.rows(); r++) {
			for (int c = 0; c < board.cols(); c++) {
				boolean[][] used = new boolean[board.rows()][board.cols()]; 
				getWord(board, r, c, "", used);
			}
		}

		return playWords;
	}

	// recursively find words by traversing board
	public void getWord(BoggleBoard board, int row, int col, String str, boolean[][] used) {
		if(used[row][col]) {
			return;
		}
		
		char letter = board.getLetter(row, col);
		if (letter == 'Q') {
			str += "Qu";
		} else {
			str += letter;
		}

		if (dictionary.contains(str.toUpperCase()) && str.length() > 2) {
			playWords.add(str);
		}
		
		used[row][col] = true;

		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r >= 0 && r != row && c != col && c >= 0 && r < board.rows() && c < board.cols()) {					
					getWord(board, r, c, str, used);
				}			
			}
		}
		used[row][col] = false;
	}

	
	public int scoreOf(String word) {
		switch (word.length()) {
		case 0:
		case 1:
		case 2:
			return 0;
		case 3:
		case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 5;
		default:
			return 11;
		}

	}

	public static void main(String[] args) {
		System.out.println("WORKING");

		final String PATH = "./data/";
		BoggleBoard board = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); // should print 84

		// new BoggleGame(4, 4);
	}

}
