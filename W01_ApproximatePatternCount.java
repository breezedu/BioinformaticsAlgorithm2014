package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/************
 * Computing_Count_d(Text, Pattern) 
 * simply requires us to compute the Hamming distance between Pattern and every k-mer substring of Text, 
 * which is achieved by the following pseudocode.
 * 
 * ApproximatePatternCount(Text, Pattern, d)
 *       count ← 0
 *       for i ← 0 to |Text| − |Pattern|
 *           Pattern′ ← Text(i , |Pattern|)
 *           if HammingDistance(Pattern, Pattern′) ≤ d
 *               count ← count + 1
 *       return count
 * 
 * @author Jeff
 *
 */
public class W01_ApproximatePatternCount {
	
	//create (declare) a new HammingDistanceProblem object;
	static W01_HammingDistanceProblem HamDis = new W01_HammingDistanceProblem();
			
	
	/*****************
	 * Main();
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		
		System.out.println("This is Approximate Pattern Count class.");
		
		//1st read_in data from D:\BioinformaticsCoursera\TXT\ApproximatePattern.txt
		//Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/ApproximatePattern.txt"));
		Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_9_6.txt"));
		
		int count=0;	//this is the # of reasonable sub-sequences;
		
		String oriSeq = read_in.next();
		String patternSeq = read_in.next();
		int d = read_in.nextInt();
		
		
		//2nd, get a sub-sequence from original-sequence, compare the sub-seq and pattern-seq with HammingDistanceProblem;
		//if the Hamming-distance <= d, count++;
		
		//the start index of the last sub-sequence;
		int end_index = oriSeq.length() - patternSeq.length();
		
		for(int i=0; i<=end_index; i++){
			
			String subSeq = oriSeq.substring(i, i+patternSeq.length());
			
			System.out.println("sub: " + subSeq +", pat: " + patternSeq);
			
			if(HamDis.run(subSeq, patternSeq) <= d) {
				count++;
			}
			
		}//end for i<end_index loop;
		
		//int miss = HamDis.run("AGTC", "AATC");
		
		System.out.println("Miss match is: " + count);
		
		
		//close read_in scanner;
		read_in.close();
		
	}//end main();

	
	public int run(String seqOne, String seqTwo, int dis) throws FileNotFoundException{
		
		
		//System.out.println("This is Approximate Pattern Count class.");
		
		//1st read_in data from D:\BioinformaticsCoursera\TXT\ApproximatePattern.txt
		//Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/ApproximatePattern.txt"));
		//Scanner read_in = new Scanner(new File("D:/BioinformaticsCoursera/TXT/dataset_9_6.txt"));
		
		//int count=0;	//this is the # of reasonable sub-sequences;
		
		//String oriSeq = read_in.next();
		//String patternSeq = read_in.next();
		//int d = read_in.nextInt();
		
		
		//2nd, get a sub-sequence from original-sequence, compare the sub-seq and pattern-seq with HammingDistanceProblem;
		//if the Hamming-distance <= d, count++;
		
		int count = 0;
		
		//the start index of the last sub-sequence;
		int end_index = seqOne.length() - seqTwo.length();
		
		for(int i=0; i<=end_index; i++){
			
			String subSeq = seqOne.substring(i, i+seqTwo.length());
			
			//System.out.println("sub: " + subSeq +", pat: " + seqTwo);
			
			if(HamDis.run(subSeq, seqTwo) <= dis) {
				count++;
			}
			
		}//end for i<end_index loop;
		
		//int miss = HamDis.run("AGTC", "AATC");
		
		//System.out.println("There are " + count + " reasonable miss match <= " + dis +"." );
		
				
		return count;
		
	}//end run();

	
}//end Week_01_ApproximatePatternCount class;
