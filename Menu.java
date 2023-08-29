package ie.atu.sw;

import java.io.File;
import java.util.*; // IMPORT JAVA UTILS PACKAGE TO ALLOW ACCESS TO SCANNER

public class Menu {
	private boolean keepRunning = true;// PRIVATE BOOLEAN SET TO TURE TO KEEP MENU RUNNING UNTIL USER QUITS
	private Scanner scanner; // SCANNER FOR MENU SYSTEM
	private String fileDirectory = null; // STRING FOR ALLOWING ACCES TO USER SPECIFIED DIRECTORY
	private int nGramSize = 0; // INT FOR USER SPECIFIED NGRAM SIZE
	private String csvFile = null; // STRING FOR USER SPECIFIED CSV FILE

	public Menu() {
		scanner = new Scanner(System.in); // NEW INSTANCE OF SCANNER FOR MENU
	}

	// Method to start application
	public void start() throws Exception {
		while (keepRunning) {
			showOptions(); // CALLS showOptions METHOD TO PRINT MENU OPTIONS FOR APPLICATION

			int choice = Integer.parseInt(scanner.next()); // take integer as user input
			switch (choice) { // switch statement for menu options
			case 1 -> getTextDirectory(); // CALL getTextDirectory METHOD FOR USER SPECIFIED DIRECTORY
			case 2 -> listFiles(); // CALL listFiles METHOD TO LIST FILES IN USER SPEC
			case 3 -> getNGramSize(); // CALL getNGramSize METHOD FOR USER SEPCIFIED NGRAM SIZE
			case 4 -> getCSVFile(); // CALL getCSVFile METHOD FOR USER SPECIFIED CSV FILE
			case 5 -> buildNGrams(); // CALL buildNGrams METHOD TO CREATE CSV FILE WITH NGRAMS
			case 6 -> { // CASE 5 = APPLICATION SHUT DOWN
				System.out.println("[INFO] Shutting down...please wait..."); // Shut down message to user //$NON-NLS-1$
				keepRunning = false; // Application shuts down
			}
			default -> System.out.println("[ERROR] Invalid input. Select from option 1 to 6!!"); // ERROR //$NON-NLS-1$
																									// message for
																									// invalid user
																									// Input

			}

		}

	}

	// Method to allow user define directory with text files
	public void getTextDirectory() {
		scanner = new Scanner(System.in); // New scanner instance
		System.out.println("Enter directory File Path"); // Message to prompt user to enter desired file //$NON-NLS-1$
															// path
		System.out.println("MAC e.g /Users/johndoe/Desktop/Project/textfiles"); // File path example for //$NON-NLS-1$
																				// MAC users
		this.fileDirectory = scanner.next(); // take user input for file path
		System.out.println("File Directory Located"); // let user know file path located //$NON-NLS-1$
		System.out.println("User's File Path: " + fileDirectory); // show file path //$NON-NLS-1$
	}

