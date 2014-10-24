package courseraBioinformatics2014;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/***********
 * CS: Generating the Theoretical Spectrum of a Peptide
 * 
 * CODE CHALLENGE: Implement LinearSpectrum.
 *    Input: An amino acid string Peptide.
 *    Output: The linear spectrum of Peptide.
 *
 **********************************
 * Pseudo code:
 * LinearSpectrum(Peptide, AminoAcid, AminoAcidMass)
 *   PrefixMass(0) ← 0
 *   for i ← 1 to |Peptide|
 *       for j ← 1 to 20
 *           if AminoAcid(j) =  i-th amino acid in Peptide
 *               PrefixMass(i) ← PrefixMass(i − 1) + AminoAcidMass(j)
 *   LinearSpectrum ← a list consisting of the single integer 0
 *   for i ← 0 to |Peptide| − 1
 *       for j ← i + 1 to |Peptide|
 *           add PrefixMass(j) − PrefixMass(i) to LinearSpectrum
 *   return sorted list LinearSpectrum
 * 
 * 
 * @author Jeff
 *
 */
public class W02_GeneTheoSpectrumPeptide {

	public static void main(String[] args){
		
		
		System.out.println("This is GeneTheoSpectrumPeptide program. ");
		
		//1st, input the original peptide sequence
		System.out.print("Please input the Peptide sequence: \n Original Peptide sequence: ");
		Scanner input = new Scanner(System.in);
		
		String ori_peptide = input.next();
		
		
		//2nd, get all sub-sequence for the original peptide string;
		ArrayList<String> sub_peptides = getSubSeq(ori_peptide);
		
		
		//3rd, get mass spectrum for each sub-sequence (this step could be merged into step-2;
		ArrayList<Integer> mass_peptides = getMassSeq(sub_peptides);
		
		
		//4th, printout the mass spectrum;
		printArrayList(mass_peptides);
		
		
		//close input
		input.close();
		
	}//end of main();
	
	

	private static ArrayList<Integer> getMassSeq(ArrayList<String> peptides) {
		// TODO get the mass weight (total weight) for each peptide sequence in the peptides arrayList;
		ArrayList<Integer> massList = new ArrayList<Integer>();
		
		//add 0 to the arrayList, because any peptide sequence might have a '0' mass;
		massList.add(0);
		
		int arrayLen = peptides.size();
		for(int i=0; i<arrayLen; i++){
			
			String curr_seq = peptides.get(i);
			
			int curr_Mass = 0;
			int curr_len = curr_seq.length();
			
			
			for(int j=0; j<curr_len; j++){
				
				switch(curr_seq.charAt(j)){
					
					case 'G': curr_Mass += 57; break;
					case 'A': curr_Mass += 71; break;
					case 'S': curr_Mass += 87; break;
					case 'P': curr_Mass += 97; break;
					case 'V': curr_Mass += 99; break;
					
					case 'T': curr_Mass += 101; break;
					case 'C': curr_Mass += 103; break;
					case 'I': curr_Mass += 113; break;
					case 'L': curr_Mass += 113; break;
					case 'N': curr_Mass += 114; break;
					
					case 'D': curr_Mass += 115; break;
					case 'K': curr_Mass += 128; break;
					case 'Q': curr_Mass += 128; break;
					case 'E': curr_Mass += 129; break;
					case 'M': curr_Mass += 131; break;
					
					case 'H': curr_Mass += 137; break;
					case 'F': curr_Mass += 147; break;
					case 'R': curr_Mass += 156; break;
					case 'Y': curr_Mass += 163; break;
					case 'W': curr_Mass += 186; break;
					
				}//end switch() loop;
				
			}//end for j<curr_len loop;
			
			//add the curr_Mass to the ArrayList;
			massList.add(curr_Mass);
			
		}//end for i<Len loop;
		
		/****************************************************
		 * Mass table;
		 *G 57	A 71		S 87		P 97		V 99
		 *T 101		C 103		I 113		L 113		N 114
		 *D 115		K 128		Q 128		E 129		M 131
		 *H 137		F 147		R 156		Y 163		W 186
		 */
		
		Collections.sort(massList);
		
		return massList;
	}//end of getMassSeq() method;

	
	
	private static void printArrayList(ArrayList<Integer> arrayList) {
		// TODO printout an arrayList;
		int Len = arrayList.size();
		
		for(int i=0; i<Len; i++){
			
			System.out.print(" " + arrayList.get(i));
		}
		
	}//end printArrayList() method;

	
	
	private static ArrayList<String> getSubSeq(String peptide) {
		// TODO Get all sub-sequences for the peptide 
		ArrayList<String> subList = new ArrayList<String>();
		
		int Len = peptide.length();
		
		//use double for loops to get all sub-strings;
		for(int i=0; i<Len; i++){
			
			for(int j=i+1; j<=Len; j++){
				
				String sub_seq = peptide.substring(i,j);
				subList.add(sub_seq);
			
			}//end inner for j<Len loop;
		}//end outer for i<Len-1 loop;
		
		
		return subList;
		
	}//end of getSubSeq() method;
	
	
}//end of everything in W01_GeneTheoSpectrumPeptide class
