package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Method to save Ngram to csv file
public class SaveNGrams {
	@SuppressWarnings("nls")
	public void save(Object[][] table, String file, int ngSize) throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(new File(file)); // new print writer stream to print to csv file
		String nextLine = System.getProperty("line.separator"); // print to next line in file

		// loop through object array table and print ngrams to file line by line
		for (int row = 1; row < table.length; row++) {
			pw.write(table[row][0] + "," + table[row][1] + nextLine);
		}

		pw.flush(); // clear the stream of any element that may be or maybe not inside the stream
		pw.close(); // Close stream and release system resources associated with this stream

	}
}
