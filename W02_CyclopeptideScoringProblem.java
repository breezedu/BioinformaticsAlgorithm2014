package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**********************************
 * Cyclopeptide Scoring Problem: Compute the score of a cyclic peptide against a spectrum.
 *     Input: An amino acid string Peptide and a collection of integers Spectrum.
 *     Output: The score of Peptide against Spectrum, Score(Peptide, Spectrum).
 *     
 * CODE CHALLENGE: Solve the Cyclopeptide Scoring Problem.
 * 
 * Sample Input:
 * 		NQEL
 * 		0 99 113 114 128 227 257 299 355 356 370 371 484
 * 
 * Since the theritical cyclopeptide spectrum of NQEL is:
 * 		0 113 113 114 114 128 129 227 242 242 242 242 257 355 356 370 370 371 371 484
 * 
 * Sample Output:
 * 		11
 * 
 * @author Jeff
 *
 */



public class W02_CyclopeptideScoringProblem {
	
	/*************
	 * Create a W02_GeneTheoSpectrumPeptide_C object, to generate all theoritical Spectrum for a cyclic peptide;
	 */
	static W02_GeneTheoSpectrumPeptide_C CycloPeptideSpec = new W02_GeneTheoSpectrumPeptide_C();
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Cyclo Peptide Scoring Problem.");
		
		//1st, create a scanner to read-in sequence and the integers of spectrum:
		Scanner reader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_102_3.txt"));
		
		
		//2nd, read_in original sequence
		String ori_sequence = reader.next();
		System.out.println("ori_seq: " + ori_sequence);
		System.out.println("Length: " + ori_sequence.length());
		
		//3rd, read_in all other integers, put them into an arrayList;
		ArrayList<Integer> real_SpectrumList = new ArrayList<Integer>();
		
		while(reader.hasNext()){
			
			int nextSpect = reader.nextInt();
			real_SpectrumList.add(nextSpect);
		
		}//end while(reader.hasNext) loop;
		
		
		//4th, generate a theoritical spectrum ArrayList according to the original sequence;
		ArrayList<Integer> theoSpectrumList = CycloPeptideSpec.run( ori_sequence );
		
		
		//5th, create a HashSet based on the theoSpectrum List;
		HashMap<Integer, Integer> theoSpecHash = buildTheoSpectrumHash(theoSpectrumList);		
		
		
		
		//6th, check the Mass spectrum collection from real experiment;
		/*********************************************************
		 * use the hashMap just created; initial count 
		 * take Mass as the Key, if there's not such 'Key' in the Map, just ignore;
		 * Every time we get a 'match', reduce the 'Value' of that key, let count++;
		 * when the Value has been reduced to 'Zero', remove that Key from the hashMap;
		 * return the count value;
		 */				
		int count = calculateSpectrumScoring(real_SpectrumList, theoSpecHash);	
		
		
		
		//7th, printout the count, which is the score of real spectrum comparing with theoritical spectrum;
		System.out.println("count: " + count);
		
		//	printArrayList(real_SpectrumList);
		
		
		//8th, close scanner
		reader.close();
		
	}//end of main();


	/**************************************************************
	 * Calculate the spectrum score, by matching the real spectrum list with the theoretical spectrum hashMap;
	 * since there 'must' be some duplicated Mass unites exist, we use 'Value' to store the repeats of that Mass;
	 * so every time we got a 'match', reduce the Value, count++;
	 * When, the Value was reduced to 'Zero', remove that Key from the HashMap;
	 * return the count;
	 * 
	 * @param real_SpectrumList
	 * @param theoSpecHash
	 * @return
	 */
	private static int calculateSpectrumScoring(ArrayList<Integer> real_SpectrumList, HashMap<Integer, Integer> theoSpecHash) {
		// TODO Auto-generated method stub
		int count = 0;
		int size = real_SpectrumList.size();
		
		for(int i=0; i<size; i++){
			
			int Mass = real_SpectrumList.get(i);
			
			if(theoSpecHash.containsKey( Mass )) {				
				
				
				int value = theoSpecHash.get( Mass );
				
				if(value > 0){
					theoSpecHash.put(Mass, value-1);
					count++;
				
				} else {
					
					theoSpecHash.remove(Mass);
				}//end if-else conditions				

			
			}//end if hash.containsKey condition;
			
		}//end for i<size loop;
		
		return count;
		
	}//end calculateSpectrumScoring() method; 


	/*****************************************
	 * Build a hashMap for the theoretical spectrum list;
	 * BST will also do the job, but HashMap seems more accurate; 
	 * @param theoSpectrumList
	 * @return
	 */
	private static HashMap<Integer, Integer> buildTheoSpectrumHash( ArrayList<Integer> theoSpectrumList) {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> spectrumHash = new HashMap<Integer, Integer>();
		
		int size = theoSpectrumList.size();
		for(int i=0; i<size; i++){
			
			int Mass = theoSpectrumList.get(i);
			
			if(!spectrumHash.containsKey( Mass )){

				spectrumHash.put(Mass, 1);
				
			} else {
				
				int value = spectrumHash.get( Mass ) +1;
				
				spectrumHash.put(Mass, value);
			}
		
		}//end for i<size loop;
		
		
		return spectrumHash;
		
	}//end buildTheoSpectrumHash() method;


	/*******
	 
	private static void printArrayList(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		
		int size = list.size();
		
		for(int i=0; i<size; i++){
			
			if(i%20 == 0) System.out.println();
			
			System.out.print(" " +list.get(i));
		}
		
	}
	*/

}//end of everything in W02_CyclopeptideScoringProblem class;
