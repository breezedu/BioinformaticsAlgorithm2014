package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*******************
 * CODE CHALLENGE: Compute the score of a linear peptide with respect to a spectrum.
 *    Input: An amino acid string Peptide and a collection of integers Spectrum.
 *    Output: The linear score of Peptide with respect to Spectrum, LinearScore(Peptide, Spectrum).
 *
 * Sample Input:
 * 	NQEL
 * 	0 99 113 114 128 227 257 299 355 356 370 371 484
 * 
 * Sample Output:
 * 	8
 *	
 * @author Jeff
 *
 */
public class W02_TrimmingthePeptideLeaderboard {
	
	
	//create a W02_GeneTheoSpectrumPeptide_L object, to generate all theoretical Mass Peaks;
	static W02_GeneTheoSpectrumPeptide_L generateLinearPeptideMass = new W02_GeneTheoSpectrumPeptide_L();
	
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Trimming the Peptide Leaderboard program.");
		
		//1st, readin data from D:\BioinformaticsCoursera\TXT/LinearScoring.txt document;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the title of input-document: [LinearScoring.txt] \n document: ");
		String doc_name = input.next();
		
		String routine = "D:/BioinformaticsCoursera/TXT/";
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		
		//2nd, get the Sequence from the first line of input.txt document;
		String ori_sequence = readIn.nextLine();
		
		System.out.println("The original sequence is: " + ori_sequence +". ");
		
		
		//3rd, get all the experimental Mass peaks from input document;
		ArrayList<Integer> exp_MassList = new ArrayList<Integer>();
		
		while(readIn.hasNext()){
			
			exp_MassList.add(readIn.nextInt());
			
		}//end while loop;
		
		
		//4th, generate all theoretical Mass peaks for the original_sequence;
		ArrayList<Integer> theo_MassList = generateLinearPeptideMass.run(ori_sequence);
		
		
		//5th, compare theoretical and experimental Mass specturm lists
		int score = compareMassSpectrums1(exp_MassList, theo_MassList);
		
		System.out.println("The score is: " + score);
		
		//close scanners:
		input.close();
		readIn.close();
		
	}//end of main();


	private static int compareMassSpectrums1(ArrayList<Integer> exp_MassList, ArrayList<Integer> theo_MassList) {
		// TODO Auto-generated method stub
		int exp_size = exp_MassList.size();
		int theo_size = theo_MassList.size();
		System.out.println("Theoretical Spectrum size: " + theo_size +", Experimental Spectrum size: " + exp_size);
		
		int score = 0; 
		HashMap<Integer, Integer> theoSpectrumHash = new HashMap<Integer, Integer>();
		
		//put every theoretical Mass into a HashMap;
		for(int i=0; i<theo_size; i++){
			
			int currMassPeak = theo_MassList.get(i);
			
			if(theoSpectrumHash.containsKey(currMassPeak)){
				
				int value = theoSpectrumHash.get(currMassPeak) + 1;
				theoSpectrumHash.put(currMassPeak, value);
				
			} else {
				
				theoSpectrumHash.put(currMassPeak, 1);
		
			}//end if-else condition;
			
		}//end for i<theo_size loop;
		
		
		//check the experimental spectrum with the theoretical spectrum;
		for(int i=0; i<exp_size; i++){
			
			int currMass = exp_MassList.get(i);
			
			if(theoSpectrumHash.containsKey(currMass)){
				
				score++;
				
				int value = theoSpectrumHash.get(currMass)-1;
				
				if(value == 0) {
					
					theoSpectrumHash.remove(currMass);
				
				} else {
					
					theoSpectrumHash.put(currMass, value);
				}//end inner if-else;
				
			}//end outer if condition;
			
		}//end for i<exp_size loop;
		
		return score;
	}

}//end of everything in W02_TrimmingthePeptideLeaderboard class
