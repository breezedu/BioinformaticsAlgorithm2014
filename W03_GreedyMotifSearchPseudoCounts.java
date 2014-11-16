package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**********************************
 * Motif Finding Meets Oliver Cromwell
 * CODE CHALLENGE: Implement GREEDYMOTIFSEARCH with PseudoCounts.
 *    Input: Integers k and t, followed by a collection of strings Dna.
 *   Output: A collection of strings BestMotifs resulting from applying GREEDYMOTIFSEARCH(Dna,k,t).
 *   If at any step you find more than one Profile-most probable k-mer in a given string, use the
 *   one occurring first.
 *   
 *   GREEDYMOTIFSEARCH(Dna, k, t)
 *       BestMotifs ¡û motif matrix formed by first k-mers in each string
 *                     from Dna
 *       for each k-mer Motif in the first string from Dna
 *           Motif1 ¡û Motif
 *           for i = 2 to t
 *               form Profile from motifs Motif1, ¡­, Motifi - 1
 *               ***
 *               * here,when generate profile matrix, make A+1, T+1, G+1, and C+1 for each column, 
 *               * make sure there's no '0' probability.
 *               ***
 *               Motifi ¡û Profile-most probable k-mer in the i-th string
 *                         in Dna
 *           Motifs ¡û (Motif1, ¡­, Motift)
 *           if Score(Motifs) < Score(BestMotifs)
 *               BestMotifs ¡û Motifs
 *       output BestMotifs
 *	
 *   
 *   Sample Input:
 *    3 5
 *    GGCGTTCAGGCA
 *    AAGAATCAGTCA
 *    CAAGGAGTTCGC
 *    CACGTCAATCAC
 *    CAATAATATTCG
 *
 *	 Sample Output:
 *    CAG
 *    CAG
 *    CAA
 *    CAA
 *    CAA
 *   
 * @author Jeff
 *
 */

public class W03_GreedyMotifSearchPseudoCounts {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is Greedy Motif Search program.");
		
		//1st, create a Scanner to read-in all data from D:\BioinformaticsCoursera\TXT\greedy_data.txt
		String routine = "D:/BioinformaticsCoursera/TXT/";
		String doc_name = "dataset_160_9.txt";
		
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		int K_mer = readIn.nextInt();
		int T_seq = readIn.nextInt();
		
		//read-in T-seq sequences from the same document;
		ArrayList<String> DNA_List = new ArrayList<String>();
		
		while(readIn.hasNextLine()){
			
			String currSeq = readIn.nextLine();
			if(currSeq.length() > 1)
				DNA_List.add(currSeq);
		
		}//end while loop;
		
		System.out.println("K_mer: " + K_mer +", T_seq: " + T_seq +", DNA sequences: " + DNA_List.size());
		
		//printList(DNA_List);
		
		
		//2nd, generate bestMotifs;
		System.out.println("\nStep 2: initial first bestMotifs.");
		ArrayList<String> bestMotifs = generateBestMotifs(DNA_List, K_mer);
		
		//printList(bestMotifs);
		
		
		
		//3rd, update bestMotifs
		System.out.println("\nStep 3: update the best motifs.");
		bestMotifs = updateBestMotifs(bestMotifs, DNA_List, K_mer, T_seq);
		
		
		//4th, output bestMotifs;
		System.out.println("\nStep 4: Print out the best Motifs:");
		printList(bestMotifs);
		
		
		
