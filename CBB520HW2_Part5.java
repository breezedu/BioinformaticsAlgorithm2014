package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*********************************
 * 
 * @author Jeff
 *
 */
public class CBB520HW2_Part5 {
	
	public static void main(String[] args) throws IOException{
		
		
		//1st, create 2 Scanners to read-in bases changes;
		//doc folder D:\BioinformaticsCoursera\TXT\cbb520
		
		System.out.println("Step 1: Create scanner for contigs data.");
		String routine = "D:/BioinformaticsCoursera/TXT/cbb520/contigs/";
		
		String doc = "abyss";
		
		String doc_pilon = doc + "1M_pillon.changes";
		String doc_vcf = doc + "_variants.txt";
		
		

		Scanner read_vcf = new Scanner(new File(routine + doc_vcf));
		Scanner read_pilon = new Scanner(new File(routine + doc_pilon));
		
		
		
		//2nd, build ChromSNPs objects, put them in an ArrayList
		System.out.println("Step 2: Read_in changes from Pilon and Samtools outputs.");
		
		System.out.println("\t Step 2.1 Read in pillon output changes.");
		ArrayList<ChromSNPs> pilonList = createPilonArrayList(read_pilon);
		
		
		System.out.println("\t Step 2.2 Read in samtools output changes.");
		ArrayList<ChromSNPs> vcfList = createVcfArrayList(read_vcf);
		
		
		//3rd, check if these contig ArrayList are equal size;
		System.out.println("\nThere are " + vcfList.size() + " changes from Samtools outputs.");		
		System.out.println("There are " + pilonList.size() + " changes from Pilon outputs.");
		
		
		
		//4th, count total SNP numbers, remove duplicates;
		int count = mergeSNPLists(vcfList, pilonList);
		
		System.out.println("There are " + count + " SNPs in total.");
		
		
		//close scanners and output
		read_vcf.close();
		read_pilon.close();
		
		
	}//end of main();
	
	
	
	
	private static int mergeSNPLists(ArrayList<ChromSNPs> vcfList, ArrayList<ChromSNPs> pilonList) {
		// TODO Auto-generated method stub
		
		int sizeV = vcfList.size();
		int sizeP = pilonList.size();
		
		int count = 0;
		
		//create a HashMap to store all ChromSNPs
		HashMap<String, String> snpMap = new HashMap<String, String>();
		
		for(int i=0; i<sizeV; i++){
			
			snpMap.put(vcfList.get(i).getChrom(), vcfList.get(i).getPost());
		
		}//end for i<sizeV loop;
		
		
		//check each ChromSNPs in pilonList, to see if there's any duplicate to vcfList;
		for(int i=0; i<sizeP; i++){
			
			String chrom = pilonList.get(i).getChrom();
			String post = pilonList.get(i).getPost();
			
			//if both chrom and post are equal, then remove this ChromSNPs; cout++;
			if(snpMap.containsKey( chrom )){
				
				if(snpMap.get(chrom).equals(post))
					count++;
			}

		}//end for i<sizeP loop;
		
		
		return sizeV + sizeP - count;
		
	}//end mergeSNPLists() method;




	/********************
	 * get all SNPs found by Pilon
	 * @param read_pilon
	 * @return
	 */
	private static ArrayList<ChromSNPs> createPilonArrayList(Scanner read_pilon) {
		// TODO Auto-generated method stub
		ArrayList<ChromSNPs> pilonList = new ArrayList<ChromSNPs>();
		
		while(read_pilon.hasNextLine()){
			
			String currLine = read_pilon.nextLine();
			String[] split = currLine.split(":");
			String chrom = split[0];
			
			split = split[1].split("\\s");
			String post = split[0];
			
			System.out.println("chrom: |" + chrom +"|, post:|" + post +"|.");
			
			ChromSNPs currSNP = new ChromSNPs(chrom, post);
			pilonList.add(currSNP);

		}//end while loop;
		
		return pilonList;
		
	}//end chreatePionArrayList() method;


	/*******************************************************
	 * ReadIn contig.fa documents outputed by Velvet or ABySS;
	 * Build contig objects based on the contigs data readed from *_contigs.fa file;
	 * put all these contig objects into an ArrayList;
	 * @param genesReader 
	 * 
	 * @return an ArrayList of contig objects
	 * @throws FileNotFoundException
	 */
	private static ArrayList<ChromSNPs> createVcfArrayList(Scanner read_vcf) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
			
		//new ArrayList<gene>();
		ArrayList<ChromSNPs> vcfList = new ArrayList<ChromSNPs>();
		
		//create an arrayList to store none # lines;
		ArrayList<String> snpStr = new ArrayList<String>();
						
		while(read_vcf.hasNextLine()){
							
			String currStr = read_vcf.nextLine();
							
			if(currStr.length()>0 && currStr.charAt(0) != '#')
				snpStr.add(currStr);
							
		}//end while loop;
				
		
		int size = snpStr.size();
		
		for(int i=0; i<size; i++){
			
			String currStr = snpStr.get(i);
			String[] split = currStr.split("\t");
			//System.out.println("Chrom: " + split[0] +",  post:" + split[1]);
			
			ChromSNPs currSNP = new ChromSNPs(split[0], split[1]);
			vcfList.add(currSNP);
			
		}//end for i<size loop;
		
		
		return vcfList;
		
	}//end createVcfArrayList() method;

	
}//end of CBB520HW2_Part4

class ChromSNPs{
	
	private String chrom;
	private String post;
	
	public ChromSNPs(String chromName, String postion){
		super();
		
		this.chrom = chromName;
		this.post = postion;
		
	}
	
	
	public String getChrom() {
		return chrom;
	}
	public void setName(String chrom) {
		this.chrom = chrom;
	}
	
	public String getPost(){
		return post;
	}

	public void setPost(String postion){
		this.post = postion;
	}
		
	
}//end of ChromSNPs class;