	// EXTRA METHOD ADDED TO MENU TO LIST FILES IN USERS DIRECTORY
	public void listFiles() {
		File folder = new File(fileDirectory); // New file folder = user specified Directory
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName()); //$NON-NLS-1$
			}
		}
	}

	// Method for choosing NGram size
	public void getNGramSize() {
		System.out.println("Enter N-Gram size bewteen 1 and 5!!"); // Message to prompt user to input //$NON-NLS-1$
																	// NGram selection
		int n = Integer.parseInt(scanner.next()); // This returns an integer, given a string representation
		if (n > 0 && n <= 5) { // NGram size has to greater than 0 but less than or equal to 5
			this.nGramSize = n; // Set NGram size from int n (user input)
			System.out.println("Your Selected N-Gram Size is : " + nGramSize); // Inform user of Ngram //$NON-NLS-1$
																				// selection
		} else {
			System.out.println("[ERROR] Invalid NGram Size Selected!!"); // Invalid Ngram error message //$NON-NLS-1$
			getNGramSize(); // restart process
		}

	}

	// Method for CSV file selection
	public void getCSVFile() {
		System.out.println("Specify name for csv file"); // ask user for csv file name to print NGrams to //$NON-NLS-1$
		String tempFile = scanner.next(); // create temporary file from user input
		this.csvFile = tempFile; // Set csv file as the temporary file
		System.out.println("Your Selected Filename is : " + tempFile); // Inform user of file name //$NON-NLS-1$
																		// selection
	}

	public void buildNGrams() throws Exception {
		if ((fileDirectory != null) && (nGramSize != 0) && (csvFile != null)) { // If fileDirectory, NGram Size &
																				// csvFile have correct input, create
																				// new instance of parser
			Parser p = new Parser(nGramSize, csvFile, fileDirectory); // New instance of the Parser class
			progressMeter(); // CALL progressMeter METHOD FOR PROGRESS BAR ON BUILDING NGRAMS CSV
			p.parseDirectory(); // calls parseDirectory method to read through directory files
		} else {
			System.out.println("[ERROR] Pleae Enter Dirtctory, NGram size & CSV File Name to continue!!"); // ERROR //$NON-NLS-1$
																											// MESSAGE
			start(); // Restart process
		}
	}

	private void showOptions() { // System prints out application details and user options
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************"); //$NON-NLS-1$
		System.out.println("*      ATU - Dept. Computer Science & Applied Physics      *"); //$NON-NLS-1$
		System.out.println("* -------------------------------------------------------- *"); //$NON-NLS-1$
		System.out.println("*                  N-Gram Frequency Builder                *"); //$NON-NLS-1$
		System.out.println("* -------------------------------------------------------- *"); //$NON-NLS-1$
		System.out.println("************************************************************"); //$NON-NLS-1$
		System.out.println("(1) --> Specify Text File Directory"); //$NON-NLS-1$
		System.out.println("(2) --> List Files in Directory"); //$NON-NLS-1$
		System.out.println("(3) --> Specify n-Gram Size"); //$NON-NLS-1$
		System.out.println("(4) --> Specify Output File"); //$NON-NLS-1$
		System.out.println("(5) --> Build n-Grams "); //$NON-NLS-1$
		System.out.println("(6) --> Quit"); //$NON-NLS-1$

		// Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT); // Change colour of console text
		System.out.print("Select Option [1-5]>"); //$NON-NLS-1$
		System.out.println(); // print new line

	}

	// Starts the progress meter by calling the printProgress method and
	// uses a loop to repeat until progress reaches 100%.
	private static void progressMeter() throws InterruptedException {
		System.out.print(ConsoleColour.GREEN); // Change the colour of the console text
		int size = 100; // The size of the meter. 100 equates to 100%
		for (int i = 0; i < size; i++) { // The loop equates to a sequence of processing steps
			printProgress(i + 1, size); // After each (some) steps, update the progress meter
			Thread.sleep(10); // Slows things down so the animation is visible
		}
	}

	// Prints the progress bar if the index entered is larger than total.
	public static void printProgress(int index, int total) {
		if (index > total)
			return; // Out of range
		int size = 50; // Must be less than console width
		char done = '█';
		char todo = '░';
		// Compute basic metrics for the meter
		int complete = (100 * index) / total;
		int completeLen = size * complete / 100;
		/*
		 * A StringBuilder should be used for string concatenation inside a loop.
		 * However, as the number of loop iterations is small, using the "+" operator
		 * may be more efficient as the instructions can be optimized by the compiler.
		 * Either way, the performance overhead will be marginal.
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("["); //$NON-NLS-1$
		for (int i = 0; i < size; i++) {
			sb.append((i < completeLen) ? done : todo);
		}
		/*
		 * The line feed escape character "\r" returns the cursor to the start of the
		 * current line. Calling print(...) overwrites the existing line and creates the
		 * illusion of an animation.
		 */
		System.out.print("\r" + sb + "] " + complete + "%"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		// Once the meter reaches its max, move to a new line.
		if (done == total)
			System.out.println("\n"); //$NON-NLS-1$
	}
}
