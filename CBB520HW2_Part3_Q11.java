package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*********************************
 * 1st, we map 10,000 lines of yjm993_R1.fastq and yjm993_R2.fastq back to the reference sequences
 * 	For each contigs files we got from Velvet and Abyss, first transfer them into index-files;
 * 2nd,use BWA to create sai files, example: abyss_r1.sai, and abyss_r2.sai;
 *  	merge them into abyss_short.sam file
 * 3rd, use Samtools to transfer abyss_sort.sam into abyss_sort.bam then abyss_sorted.bam file;
 * 4th use bamToBed tool to transfer sorted bam file into BED filr;
 * 
 * here, in this script, we will try to extract/analysis the "in-consistent" pairs in each original Contigs files
 * 
 * @author Jeff
 *
 */
public class CBB520HW2_Part3_Q11 {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("This is CBB520 HomeWork#2 Part3, Q11.");
		
		//1st, create a scanner to read_in data from D:\BioinformaticsCoursera\TXT\cbb520\Sam2Bed/
		
		String routine = "D:/BioinformaticsCoursera/TXT/cbb520/Sam2Bed/";
		//String doc_name = "ver6h.bed";
		
		//String doc_name = "abyss.bed";
		String doc_name = "velvet.bed";
		
		Scanner read_in = new Scanner(new File(routine + doc_name));
		
		//create an arrayList to store all lines of bed_line objects;
		ArrayList<bed_line> bedList = createBedList(read_in);
		
		int count = bruceForce(bedList);
		
		
		System.out.println("There are " + count + " in-consistent pairs.");
		//close scanner
		read_in.close();
	}//end of main();

	private static int bruceForce(ArrayList<bed_line> bedList) {
		// TODO Auto-generated method stub
		
		int size = bedList.size();
		int count = 0;
		
		for(int i=0; i<size; i++){
			
			bed_line currBed = bedList.get(i);
			
			for(int j=0; j<size; j++){
				
				bed_line comBed = bedList.get(j);
				
				if(comBed.name.equals(currBed.name)){
					
					if( Math.abs(comBed.pos_start - currBed.pos_start) > 600 || !comBed.chr.equals(currBed.chr))
						count++;
					
				}//end if names equals;
			}//end inner for j loop;
			
		}//end outer for i loop;
		
		return count;
	}//end bruceForce() method;
	
	
	/**************************
	 * Create an ArrayList to store all bed_line objects;
	 * @param read_in
	 * @return
	 */
	private static ArrayList<bed_line> createBedList(Scanner read_in) {
		// TODO Auto-generated method stub
		ArrayList<bed_line> bedList = new ArrayList<bed_line>();
		
		while(read_in.hasNextLine()){
			
			String currLine = read_in.nextLine();
			
			String[] element = currLine.split("\t");
			
			bed_line currBed = new bed_line();
			
			currBed.chr = element[0];
			currBed.name = element[3].substring(0, element[3].length()-1);
			
			currBed.pos_start = Integer.parseInt(element[1]);
			currBed.pos_end = Integer.parseInt(element[2]);
			
			bedList.add(currBed);
		}
		
		return bedList;
		
	}//end createBedList() method;
	
}//end of everything;

class bed_line {
	
	String chr;
	String name;
	
	int pos_start;
	int pos_end;
}
