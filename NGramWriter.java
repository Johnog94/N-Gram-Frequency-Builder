package ie.atu.sw;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Arrays;

public class NGramWriter implements Serializable { // convert NGramWriter to stream that you can send over a network or
													// save it as file or store in DB for later usage
	private static final long serialVersionUID = 777; // Serialisation Identifier
	private Object[][] table = new Object[1000][2]; // Private 2D array to store NGrams with 1000 rows and 2 columns
	private SaveNGrams ngWriter = new SaveNGrams(); // allow class NGramWriter to call SaveNGrams class as ngWriter
	private int index;

	// Method to get Ngrams within files
	public void getNGrams(String w, int ngSize) {
		int n = ngSize;
		String[] ngrams = new String[w.length() - n + 1];

		// loop through words in file for ngrams based on ngram size selected
		for (int i = 0; i <= w.length() - n; i++) {
			ngrams[i] = w.substring(i, i + n);
		}
		for (String t : ngrams) {
			addNGram(t);
		}
	}

	// method to add ngrams from files to hash table
	@SuppressWarnings("boxing")
	public void addNGram(String ngram) {
		try {
			index = ngram.hashCode() % table.length;
			long counter = 1;

			if (table[index][0] != null) {
				counter += (Long) table[index][1];
			}
			table[index][0] = ngram;
			table[index][1] = counter;

		} catch (Exception e) {
			e.printStackTrace(); // prints the throwable along with other details like the line number and class
									// name where the exception occurred
		}
	}

	public void save(String outputFile, int ngSize) throws FileNotFoundException {
		ngWriter.save(table, outputFile, ngSize); // save ngrams to file
	}

	// get index method
	public int getIndex() {
		return index;
	}

	// Stream count method to get NGram matches
	public int getArrLength(Object[][] table) {
		Arrays.stream(table).filter(e -> e != null).count();
		return index; // return index when completed
	}

}
