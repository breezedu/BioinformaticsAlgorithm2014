package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

public class W01_FrequentWordswithMismatchesProblem {
	
	
	//create a ApproximatePatternCount object;
	//call approPattCount.run(seq1, seq2, dis)
	//it will return the ApproximatePatternCount of two sequence with miss match less than dis;
	static W01_ApproximatePatternCount approPattCount = new W01_ApproximatePatternCount();
	
	
	//create a GenerateAllKmers object;
	//call generateKmers.run(int Len), it will return an ArrayList of all possible Kmers with Len nucleotides'
	static W01_GenerateAllKmers generateKmers = new W01_GenerateAllKmers();
	
	
	/***********
	 * Main();
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		
		//1st, read_in data from D:\BioinformaticsCoursera\TXT\frequent_words_mismatch_data_1.txt
		String routine = "D:/BioinformaticsCoursera/TXT/";
		String doc = "dataset_9_7.txt";
		
		Scanner read_in = new Scanner(new File(routine + doc));
		
		String ori_sequence = read_in.next();
		int kmer_length = read_in.nextInt();
		int dis = read_in.nextInt();
		
		
		
		//2nd, call generateKmers.run() to get an ArrayList of all possible Kmers
		//with the Length of kmer_length;
		ArrayList<String> kmers_list = generateKmers.run(kmer_length);
	
		
		
		//3rd, take each possible sub-sequence from subSeq_list, compare the ApproximatePatternCount with 
		//the original sequence, update the freq_max whenever we have a new maximum value;
		ArrayList<String> maxKmers_list = new ArrayList<String>();
		
		//keep record of maximum frequency; 
		int freq_max = 0;
		
		int arraylist_size = kmers_list.size();	//the size of sub-sequence arrayList;
		
		for(int i=0; i<arraylist_size; i++){
			
			int freq_count = approPattCount.run(ori_sequence, kmers_list.get(i), dis);
			
			if(freq_count > freq_max){
				
				maxKmers_list.clear();
				maxKmers_list.add(kmers_list.get(i));
				freq_max = freq_count; 
				
				System.out.println("  " + kmers_list.get(i) + ", " + freq_max);
				
			} else if(freq_count == freq_max) {
				
				maxKmers_list.add(kmers_list.get(i));
			
			}//end if (freq_count >=< freq_max) conditions;
			
		}//end for i< arrayList_size loop;
		
		
		
		//4th, printout result, the arrayList with max-frequency sub-sequences;
		printArraylist(maxKmers_list);
		
		
		
		//5th, close read_in scanner;
		read_in.close();
		
	}//end main();
	
	

	/********
	 * Printout an arraylist of strings;
	 * @param maxFreq_list
	 */
	private static void printArraylist(ArrayList<String> al) {
		// TODO Auto-generated method stub
		
		int size = al.size();
		
		for(int i=0; i<size; i++){
			
			System.out.print(al.get(i) + " ");
		}//end for i<size loop;
		
		System.out.println();
		
	}//end printArraylist() method;

	
}//end Week_01_FrequentWordswithMismatchesProblem;
