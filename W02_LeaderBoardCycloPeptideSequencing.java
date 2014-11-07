package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/***********************
 * CODE CHALLENGE: Implement LEADERBOARDCYCLOPEPTIDESEQUENCING.
 *   Input: An integer N and a collection of integers Spectrum.
 *   Output: LeaderPeptide after running LEADERBOARDCYCLOPEPTIDESEQUENCING(Spectrum, N).
 *   
 * Sample Input:
 *   10
 *   0 71 113 129 147 200 218 260 313 331 347 389 460
 *   
 * Sample Output:
 * 113-147-71-129
 * 
 * 
 * Pseudo Code:
 * LEADERBOARDCYCLOPEPTIDESEQUENCING(Spectrum, N)
 *      Leaderboard ¡û {empty peptide}
 *      LeaderPeptide ¡û empty peptide
 *      while Leaderboard is non-empty
 *           Leaderboard ¡û Expand(Leaderboard)
 *           
 *           for each Peptide in Leaderboard
 *               if Mass(Peptide) = ParentMass(Spectrum)
 *                   if Score(Peptide, Spectrum) > Score(LeaderPeptide, Spectrum)
 *                       LeaderPeptide ¡û Peptide
 *               else if Mass(Peptide) > ParentMass(Spectrum)
 *                   remove Peptide from Leaderboard
 *                   
 *           Leaderboard ¡û Trim(Leaderboard, Spectrum, N)
 *           
 *      output LeaderPeptide
 * 
 * @author Jeff
 * 
 */
public class W02_LeaderBoardCycloPeptideSequencing {
	
	
	//Peptide blocks:
	/**************************************************************************
	 * Possible peptide blocks and their Mass spectrum integers:
	 *
	 *	G	A	S	P	V	T	C	I/L	N	D	K/Q	E	M	H	F	R	Y	W
	 *	57	71	87	97	99	101	103	113	114	115	128	129	131	137	147	156	163	186
	 *	
	 *	will use these integers to "Expand" the sequences.
	 * @throws FileNotFoundException 
	 */

	
	public static void main(String[] args) throws FileNotFoundException{

		
		
		//1.1st, create Leaderboard
		ArrayList<ArrayList<Integer>> Leaderboard = new ArrayList<ArrayList<Integer>>();
		
		//1.2nd, create an empty peptide arrayList (add 0 only to the arraylist
		ArrayList<Integer> emptyPeptide = new ArrayList<Integer>();
		//emptyPeptide.add(0);
		
		//1.3rd, add emptyPeptide to leaderboard 
		Leaderboard.add(emptyPeptide);
		
		
		
		//2nd, create LeaderPeptide another empty arrayList with only 0 in it
		ArrayList<Integer> LeaderPeptide = new ArrayList<Integer>();
		//LeaderPeptide.add(0);
		ArrayList<ArrayList<Integer>> leaderPeptideList = new ArrayList<ArrayList<Integer>>();
		leaderPeptideList.add(LeaderPeptide);
		
		
		
		//3rd, readIn data from D:\BioinformaticsCoursera\TXT\leaderboard_clean.txt
		Scanner readIn = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_102_7.txt"));

		//Scanner readIn = new Scanner(new File("D:/BioinformaticsCoursera/TXT/leaderboard_clean.txt"));
		
		int num_N = readIn.nextInt();
		
		ArrayList<Integer> expMassList = new ArrayList<Integer>();
		
		while(readIn.hasNext()){
			
			int nextNum = readIn.nextInt();
			expMassList.add(nextNum);
		
		}//end while readIn.hasNext() loop;
		
		int massParent = expMassList.get(expMassList.size()-1);
		System.out.println("The parent Mass spectrum is: " + massParent);
		
		
		//close readIn scanner
		readIn.close();
		
		
		
		//4th, very important while loop;
		while(Leaderboard.size() > 0 ){
			
			System.out.println("Size: " + Leaderboard.size() );
			printArrayList(Leaderboard.get(0));
			
			//expand Leaderboard
			Leaderboard = ExpandLeaderboard(Leaderboard);
			
			
			//get score of current LeaderPeptide against massList			
			int currScore = scorePeptide2MassList(LeaderPeptide, expMassList);
			
			
			for(int i=0; i<Leaderboard.size(); i++){
				
				//System.out.print(Leaderboard.size() + " sub: " + Leaderboard.get(i).size() +  " ");
				//printArrayList(Leaderboard.get(i));
				int currTotalMass = totalMass( Leaderboard.get(i));
				
				if( currTotalMass == massParent) {
					
					//if score(Leaderboard.get(i), massList) > score(LeaderPeptide, massList)
					//		LeaderPeptide <-- Leaderboard.get(i);
					
					if(scorePeptide2MassList( Leaderboard.get(i), expMassList) > currScore){
						
						System.out.println("currScore: " + currScore);
						currScore = scorePeptide2MassList( Leaderboard.get(i), expMassList);
						LeaderPeptide = new ArrayList<Integer>(Leaderboard.get(i));
					}
				//	Leaderboard.remove(i);
				//	i--;
					
				} else if (currTotalMass > massParent){
										
					Leaderboard.remove(i);
					i--;
					
				}//end if-else conditions;
				
				
			}//end i <numOfLeaderPeptides loop;
			
			
			//Trim(Leaderboard, Spectrum, N)
			
			Leaderboard = trimCyclicPeptideList(Leaderboard, expMassList, num_N);
			
			
		}//end while Leaderboard.size() > 0 loop;
		
		
		
		
		
		//5th, output leader peptide arrayList;
		
		System.out.println("Printout experimental Peptide arrayList:");
		printArrayList(expMassList);
		
		System.out.println("Printout Leader Peptide:");
		printArrayList(LeaderPeptide);
		
		
		
		
	}//end of main();
	
	
	private static ArrayList<ArrayList<Integer>> trimCyclicPeptideList(ArrayList<ArrayList<Integer>> leaderboard, ArrayList<Integer> expMassList, int num_N) {
		// TODO Auto-generated method stub
		if(leaderboard.size() <= num_N) return leaderboard;
		
		//create an arrayList to store all index-score-Pairs;
		ArrayList<IndexScorePair> pairList = new ArrayList<IndexScorePair>();
		
		ArrayList<ArrayList<Integer>> retBoard = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<leaderboard.size(); i++){
			
			IndexScorePair currPair = new IndexScorePair(i, scorePeptide2MassList(leaderboard.get(i), expMassList));
			pairList.add(currPair);
		}	
		
		
		//sort pairList, according to the score of each pair
		Collections.sort(pairList);
		
