/**
 * 
 */
package MaleFemaleTextRecognizer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author User
 *
 */
public class Main {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		List<String> text;
		try {
			log(Arrays.toString(GetTokens(loadTextFile("textFiles/text.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a text file and turns it into a string.
	 * @param aFileName string path to the file
	 * @return The text string in the given path file.
	 * @throws IOException Exception when IO has failed.
	 */
	static String loadTextFile(String aFileName) throws IOException 
	{
		// Get path from string
		Path path = Paths.get(aFileName);
		// Reads all string lines from path with unicode encoding
		List<String> stringList =  Files.readAllLines(path, ENCODING);
		// Variable to store our string
		String returnString = "";
		// Get all string lines and put them in one string with linebreaks
		for (String s : stringList)
		{
			returnString += s + "\t";
		}
		return returnString;
	}
	
	/**
	 * Gets a string by which he removes all non A-Z and a-z characters returns array of words.
	 * @param s string to tokenify
	 * @return the tokenified string
	 */
	public static String[] GetTokens(String s)
	{
		// Get string and replace all non letters with nothing
		s = s.replaceAll("[^a-zA-Z\\s]", "");
		// Set everything to lower case
		s = s.toLowerCase();
		// Split the words at the whitespace
		return s.split("\\s+");
	}
	
	/**
	 * Logs an object, shortcut to system.bladibla.bla(bla bla)
	 * @param o Object to print
	 */
	private static void log(Object o)
	{
		System.out.println(String.valueOf(o));
	}

}
