package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/*********************************
 * A most frequent k-mer with up to d mismatches in Text is simply a string Pattern 
 * maximizing Countd(Text, Pattern) among all k-mers. 
 * Note that Pattern does not need to actually appear as a substring of Text; 
 * for example, as we already saw, AAAAA is the most frequent 5-mer with 1 mismatch 
 * in AACAAGCTGATAAACATTTAAAGAG, even though it does not appear exactly in this string. 
 * Keep this in mind while solving the following problem.
 * 
 * Frequent Words with Mismatches Problem: Find the most frequent k-mers with mismatches in a string.
 *    Input: A string Text as well as integers k and d. (You may assume k ¡Ü 12 and d ¡Ü 3.)
 *    Output: All most frequent k-mers with up to d mismatches in Text.
 *    
 *    CODE CHALLENGE: Solve the Frequent Words with Mismatches Problem.
 *    Sample Input:
 *    	ACGTTGCATGTCGCATGATGCATGAGAGCT
 *      4 1
 *      
 *    Sample Output:
 *      GATG ATGC ATGT
 * 
 * @author Jeff
 *
 */

public class Week_01_FrequentWordswithMismatchesProblem {
	
	//create a ApproximatePatternCount object;
	static Week_01_ApproximatePatternCount patCount = new Week_01_ApproximatePatternCount();
	
	public static void main(String[] args) throws FileNotFoundException{
		
		//1st, read_in data from D:\BioinformaticsCoursera\TXT\frequent_words_mismatch_data_1.txt
		Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/frequent_words_mismatch_data_1.txt"));
		
		String ori_sequence = read_in.next();
		int kmer = read_in.nextInt();
		int dis = read_in.nextInt();
		
		
		//2nd, get unique sub-sequences, put them into an ArrayList<>
		ArrayList<String> subSeq_list = new ArrayList<String>();
		
		//use hash-set to check if the new sub-sequence is unique;
		HashSet<String> subSet = new HashSet<String>();
		
		//the index of the last sub-sequence;
		int end_index = ori_sequence.length()-kmer;
		//System.out.println(ori_sequence.substring(end_index));
		
		for(int i=0; i<=end_index; i++){
			
			String subSeq = ori_sequence.substring(i, i+kmer);
			
			if(!subSet.contains(subSeq)) {
				
				subSeq_list.add(subSeq);
				
			}//end if subSet !contains(subseq) condition;
		
		}//end for i<=end_index loop;
		
		
		//3rd, 
		
		int count = patCount.run("AAAGGCC", "AAGC", 1);		
		System.out.println("count: " + count);
		
	}//end main();

}//end Week_01_FrequentWordswithMismatchesProblem;
