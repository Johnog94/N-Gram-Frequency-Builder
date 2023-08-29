package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;

public class Parser {
	private NGramWriter ngSaver = new NGramWriter(); // call NGramWriter class as ngSaver
	private int size;
	private String file;
	private String fileDirectory;

	public Parser(int n, String outputFile, String dir) {
		this.fileDirectory = dir;
		this.size = n;
		this.file = outputFile;
	}

	public void parseDirectory() throws FileNotFoundException {
		File fileDir = new File(fileDirectory); // get file directory for parsing from user specified directory
		@SuppressWarnings("resource")
		String fileSeparator = FileSystems.getDefault().getSeparator(); // file path separator to separate files in
																		// specified directory
		String[] files = fileDir.list(); // add separated files to string array
		for (String file : files) { // take each file individually from files array and set as string
			parseFile(fileDir.getAbsolutePath() + fileSeparator + file); // call parse file method on each file
		}
		ngSaver.save(file, size); // write ngrams and save to file
	}

	// Method to parse individual file and split words / make lower case
	@SuppressWarnings("nls")
	public void parseFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))); 
			/*
			 * load file to object reader for parsing
			 */																								
			String line = null;
			while ((line = br.readLine()) != null) { // While loop will read files until end
				String[] words = line.split("\\s+"); // create string array by slicing words from parsed files
				for (String word : words) { // take each word from words array as a string
					word = word.trim().replaceAll("[^a-zA-Z]", "").toLowerCase(); 
					/*
					trims space and strips all
					characters from string other than
					lower and upper case letters, and
					convert all to lower case
					*/
					if (word.length() >= size) { // if the word length is greater than or equal to to Ngram size
													// selected
						ngSaver.getNGrams(word, size);
					} else
						continue;
				}
			}
			br.close(); // Close stream and release system resources associated with this stream
		} catch (Exception e) { // exception handling
			System.out.println("[ERROR] Files could not be Read!!"); // Error message for user to say files can be read
			e.printStackTrace(); //// prints the throwable along with other details like the line number and
									//// class name where the exception occurred
			return;
		}

	}

}
