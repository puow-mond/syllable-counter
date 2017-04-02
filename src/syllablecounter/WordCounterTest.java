package syllablecounter;

import java.net.URL;
import java.util.*;
import java.io.*;

/**
 * Test the countSyllables method using some test words read from a file.
 */
public class WordCounterTest {

	/**
	 * Read lines from a file containing "word syllable_count" and add them to
	 * map of words.
	 * 
	 * @param in InputStream to read from
	 */
	private static Map<String, Integer> loadWords(InputStream in) throws IOException {
		Map<String, Integer> words = new HashMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String inline = null;
		int linecount = 0;
		while ((inline = reader.readLine()) != null) {
			linecount++;
			inline = inline.trim();
			if (inline.startsWith("#"))
				continue; // skip lines starting with #
			if (inline.isEmpty())
				continue;
			String[] args = inline.split("\\s+");
			if (args.length != 2) {
				System.out.printf("Invalid line %d: %s\n", linecount, inline);
				continue;
			}
			try {
				int count = Integer.parseInt(args[1]);
				words.put(args[0], count);
			} catch (NumberFormatException nfe) {
				System.out.printf("Invalid line %d: %s\n", linecount, inline);
			}
		}
		try {
			reader.close();
		} catch (IOException ioe) {
			/* ignore */ }

		return words;
	}

	/**
	 * Load words from URL.
	 * 
	 * @param urlname is name of URL.
	 * @return Map of words.
	 * @throws IOException if error occurred.
	 */
	private static Map<String, Integer> loadWords(String urlname) throws IOException {
		URL url = new URL(urlname);
		InputStream in = url.openStream();
		return loadWords(in);
	}

	/**
	 * Test the countSyllables method from WordCounter class and print results.
	 * 
	 * @param args not used.
	 * @throws IOException if cannot read.
	 */
	public static void main(String[] args) throws IOException {
		Map<String, Integer> words = loadWords(URLNAME);
		WordCounter counter = new WordCounter();
		int correct = 0;
		int incorrect = 0;
		for (String word : words.keySet()) {
			int expect = words.get(word);
			int actual = counter.countSyllables(word);
			System.out.printf("%-24s  %4d", word, actual);
			if (expect == actual) {
				correct++;
				System.out.println("  Correct");
			} else {
				incorrect++;
				System.out.printf("  Incorrect, actual = %d\n", expect);
			}
		}
		System.out.printf("Correct: %d  Incorrect: %d\n", correct, incorrect);
	}

	static final String URLNAME = "http://se.cpe.ku.ac.th/testwords.txt";
}
