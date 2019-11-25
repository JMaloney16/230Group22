import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManagerTest.java - a simple program designed to test the
 * FileManager class to ensure that it works.
 * 
 * @version 0.1
 * @author Dylan Cole
 */

public class FileManagerTest.java{
	
	/**
	 * main
	 * A method that will be used to test the legitimacy
	 * of the FileManager and the static FileReading class
	 */
	public static void main(String[] args){
		private final String TEST_FILE_NAME_1 = "659FhjdXxXx589.txt";
		private final String TEST_FILE_NAME_2 = "Level Example.txt";
		private final Player TEST_PLAYER = new Player(0, 0, "textPlayerImg.png")
		//private final Board TEST_BOARD = new Board([)
		
		//FILE READER TESTS
		
		//Check that the exceptions work properly.
		System.out.println("TEST 1 -- File should not be found --")
		try {
			FileManager.FileReading.createFileScanner(TEST_FILE_NAME_1);
		} catch (FileNotFoundException e) {
			System.out.println("Test Passed: exception is thrown as expected.");
		}
		
		//Check that the FileReader takes in the valid file as Expected
		System.out.println("TEST 2 -- File should be recognized by Reader --")
		try { 
			FileManager.FileReading.createFileScanner(TEST_FILE_NAME_2);
		} catch (FileNotFoundException e) {
			System.out.println("Test Failed: exception is thrown");
		}
		
		//FILE WRITER TESTS
		
	}
}