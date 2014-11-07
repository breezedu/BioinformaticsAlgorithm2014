package courseraBioinformatics2014;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/********************************************************************************
 * Taking your set of longest, conserved, unique gene sequences you found above, 
 * write a program to test the quality of an S. cerevisiae genome assembly.
 * 
 */
public class CBB520HW2_Part2 {
	
	/********************************************************
	 * main() 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{		
		
		
		//1st, create an arrayList to store all genes;
		ArrayList<gene> geneList = createGeneArrayList();
		
		
		//2nd, create an arrayList of contigs.
		ArrayList<contig> contigList = createContigArrayList();
		
		
		//3rd, score contigs and genes;
		scoreContigList(geneList, contigList);
		
		
	}//end main();
	
	
	/*********************************************
	 * Score each gene to all contigs, to see if any contige would have a subsequence align with that gene
	 * total score will depends on how many genes get alignment;
	 *  
	 * @param geneList
	 * @param contigList
	 * @throws IOException
	 */
	private static void scoreContigList(ArrayList<gene> geneList, ArrayList<contig> contigList) throws IOException {
		// TODO Auto-generated method stub
		String routine = "D:/";
		System.out.println("The output file will be put D:/");
		
		File output_file = new File(routine +"K70_cbb520_output.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		output.write("This is the output of CBB520 HW2 Part2.\n");
		
		int numOfContig = geneList.size();
		for(int i=0; i<numOfContig; i++){
			
			//Score one contig aginst all genes;
			int score = scoreGene2Contig(geneList.get(i), contigList);
			
			output.write("Score\t" + score +"\t" +"Gene\t" + geneList.get(i).getName() +"\n");
		}
		
		output.close();
		
	}//end of scoreContigList();
	
	

	private static int scoreGene2Contig(gene gene, ArrayList<contig> contigList) {
		// TODO Auto-generated method stub
		
		int numOfContigs = contigList.size();
		int score = 0;
		
		
		for(int i=0; i<numOfContigs; i++){
			
			String currContig = contigList.get(i).getSequence();
			
			if(compareTwoSeq(gene.getSequence(), currContig )) score++;
		}
		
		
		System.out.println("Score: " + score + ", Gene: " + gene.getName());
		
		
		return score;
		
	}//end scoreContig;
	
	
	/**********************************************************************
	 * compare two sequence of different length, break the longer/later sequence into fractions
	 * each fraction would have equal length of sequence one;
	 * then call Over50Identical() method to check if the equal length subsequence has 50% 
	 * identical or not;
	 * if any of these sub-sequence has over 50% identical with sequence one, return true;
	 * else return false;
	 * @param geneSeq
	 * @param contigSeq
	 * @return
	 */
	private static boolean compareTwoSeq(String geneSeq, String contigSeq) {
		// TODO Auto-generated method stub
		
		int contigLen = contigSeq.length();
		int geneLen = geneSeq.length();
		
		int start = 0;
		int end = contigLen - geneLen +1;
		
		//get the reverse geneSeq;
		String revgeneSeq = "";
		for(int i=0; i<geneLen; i++){
			revgeneSeq = geneSeq.charAt(i) + revgeneSeq;
		}
		
		for(int i=start; i<end; i++){
			
			String subSeqOfContig = contigSeq.substring(i, i+geneLen);
			
			//not only the original sequence, but also the reverse sequence
			if( Over50Identical(subSeqOfContig, geneSeq) ) return true;
			if( Over50Identical(subSeqOfContig, revgeneSeq)) return true;
			
		}//end for i<end loop;
		
		return false;
	}//end compareTwoSeq() method;
	
	
	
	/***************************************************************
	 * compare two sequences of equal length;
	 * if there are more than 50% identical, return true;
	 * else return false;
	 * @param str1
	 * @param str2
	 * @return
	 */
	private static boolean Over50Identical(String str1, String str2) {
		// TODO Auto-generated method stub
		//str2 is the geneStr;
		
		int Len = str1.length();
		//System.out.print("len1 " + str1.length() +" len2 " + str2.length() );
		
		int ident = 0;
		for(int i=0; i<Len; i++){
			if(str1.charAt(i) == str2.charAt(i)) ident++;
		}
		
		if(ident >= Len/2) return true;
		
		return false;
	
	}//end Over50Identical() method;
	

	
	/*******************************************************
	 * ReadIn contig.fa documents outputed by Velvet or ABySS;
	 * Build contig objects based on the contigs data readed from *_contigs.fa file;
	 * put all these contig objects into an ArrayList;
	 * 
	 * @return an ArrayList of contig objects
	 * @throws FileNotFoundException
	 */
	private static ArrayList<contig> createContigArrayList() throws FileNotFoundException {
		// TODO Auto-generated method stub
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
		System.out.println("Step 2, read_in contigs from velvet or abyss outputs.");
		
		
		Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/abyss_k43_contigs.fa"));
		//Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/S288C_reference.fsa"));
		//Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/velvet_output_contigs.fa"));

	
		//new ArrayList<gene>();
		ArrayList<contig> contigList = new ArrayList<contig>();
				
		//create a new gene object;
		contig new_contig = new contig("", "", 0);
		
						
		while(genesReader.hasNextLine()){
							
			String currStr = genesReader.nextLine();
							
			if(currStr.length()>0 && currStr.charAt(0) == '>'){				
								
				new_contig.setLength(new_contig.getSequence().length() );
				
				if(new_contig.getLength() > 1000)
					contigList.add(new_contig);
								
				//create a new gene object;
				new_contig = new contig(currStr, "", 0);
								
								
			} else {
								
				new_contig.setSequence(new_contig.getSequence() + currStr);
			}
							
							
							
		}//end while loop;
				
		System.out.println("There are " + contigList.size() + " contigs longer than 1000bp in the ArrayList.");
					
		for(int i=0; i<contigList.size(); i++){
				
			System.out.println(contigList.get(i).getName() + "\n                 " + contigList.get(i).getLength() );
		}
				
		contigList.remove(0);
		//close scanner;
		genesReader.close();
				
		return contigList;
		
	}

	
	
	/******************************************************************************************************
	 * read_in data from D:/BioinformaticsCoursera/TXT/cbb520/seqdump.txt
	 * create gene objects, put each object into the gene-arrayList;
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ArrayList<gene> createGeneArrayList() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
		System.out.println("Step 1, read_in genes from seqdump.txt.");
		Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/seqdump_old.txt"));
		
		//Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/seqdump_old.txt"));
		
		
		//new ArrayList<gene>();
		ArrayList<gene> geneList = new ArrayList<gene>();
		
		//create a new gene object;
		gene newGene = new gene("","", 0);
		
				
		while(genesReader.hasNextLine()){
					
			String currStr = genesReader.nextLine();
					
			if(currStr.length()>0 && currStr.charAt(0) == '>'){				
						
				newGene.setLength( newGene.getSequence().length() );
						
				geneList.add(newGene);
						
				//create a new gene object;
				newGene = new gene("", "", 0);
						
				//assign the first line as the name of the new-gene;
				newGene.setName( currStr );
						
				newGene.setSequence("");
						
						
			} else {
						
				newGene.setSequence(newGene.getSequence() + currStr);
			}
					
					
					
		}//end while loop;
		
		System.out.println("There are " + geneList.size() + " genes in the ArrayList.");
			
		for(int i=0; i<geneList.size(); i++){
		
			System.out.println(geneList.get(i).getName() + "\n                 " + geneList.get(i).getLength() );
		}
		
		geneList.remove(0);
		//close scanner;
		genesReader.close();
		
		return geneList;
	}//end of createGeneArrayList() method;

}//end of everything in CBB520HW2_Part2 class



/****************************
 * gene class: Create Gene object
 * @author Jeff
 *
 */
class gene {
	
	private String name;
	private String sequence;
	private int length;
	
	public gene(String name, String sequence, int length){
		super();
		
		this.name = name;
		this.sequence = sequence;
		this.length = length;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSequence(){
		return sequence;
	}

	public void setSequence(String sequence){
		this.sequence = sequence;
	}
	
	public int getLength(){
		return length;
	}
	
	public void setLength(int length){
		this.length = length;
	}
	
	
	
}//end of gene class;


/**************************
 * Contig class: create contig objects
 * @author Jeff
 *
 */
class contig{
	
	private String name;
	private String sequence;
	private int length;
	
	public contig(String name, String sequence, int length){
		super();
		
		this.name = name;
		this.sequence = sequence;
		this.length = length;
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSequence(){
		return sequence;
	}

	public void setSequence(String sequence){
		this.sequence = sequence;
	}
	
	public int getLength(){
		return length;
	}
	
	public void setLength(int length){
		this.length = length;
	}
	
	
}//end of contig class;
