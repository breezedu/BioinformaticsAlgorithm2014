package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*********************************
 * We say that position i in k-mers p1 бн pk and q1 бн qk is a mismatch if pi б┘ qi. 
 * For example, CGAAT and CGGAC have two mismatches. 
 * The number of mismatches between strings p and q is called the Hamming distance between these strings 
 * and is denoted HammingDistance(p, q).
 * 
 * Hamming Distance Problem: Compute the Hamming distance between two strings.
 *    Input: Two strings of equal length.
 *    Output: The Hamming distance between these strings.
 *
 * Sample Input:
 * 	GGGCCGTTGGT
 * 	GGACCGTTGAC
 * 
 * Sample Output:
 * 	3
 * 
 * @author Jeff
 *
 */
public class Week_01_HammingDistanceProblem {

	/**********
	 * Main()
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Hamming Distance Problem.");
		
		//1st, read_in two string from D:\BioinformaticsCoursera\TXT\HammingDistance.txt
		
		Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_9_3.txt"));
		
		String seqOne = read_in.next();
		String seqTwo = read_in.next();
		
		
		//2nd, traverse through the sequence, calculate mismatches;
		int miss_match = 0;
		int seq_length = seqOne.length();
		
		for(int i=0; i<seq_length; i++){
			
			if(seqOne.charAt(i) != seqTwo.charAt(i)) miss_match++;
		}
		
		//3rd, printout the # of miss matches;
		System.out.println("There are " + miss_match +" miss matches.");
		
		
		//4th, close the scanner
		read_in.close();
		
	}//end of main();
	
	
	
	/****************
	 * create a run() method, thus other class could create a HammingDistanceProblem object hamDis
	 * then call hamDis.run(string1, string2) to calculate the distance between two strings;
	 * @param seqOne
	 * @param seqTwo
	 * @return
	 * @throws FileNotFoundException
	 */
	public int run(String seqOne, String seqTwo) throws FileNotFoundException{
		
		//System.out.println("This is Hamming Distance Problem in Week_01_HammingDistanceProblem.java class.");
		
		//1st, read_in two string from D:\BioinformaticsCoursera\TXT\HammingDistance.txt
		
		//Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/" + data_set));
		
		//String seqOne = read_in.next();
		//String seqTwo = read_in.next();
		
		//1st, different from main(); the run() method has two strings passed by call
		if(seqOne.length() != seqTwo.length()){
			
			System.out.println("The two sequences could not match.");
		}
		
		
		//2nd, traverse through the sequence, calculate mismatches;
		int miss_match = 0;
		int seq_length = seqOne.length();
		int seq_length2 = seqTwo.length();
		if(seq_length != seq_length2) {
			
			System.out.println("The two sequences could not match.");
			
		}//end if() condition;
		
		for(int i=0; i<seq_length; i++){
			
			if(seqOne.charAt(i) != seqTwo.charAt(i)) miss_match++;
		}
		
		//3rd, printout the # of miss matches;
		System.out.println("There are " + miss_match +" miss matches.");
		
		
		//4th, close the scanner
		//read_in.close();
		
		return miss_match;
		
	}//end of run();
	
	
}//end of Hamming Distance Problem class;
