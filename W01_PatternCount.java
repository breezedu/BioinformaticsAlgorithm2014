package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*********************************
 * CODE CHALLENGE: Implement PatternCount (reproduced below).
 *    Input: Strings Text and Pattern.
 *    Output: Count(Text, Pattern).
 *
 *   PatternCount(Text, Pattern)
 *       count ← 0
 *       for i ← 0 to |Text| − |Pattern|
 *           if Text(i, |Pattern|) = Pattern
 *               count ← count + 1
 *       return count
 * 
 * 
 * @author Jeff
 *
 */
public class W01_PatternCount {

	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Implement PatternCount class.");
		
		//1st, read-in data from D:/BioinformaticsCourseraTXT/PatternCount.txt;
		Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_2_6.txt"));
		
		String text_string = read_in.next();
		String pattern_string = read_in.next();
		
		System.out.println("text_string: " +text_string);
		System.out.println("pattern_string: " + pattern_string);
		
		
		//2nd check how many repeats of the pattern_string in the test string:
		int count = 0; //the total count of repeats;
		
		//get the lengths of text sequence and pattern sequence;
		int length_text = text_string.length();
		int length_pattern = pattern_string.length();
		
		
		int step = length_text - length_pattern; //the last index of subsequence;
		for(int i=0; i<step; i++){
			
			String curr_string = text_string.substring(i, i+length_pattern);
			if(curr_string.equals(pattern_string)) count++;
		}
		
		
		//3rd printout the total repeats of pattern string in the text string;
		System.out.println("There are " + count +" repeats.");
		
		//close read_in scanner;
		read_in.close();
		
	}//end main();
	
}//end of Week_1_PatternCount class;
