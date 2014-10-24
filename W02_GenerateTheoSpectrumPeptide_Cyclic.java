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
 * CyclicSpectrum(Peptide, AminoAcid, AminoAcidMass)
 *   PrefixMass(0) ← 0
 *   for i ← 1 to |Peptide|
 *       for j ← 1 to 20
 *           if AminoAcid(j) =  i-th amino acid in Peptide
 *               PrefixMass(i) ← PrefixMass(i − 1) + AminoAcidMass(j)
 *               
 *   CyclicSpectrum ← a list consisting of the single integer 0
 *   for i ← 0 to |Peptide| − 1
 *       for j ← i + 1 to |Peptide|
 *           add PrefixMass(j) − PrefixMass(i) to CyclicSpectrum
 *           add total_Mass - (PrefixMass(j)-PrefixMass(i)) to CyclicSpectrum
 *           
 *   return sorted list CyclicSpectrum
 * 
 * 
 * @author Jeff
 *
 */

public class W02_GenerateTheoSpectrumPeptide_Cyclic {

	public static void main(String[] args){
		
		
		System.out.println("This is GeneTheoSpectrumPeptide program. ");
		
		//1st, input the original peptide sequence
		System.out.print("Please input the Peptide sequence: \n Original Peptide sequence: ");
		Scanner input = new Scanner(System.in);
		
		String ori_peptide = input.next();
		int total_Mass = getSeqMass(ori_peptide);
		
		//2nd, get all sub-sequence for the original peptide string;
		ArrayList<Integer> sub_peptides = getSubSeq(ori_peptide);
		printArrayList(sub_peptides);
		
		//3rd, get mass spectrum for each sub-sequence (this step could be merged into step-2;
		ArrayList<Integer> mass_peptides = getMassSeq(sub_peptides, total_Mass);
		
		
		//4th, printout the mass spectrum;
		printArrayList(mass_peptides);
		
		
		//close input
		input.close();
		
	}//end of main();
	
	
	public ArrayList<Integer> run(String ori_peptide){
		
		//1st, get total_Mass for ori_peptide;
		int total_Mass = getSeqMass(ori_peptide);
		
		//2nd, get all sub-sequence for the original peptide string;
		ArrayList<Integer> massList = getSubSeq(ori_peptide);
		
		
		//3rd, get mass spectrum for each sub-sequence (this step could be merged into step-2;
		ArrayList<Integer> mass_peptides = getMassSeq(massList, total_Mass);
		
		
		//4th, return the mass_peptides arrayList;
		return mass_peptides;
		
	}//end run() method;
	

	private static ArrayList<Integer> getMassSeq(ArrayList<Integer> massList, int total_Mass) {
		// TODO get the mass weight (total weight) for each peptide sequence in the peptides arrayList;
		System.out.println("Total mass: " + total_Mass);
		
		
		ArrayList<Integer> subPepMassList = new ArrayList<Integer>();
		
		//add 0 to the arrayList, because any peptide sequence might have a '0' mass;
		subPepMassList.add(0);
		
		int arrayLen = massList.size();
		for(int i=0; i<arrayLen; i++){
			
			int curr_Mass = 0;
			
			for(int j=i+1; j<arrayLen; j++){
				
				for(int k=i; k<j; k++){
					
					curr_Mass += massList.get(k);
				
				}//end for k<j loop;
					
				//add the curr_Mass to the ArrayList;
				subPepMassList.add(curr_Mass);
				
				//add the complementary Mass to the ArrayList, this will add all cyclic-peptide sub-seq mass to the arrayList;
				subPepMassList.add(total_Mass - curr_Mass);
				
				//reset curr_Mass;
				curr_Mass = 0;
				
			}//end for j<arrayLen loop;
						
			
		}//end outer for i<Len loop;
		
		/****************************************************
		 * Mass table;
		 *G 57	A 71		S 87		P 97		V 99
		 *T 101		C 103		I 113		L 113		N 114
		 *D 115		K 128		Q 128		E 129		M 131
		 *H 137		F 147		R 156		Y 163		W 186
		 */
		subPepMassList.add(total_Mass);
		Collections.sort(subPepMassList);
		
		return subPepMassList;
	}//end of getMassSeq() method;
	
	
	
	private static int getSeqMass(String curr_seq) {
		// TODO Auto-generated method stub
		
		int curr_len = curr_seq.length();
		
		int curr_Mass = 0;
		
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
		
		return curr_Mass;
	}



	private static void printArrayList(ArrayList<Integer> arrayList) {
		// TODO printout an arrayList;
		int Len = arrayList.size();
		
		for(int i=0; i<Len; i++){
			
			if(i%30 == 0) System.out.println();
			
			System.out.print(" " + arrayList.get(i));
		}
		
		System.out.println();
	}//end printArrayList() method;

	
	
	private static ArrayList<Integer> getSubSeq(String peptide) {
		// TODO Get all sub-sequences for the peptide 
		ArrayList<Integer> subList = new ArrayList<Integer>();
		
		int Len = peptide.length();
		
		//use double for loops to get all sub-strings;
		for(int i=0; i<Len; i++){
							
				switch(peptide.charAt(i)){
				
				case 'G': subList.add(57); break;
				case 'A': subList.add(71); break;
				case 'S': subList.add(87); break;
				case 'P': subList.add(97); break;
				case 'V': subList.add(99); break;
				
				case 'T': subList.add(101); break;
				case 'C': subList.add(103); break;
				case 'I': subList.add(113); break;
				case 'L': subList.add(113); break;
				case 'N': subList.add(114); break;
				
				case 'D': subList.add(115); break;
				case 'K': subList.add(128); break;
				case 'Q': subList.add(128); break;
				case 'E': subList.add(129); break;
				case 'M': subList.add(131); break;
				
				case 'H': subList.add(137); break;
				case 'F': subList.add(147); break;
				case 'R': subList.add(156); break;
				case 'Y': subList.add(163); break;
				case 'W': subList.add(186); break;
				
			}//end switch() loop;
			
		}//end for i<Len-1 loop;
		
		
		return subList;
		
	}//end of getSubSeq() method;
	
	
}//end of everything in W01_GeneTheoSpectrumPeptide class
