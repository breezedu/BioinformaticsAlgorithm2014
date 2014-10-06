package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*********************************
 * Input: A string Text and an integer k.
 * Output: All most frequent k-mers in Text.
 * 
 * Sample Input:
 *   ACGTTGCATGTCGCATGATGCATGAGAGCT
 *   4
 * 
 * Sample Output:
 *   CATG GCAT
 * 
 * @author Jeff
 *
 */

public class W01_FrequentWordsProblem {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Frequent Words Problem script.");
		
		//1st, read_in data from D:\BioinformaticsCoursera\TXT\dataset_3_X.txt
		Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_2_9.txt"));
		
		String sequence = read_in.next();
		int kmer = read_in.nextInt();
		
		System.out.println("Original string: " + sequence);
		System.out.println("k-mer: " + kmer);
		
		
		//2nd, create an arrayList to store current sub_strings with most frequencies; 
		int freq_max = 0;	//this will store the maximum frequency;
		ArrayList<String> Most_freq_seq = new ArrayList<String>();
		
		
		//3rd, create a hash_table to store all sub-sequences and their show-up times;
		HashMap<String, Integer> freSeqHash = new HashMap<String, Integer>();
		
		
		//4th, traverse the original sequence, calculate the times each sub-sequence has showed up;
		//if it has already been in the hash-table, just update the <value>; else put<string, 1>;
		//if the new <value> > freq_max, empty the Most_freq_seq arraylist, add current substring;
		//   else if the new <value> == freq_max, just add current substring to the arrayList;
		//   else, do nothing;
		int end = sequence.length() - kmer; 	//this is the last index of sub-string;
		
		for(int i=0; i<end; i++){
			
			String currStr = sequence.substring(i, i+kmer);
			
			//check hash-table;
			if(freSeqHash.containsKey(currStr)){
				
				int value = freSeqHash.get(currStr) + 1;
				freSeqHash.put(currStr, value);
				
				if(value == freq_max) {
					
					Most_freq_seq.add(currStr);
				
				} else if (value > freq_max){
					
					Most_freq_seq.clear();
					Most_freq_seq.add(currStr);
					freq_max = value;
				
				} //end if (value >= freq_max) conditions;
				
				
			} else {
				
				freSeqHash.put(currStr, 1);
				
			}//end freSeqHash.containsKey() condition;
			
		} //end for i<=end loop;
		
		
		//5th, printout all sub-sequences stored in the Most_freq_seq ArrayList;
		System.out.println("There are " + Most_freq_seq.size() + " sub-sequences with most frequence " + freq_max +". ");
		
		printArrayList(Most_freq_seq);
		
		//close read_in scanner
		read_in.close();
		
	}//end of main();
	

	/*********
	 * Printout all strings in an arrayList;
	 * @param arrayList
	 */
	private static void printArrayList(ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		
		int size = arrayList.size();
		for(int i=0; i<size; i++){
			
			System.out.print(arrayList.get(i) + " ");
		}
		
		System.out.println();
		
	}//end printArrayList() method;

}//end of Week_01_FrequentWordsProblem class;
