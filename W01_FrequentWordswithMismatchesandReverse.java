package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*********************************
 * We now redefine the Frequent Words Problem to account for both mismatches and reverse complements. 
 * Recall that Pattern refers to the reverse complement of Pattern.
 * 
 * Frequent Words with Mismatches and Reverse Complements Problem: 
 * Find the most frequent k-mers (with mismatches and reverse complements) in a DNA string.
 *     Input: A DNA string Text as well as integers k and d.
 *     Output: All k-mers Pattern maximizing the sum Countd(Text, Pattern)+ Countd(Text, Pattern)
 *     over all possible k-mers.
 *     
 * CODE CHALLENGE: Solve the Frequent Words with Mismatches and Reverse Complements Problem.
 * 
 * Sample Input:
 *    ACGTTGCATGTCGCATGATGCATGAGAGCT
 *    4 1
 *    
 * Sample Output:
 *    ATGT ACAT
 * 
 * @author Jeff
 *
 */

public class W01_FrequentWordswithMismatchesandReverse {
	
	//create a ApproximatePatternCount object;
	static W01_ApproximatePatternCount approPattCount = new W01_ApproximatePatternCount();
	
	
	//create a GenerateAllKmers object;
	static W01_GenerateAllKmers generateKmers = new W01_GenerateAllKmers();
	
	
	/***********
	 * Main();
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		//1st, read_in data from D:\BioinformaticsCoursera\TXT\frequent_words_mismatch_data_1.txt
		String routine = "D:/BioinformaticsCoursera/TXT/";
		String doc = "dataset_9_8.txt";
		
		Scanner read_in = new Scanner(new File(routine + doc));
		
		String ori_sequence = read_in.next();
		int kmer_length = read_in.nextInt();
		int dis = read_in.nextInt();
		
		
		//2nd, call generateKmers.run() to get an ArrayList of all possible Kmers
		//with the Length of kmer_length;
		ArrayList<String> kmers_list = generateKmers.run(kmer_length);
	
		
		//3rd, take each possible sub-sequence from subSeq_list, compare the ApproximatePatternCount with 
		//the original sequence, update the freq_max whenever we have a new maximum value;
		ArrayList<String> maxKmers_list = getMaxKmers(ori_sequence, kmers_list, dis);
		
		
		
		//4th, printout the arrayList with max-frequency sub-sequences;
		//and the reversed-complement-maxKmers_list;
		printArraylist(maxKmers_list);
		//printArraylist(reverse_maxKmers_list);
		
		
		
		//close read_in scanner;
		read_in.close();
		
	}//end main();
	
	
	
	/*********
	 * Get the complement sequence: AGGCCT -> TCCGGA;
	 * @param revSeq
	 * @return
	 */
	private static String complementString(String revSeq) {
		// TODO get the complement sequence
		
		int len = revSeq.length();
		String complSeq = "";
		
		for(int i=0; i<len; i++){
			
			switch(revSeq.charAt(i)){
			
			case 'A' : complSeq += 'T'; break;
			case 'G' : complSeq += 'C'; break;
			case 'C' : complSeq += 'G'; break;
			case 'T' : complSeq += 'A'; break;
			
			}//end of switch-case;
			
		}//end for i<len loop;
		
		
		return complSeq;
		
	}//end complementString() method;



	/**********************
	 * get the reverse string;
	 * @param oriSeq
	 * @return
	 */
	private static String reverseString(String oriSeq) {
		// TODO Reverse a string;
		int len = oriSeq.length();
		
		String revSeq = "";
		for(int i=0; i<len; i++){
			revSeq = oriSeq.charAt(i) + revSeq;
		}
		
		return revSeq;
	
	}//end reverseString() method;




	/****************************
	 * get the kmers with maximum ApprociatePatternCount;
	 * @param ori_sequence
	 * @param kmers_list
	 * @param dis
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ArrayList<String> getMaxKmers(String ori_sequence, ArrayList<String> kmers_list, int dis) throws FileNotFoundException {
		// TODO get the kmers with maximum ApprociatePatternCount;
		ArrayList<String> maxKmers_list = new ArrayList<String>();
		
		//keep record of maximum frequency;
		int freq_max = 0;
				
		int arraylist_size = kmers_list.size();	//the size of sub-sequence arrayList;
				
		for(int i=0; i<arraylist_size; i++){
			
			String oriSeq = kmers_list.get(i);
			String revSeq = reverseString(oriSeq);
			String compSeq = complementString(revSeq);
			
			int freq_count = approPattCount.run(ori_sequence, oriSeq, dis) + approPattCount.run(ori_sequence, compSeq, dis);
					
			if(freq_count > freq_max){
						
				maxKmers_list.clear();
				maxKmers_list.add(kmers_list.get(i));
				freq_max = freq_count; 
						
				System.out.println("  " + kmers_list.get(i) + ", " + freq_max);
						
			} else if(freq_count == freq_max) {
						
				maxKmers_list.add(kmers_list.get(i));
					
			}//end if (freq_count >=< freq_max) conditions;
					
		}//end for i< arrayList_size loop;
		
		return maxKmers_list;
	}



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