		//cloase scanner;
		readIn.close();
	}//end of main();

	
	private static ArrayList<String> updateBestMotifs( ArrayList<String> bestMotifs, ArrayList<String> dna_list, int k_mer, int t_seq) {
		// TODO Auto-generated method stub
		
		//get the motifsScore of bestMotifs;
		int bestScore = motifsScore(bestMotifs);
		System.out.println("The inital best motifs score is: " + bestScore);
		
		

		
		int seqLen = dna_list.get(0).length(); 
		int numOfKmer = seqLen - k_mer +1;
		
		for(int i=0; i<numOfKmer; i++){
			
			ArrayList<String> currMotifs = new ArrayList<String>();
			
			String motif0 = dna_list.get(0).substring(i, i+k_mer);
			currMotifs.add(motif0);
			
			for(int j = 1; j<t_seq; j++){
				
				//create a matrix of profile base on the currMotifs (motifj - motifj);
				double[][] profileMatrix = createProMatrix( currMotifs );
				
				//get the subSequence from jTH dna_sequence with the most profile[][] probable;
				String motif_j = profileMostProbable(profileMatrix, dna_list.get(j), k_mer);
				
				//add the motif_j into currMotifs ArrayList;
				currMotifs.add(motif_j);
				
			}//end inner for j<t_seq loop;
			
			
			//if currMotifs Score() less than bestMotifs' Score, update the bestMotifs;
			int currScore = motifsScore(currMotifs);
			
			System.out.println("L138: Curr motifs score: " + currScore);
			printList(currMotifs);
			
			if( currScore < bestScore){
				
				System.out.println("a new bestScore: " + currScore);
				
				bestScore = currScore;
				bestMotifs = new ArrayList<String>(currMotifs);
			}
			
		}//enf for i<numOfKmer loop;
		
		
		
		return bestMotifs;
	}


	/************************
	 * Get Motifj ¡û Profile-most probable k-mer in the i-th string
	 * @param profileMatrix
	 * @param string
	 * @param k_mer
	 * @return
	 */
	private static String profileMostProbable(double[][] profileMatrix, String sequence, int k_mer) {
		// TODO Auto-generated method stub
		/************
		 * profileMatrix
		 * A
		 * T
		 * G
		 * C
		 */
		
		//1st, get 1st k_mer from this sequence
		String Kmer = sequence.substring(0, k_mer);
		double score = profileScore(Kmer, profileMatrix);
		
		int Len = sequence.length();
		int lastStartPoint = Len - k_mer + 1;
		
		for(int i=1; i<lastStartPoint; i++){
			
			String currSeq = sequence.substring(i, i+k_mer);
			
			double currPro = profileScore(currSeq, profileMatrix);
			if(currPro > score){
				
				Kmer = currSeq;
				score = currPro;
			}
			
		}//end for i<lastStartPoint loop;
		
		return Kmer;
		
	}//end profileMostProbable() method;

	
	/*****************
	 * calculate a profile score of a sequence against a profile matrix;
	 * @param kmer1
	 * @param profileMatrix
	 * @return
	 */
	private static double profileScore(String seq, double[][] profileMatrix) {
		// TODO Auto-generated method stub
		/************
		 * profileMatrix
		 * A
		 * T
		 * G
		 * C
		 */
		double score = 1;
		int Len = seq.length();
		
		for(int i=0; i<Len; i++){
			
			double currPro = 0;
			switch(seq.charAt(i)){
			
			case 'A': currPro = profileMatrix[0][i]; break;
			case 'T': currPro = profileMatrix[1][i]; break;
			case 'G': currPro = profileMatrix[2][i]; break;
			case 'C': currPro = profileMatrix[3][i]; break;
			
			}//end switch-case loop;
			
			score = score * currPro;
		
		}//end for i<Len loop;
		
		
		return score;
	
	}//end profileScore() method;


	/*************
	 * get a score of a motifs matrix;
	 * A T G C
	 * A T T C
	 * A A T T
	 * 0 1 1 1 miss matches to the most repeated Character; 
	 * Score = 3
	 * 
	 * @param bestMotifs
	 * @return
	 */
	private static int motifsScore(ArrayList<String> motifList) {
		// TODO Auto-generated method stub
		int row = motifList.size();
		int col = motifList.get(0).length();
		int score = 0;
		
		for(int i=0; i<col; i++){
			
			int A = 0;
			int T = 0; 
			int G = 0;
			int C = 0;
			
			for(int j=0; j<row; j++){
				
				switch(motifList.get(j).charAt(i)){
				
				case 'A': A++; break;
				case 'T': T++; break;
				case 'G': G++; break;
				case 'C': C++; break;
				
				}//end switch-case;
				
			}//end for j<row loop;
			
			
			int max = A;
			if( T > max) max = T;
			if( G > max) max = G;
			if( C > max) max = C;
			
			//System.out.println("A: " + A +", T: " + T + ", G: " + G +", C: " + C  +", max: " + max);
			
			score = score + row - max;
			
		}//end for i<col loop;
		
		return score;
		
	}//end motifsScore() method;


	/*********
	 * create a score/profile matrix according to an arrayList of string/sequences;
	 * @param motif0
	 * @return
	 */
	private static double[][] createProMatrix(ArrayList<String> motifs) {
		// TODO Auto-generated method stub
		int row = motifs.size();
		int col = motifs.get(0).length();
		
		/*****
		 * A =-=-=-=
		 * T =-=-=-=
		 * G =-=-=-=
		 * C =-=-=-=
		 */
		double[][] profile = new double[4][col];
		
		for(int i=0; i<col; i++){
			double total = motifs.size();
			double A = 1;
			double T = 1;
			double G = 1;
			double C = 1;
			
			for(int j=0; j<row; j++){
				
				switch(motifs.get(j).charAt(i)){
				
				case 'A': A++; break;
				case 'T': T++; break;
				case 'G': G++; break;
				case 'C': C++; break;
				
				}				
				
			}//end j<row loop;
			
			profile[0][i] = A/total;
			profile[1][i] = T/total;
			profile[2][i] = G/total;
			profile[3][i] = C/total;
			
		}//end for i<col loop;
		
		return profile;
		
	}//end createProMatrix() method;


	/*******************
	 * printout an arrayList
	 * @param bestMotifs
	 */
	private static void printList(ArrayList<String> bestMotifs) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<bestMotifs.size(); i++){
			System.out.println(bestMotifs.get(i));
		}
		
		System.out.println("There are " + bestMotifs.size() +" lines.");
		
	}//end printList() method;



	/******************************
	 * motif matrix formed by first k-mers in each string from DNA sequences;
	 * @param dNA_List
	 * @param k_mer
	 * @return
	 */
	private static ArrayList<String> generateBestMotifs(ArrayList<String> DNA_list, int k_mer) {
		// TODO Auto-generated method stub
		ArrayList<String> kmerList = new ArrayList<String>();
		
		for(int i=0; i<DNA_list.size(); i++){
			
			String subSeq = DNA_list.get(i).substring(0, k_mer);
			kmerList.add(subSeq);
		
		}//end for i<DNA_list.size() loop;
		
		return kmerList;
	
	}//end generateBestMotifs() method;

}//end of W03_GreedyMotifSearch class;
