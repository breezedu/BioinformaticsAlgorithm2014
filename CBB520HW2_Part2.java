package courseraBioinformatics2014;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*********************************
 * 
 * 
 */
public class CBB520HW2_Part2 {
	
	public static void main(String[] args) throws IOException{
		
		
		
		//1st, create an arrayList to store all genes;
		ArrayList<gene> geneList = createGeneArrayList();
		
		//2nd, create an arrayList of contigs.
		ArrayList<contig> contigList = createContigArrayList();
		
		//3rd, score contigs and genes;
		scoreContigList(geneList, contigList);
		
		
	}//end main();

	private static void scoreContigList(ArrayList<gene> geneList, ArrayList<contig> contigList) throws IOException {
		// TODO Auto-generated method stub
		String routine = "D:/";
		System.out.println("The output file will be put D:/");
		
		File output_file = new File(routine +"cbb520_output.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		int numOfContig = geneList.size();
		for(int i=0; i<numOfContig; i++){
			
			//Score one contig aginst all genes;
			scoreContig(geneList.get(i), contigList, output);
			
		}
		
		output.close();
		
	}//end of scoreContigList();
	
	

	private static void scoreContig(gene gene, ArrayList<contig> contigList, BufferedWriter output) throws IOException {
		// TODO Auto-generated method stub
		
		int numOfContigs = contigList.size();
		int score = 0;
		
		
		for(int i=0; i<numOfContigs; i++){
			
			String currContig = contigList.get(i).getSequence();
			
			if(compareTwoSeq(gene.getSequence(), currContig )) score++;
		}
		
		
		System.out.println("Score: " + score + ", Gene: " + gene.getName());
		
		output.write("Score" + "\t" + score +"\t" +"Gene \t" + gene.getName() +"\n");
		
	}//end scoreContig;
	
	
	
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
			
			if( Over50Identical(subSeqOfContig, geneSeq) ) return true;
			if( Over50Identical(subSeqOfContig, revgeneSeq)) return true;
			
		}//end for i<end loop;
		
		return false;
	}//end compareTwoSeq() method;
	
	
	
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
	

	private static ArrayList<contig> createContigArrayList() throws FileNotFoundException {
		// TODO Auto-generated method stub
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
		System.out.println("Step 2, read_in contigs from velvet or abyss outputs.");
		
		
		Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/abyss_k69_contigs.fa"));
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

	
	/***********
	 * read_in data from D:/BioinformaticsCoursera/TXT/cbb520/seqdump.txt
	 * create gene objects, put each object into the gene-arrayList;
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ArrayList<gene> createGeneArrayList() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
		System.out.println("Step 1, read_in genes from seqdump.txt.");
		Scanner genesReader = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/seqdump.txt"));
		
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
