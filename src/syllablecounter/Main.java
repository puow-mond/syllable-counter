package syllablecounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import stopwatch.Stopwatch;

/**
 * reads all the words from a URL or File and calls countSyllables. Output the
 * total number of words, syllables, and the elapsed time in seconds.
 * 
 * @author Wongsathorn Panichkurkul
 *
 */
public class Main {

	private static final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
	private static int countSyllables = 0;
	private static int countWord = 0;
	private static WordCounter counter = new WordCounter();
	private static Stopwatch stopwatch = new Stopwatch();

	/**
	 * Get all words from URL and then return a List of words.
	 * 
	 * @param urlname is name of URL.
	 * @return List of words.
	 */
	private static List<String> getWord(String urlname) {
		List<String> returnWord = new ArrayList<String>();
		BufferedReader reader;
		try {
			URL url = new URL(urlname);
			InputStream in = url.openStream();
			reader = new BufferedReader(new InputStreamReader(in));
			while (true) {
				String word = reader.readLine();
				if (word == null)
					break;
				returnWord.add(word);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return returnWord;
	}

	/**
	 * For count all syllables and words.
	 * 
	 * @param words is List of words that you want to analyze.
	 */
	private static void analyzeWords(List<String> words) {
		for (String word : words) {
			int syllables = counter.countSyllables(word);
			if (syllables != 0)
				countWord++;
			countSyllables += syllables;
		}
	}

	/**
	 * print result and description.
	 */
	private static void printResult() {
		System.out.println("Reading words from " + DICT_URL);
		System.out.println("Counted " + countSyllables + " syllables in " + countWord + " words");
		System.out.printf("Elapse time: %.3f sec\n", stopwatch.getElapsed());
	}

	/**
	 * read all words from URL ,count syllables ,word and print description.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		stopwatch.start();
		List<String> allWords = getWord(DICT_URL);
		analyzeWords(allWords);
		stopwatch.stop();
		printResult();
	}
}
