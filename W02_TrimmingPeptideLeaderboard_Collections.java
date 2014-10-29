package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*********************************
 * 	CODE CHALLENGE: Implement Trim (reproduced below).
 *    Input: A collection of peptides Leaderboard, a collection of integers Spectrum, and an integer N.
 *    Output: The N highest-scoring linear peptides on Leaderboard with respect to Spectrum.
 *
 *	Trim(Leaderboard, Spectrum, N, AminoAcid, AminoAcidMass)
 *   for j ¡û 1 to Leaderboard
 *       Peptide ¡û j-th peptide in Leaderboard
 *       LinearScores(j) ¡û LinearScore(Peptide, Spectrum)
 *   sort Leaderboard according to the decreasing order of scores in LinearScores
 *   sort LinearScores in decreasing order
 *   for j ¡û N + 1 to |Leaderboard|
 *       if LinearScores(j) < LinearScores(N)
 *           remove all peptides starting from the j-th peptide from Leaderboard
 *       return Leaderboard
 *   return Leaderboard
 *
 * @author Yan
 *
 */
public class W02_TrimmingPeptideLeaderboard_Collections {
	
	//create a W02_ComputeScoreofLinearPeptidetoSpectrum object, to score a peptide and a collection of spectrum;
	static W02_ComputeScoreofLinearPeptidetoSpectrum CompScoreLinearPeptide = new W02_ComputeScoreofLinearPeptidetoSpectrum();

	public static void main(String[] args) throws FileNotFoundException{
		
		//1st, create a scanner to readin data from dataset
		Scanner input = new Scanner(System.in);
		
		String routine = "D:/BioinformaticsCoursera/TXT/";
		
		System.out.print("Please input the title of the document:[Trim.txt] \n document: ");
		String doc_name = input.next();
		
		Scanner readIn = new Scanner(new File(routine + doc_name +".txt"));
		
		
		//2nd, read_in all strings (sequences), put them into an ArrayList;
		ArrayList<String> sequenceList = new ArrayList<String>();

		ArrayList<Integer> exp_MassList = new ArrayList<Integer>();
		
		while(readIn.hasNext()){
			
			String currStr = readIn.next();
			
			if(currStr.charAt(0) >= '0' && currStr.charAt(0)<= '9') {
				
				System.out.println(" " + currStr);
				int currInt = Integer.parseInt(currStr);
				exp_MassList.add(currInt);
				
			} else {
				
				sequenceList.add(currStr);

			}//end if-else condition;

		}//end while(readIn.hasNext) loop;
		
		
		
		int exp_size = exp_MassList.size() -1;
		int seqNum = sequenceList.size();
		
		int N = exp_MassList.get(exp_size);
		exp_MassList.remove(exp_size);
		
		
		//calculate Linear-Peptide-score for each sequence; put Sequence-score pair into an ArrayList;
		ArrayList<LinearPepScore> SeqScoreList = new ArrayList<LinearPepScore>();
		
		for(int i=0; i<seqNum; i++){
			
			int score = CompScoreLinearPeptide.run(sequenceList.get(i), exp_MassList);
			
			LinearPepScore currPepScore = new LinearPepScore(sequenceList.get(i), score);

			
			SeqScoreList.add(currPepScore);
		
		}//end for i<seqNum loop;
		
//		sortSeqArrayList(SeqScoreList);
		Collections.sort(SeqScoreList);
		
		for(int i=0; i<N; i++){
			
			System.out.println( SeqScoreList.get(i).seq);
		}
		
		
		//if the following several sequences have the same score, printout them too.
		int next = N;
		while(SeqScoreList.get(next).score == SeqScoreList.get(N-1).score){
			
			System.out.println( SeqScoreList.get(next).seq);
			next++;
		}
		
		//close scanners
		input.close();
		readIn.close();
		
		
	}//end of main();
	

}//end of everything in W01_