		for(int i=0; i<num_N; i++){
			
			int index = pairList.get(i).getIndex();
			
			retBoard.add(leaderboard.get(index));
		}
		
		
		//put all these score equal to pairList.get(num_N-1) to the leaderboard too;
		for(int i= num_N; i<pairList.size(); i++){
			
		//	System.out.print(">" + pairList.get(i-1).getScore());
			
			if(pairList.get(i).getScore() >= pairList.get(num_N-1).getScore())
				
				retBoard.add( leaderboard.get(pairList.get(i).getIndex()) );
		}
		
		return retBoard;
	}//end Trim method;


	private static int scorePeptide2MassList(ArrayList<Integer> leaderPeptide, ArrayList<Integer> expMassList) {
		// TODO Auto-generated method stub
		
		//1st, get the theoretical-mass-spectrum list for that peptide;
		ArrayList<Integer> theoMassList = getTheoMassListCyclic(leaderPeptide);
		
		//2nd, call comparemassSpectrum() to score the theoretical-mass-spectrum to the experimental spectrum;
		int score = compareMassSpectrum(expMassList, theoMassList);
		
		return score;
	}


	static int compareMassSpectrum(ArrayList<Integer> exp_MassList,	ArrayList<Integer> theo_MassList) {
		// TODO Auto-generated method stub
		int exp_size = exp_MassList.size();
		int theo_size = theo_MassList.size();
		//System.out.println("Theoretical Spectrum size: " + theo_size +", Experimental Spectrum size: " + exp_size);
		
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


	/*******************
	 * to get a theoritical mass spectrum of a cyclic peptide from a mass-list;
	 * @param leaderPeptide
	 * @return
	 */
	static ArrayList<Integer> getTheoMassListCyclic(ArrayList<Integer> Peptide) {
		// TODO Auto-generated method stub
		ArrayList<Integer> massList = new ArrayList<Integer>();
		
		int totalMass = totalMass(Peptide);
		massList.add(0);
		
		int Len = Peptide.size();
		
		for(int i=0; i<Len; i++){
			
			for(int j=i+1; j<Len; j++){
				
				int currMass = 0;
				
				for(int k=i; k<j; k++){
					
					currMass += Peptide.get(k);
					
				}
				
			massList.add(currMass);
			massList.add(totalMass - currMass);
				
			}//end for j<Len loop;
			
		}//end for i<Len loop;
		
		massList.add(totalMass);
		
		Collections.sort(massList);
		
		return massList;
		
	}//end getTheoMassListCyclic() method;


	/*************************
	 * calculate the total mass of a mass spectrum arrayList;
	 * 
	 * @param arrayList
	 * @return the totalMass value;
	 */
	private static int totalMass(ArrayList<Integer> arrayList) {
		// TODO Auto-generated method stub
		int totalMass = 0;
		
		for(int i=0; i<arrayList.size(); i++){
			
			totalMass += arrayList.get(i);
		}//end for loop;
		
		return totalMass;
		
	}//end totalMass() method;



	static void printArrayList(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		int Len = list.size();
		if(Len < 1) {
			
			System.out.println("Got an empty arrayList.");
			return;
		}
		
		
		System.out.print(" " + list.get(0));
		
		for(int i=1; i<Len; i++){
			System.out.print("-" + list.get(i));
		}
		
		System.out.println();
	}





	/****************
	 * Expand current peptides in the leaderboard arrayList, add one more peptide block for each peptide;
	 * 
	 * @param leaderboard
	 * @return
	 */
	private static ArrayList<ArrayList<Integer>> ExpandLeaderboard(	ArrayList<ArrayList<Integer>> oriBoard) {
		// TODO Auto-generated method stub
		// Peptide blocks: 57	71	87	97	99	101	103	113	114	115	128	129	131	137	147	156	163	186
		ArrayList<ArrayList<Integer>> retBoard = new ArrayList<ArrayList<Integer>>();
		
		int[] blocks = {57, 71, 87, 97, 99, 101, 103, 113, 114, 115, 128, 129, 131, 137, 147, 156, 163, 186};
		int numOfBlocks = blocks.length;
		
		int currPeptides = oriBoard.size();
		
		for(int i=0; i<currPeptides; i++){
			
			for(int j=0; j<numOfBlocks; j++){
				
				//make a whole copy of each arrayList in the Leaderboard ArrayList;
				ArrayList<Integer> expPeptide = new ArrayList<Integer>(oriBoard.get(i));
				
				//add one more peptide block to the end of the new peptide arrayList;
				expPeptide.add(blocks[j]);
				
				//add the new peptide arrayList to returnBoard ArrayList-of-ArrayList;
				retBoard.add(expPeptide);
			}
		}
		
		
		return retBoard;
		
	}//end ExpandLeaderboard() method;
	
	
}//end of everything in W02_leaderBoardCycloPeptideSequencing class;



