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
 * @author Jeff
 *
 */
public class CBB520HW2_Part4 {
	
	public static void main(String[] args) throws IOException{
		
		
		//1st, create a Scanner to readin contigs data;
		//doc folder D:\BioinformaticsCoursera\TXT\cbb520
		
		System.out.println("Step 1: Create scanner for contigs data.");
		String routine = "D:/BioinformaticsCoursera/TXT/cbb520/contigs/";
		String doc_contig = "ver6h_contigs.fa";
		String doc_indel = "ver6h_var.vcf";
		
		Scanner read_contig = new Scanner(new File(routine + doc_contig));
		
		
		
		//2nd, build contig objects, put them in an ArrayList
		System.out.println("Step 2: Read_in contigs from velvet or abyss outputs.");
		
		ArrayList<contig> contigList = createContigArrayList(read_contig);
		
		System.out.println("There are " + contigList.size() + " contigs.");
		
		for(int i=0; i<contigList.size(); i++){
			
			System.out.print("  " + contigList.get(i).getName());
			
			if(contigList.get(i).getName().equals("7571"))
				System.out.println("THIS: 7571's length is " + contigList.get(i).getLength() +". " );
		}
		
		
		
		//3rd, another reader to read in SNPs position and the chromosomes they locate on
		System.out.println("Step 3: another scanner for SNPs position and chromosome name.");
		
		Scanner read_indel = new Scanner(new File(routine + doc_indel));
		
		String title = read_indel.nextLine();
		System.out.println( title );
		String info = read_indel.nextLine();
		System.out.println( info );
		
		
		//4th, create an output writter to record all SNPs: 
		//Contig35, base 35198 CGTATCTACG G GGCTAGTCAT should be CGTATCTACG A GGCTAGTCAT
		System.out.println("Step 4: write into document.");
		
		File output_file = new File(routine + doc_indel +"SNPs.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));
		
		output.write(doc_indel + " SNPs.txt\n");
		
		
		
		//5th, 
		while(read_indel.hasNextLine()){
			
			String currLine = read_indel.nextLine();
			
			String[] indels = currLine.split("\\s+");
			
			
			if(indels.length>2 && indels[3].length()<2){
				
				for(int i=0; i<contigList.size(); i++){
					
					if(contigList.get(i).getName().equals(indels[0])){
						
						int pos = Integer.parseInt(indels[1]);
						
						String front = contigList.get(i).getSequence().substring(pos-11, pos-1);
						String after = contigList.get(i).getSequence().substring(pos, pos+10);
						
				//		System.out.println(" " + contigList.get(i).getSequence().substring(pos-11, pos+10));
						
						System.out.println(indels[0] + "\tbase\t" + indels[1] +":\t" + front +"\t" + indels[3] +"\t" + after +"\tshould be:\t"
								           + front +"\t" + indels[4] + "\t" + after);
						
						
						output.write(indels[0] + "\tbase\t" + indels[1] +":\t" + front +"\t" + indels[3] +"\t" + after +"\tshould be:\t"
								           + front +"\t" + indels[4] + "\t" + after +"\n");
						
					}
					
				}
				
				
			}
			
		}
		
		
		//close scanners and output
		read_contig.close();
		read_indel.close();
		output.close();
		
	}//end of main();
	
	
	/*******************************************************
	 * ReadIn contig.fa documents outputed by Velvet or ABySS;
	 * Build contig objects based on the contigs data readed from *_contigs.fa file;
	 * put all these contig objects into an ArrayList;
	 * @param genesReader 
	 * 
	 * @return an ArrayList of contig objects
	 * @throws FileNotFoundException
	 */
	private static ArrayList<contig> createContigArrayList(Scanner genesReader) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//1st, read_in genes from D:/BioinformaticsCoursera/TXT/cbb520
		System.out.println("            Read_in contigs from velvet or abyss outputs.");
		
		//new ArrayList<gene>();
		ArrayList<contig> contigList = new ArrayList<contig>();
				
		//create a null new gene object;
		contig new_contig = new contig("", "", 0);
		
						
		while(genesReader.hasNextLine()){
							
			String currStr = genesReader.nextLine();
							
			if(currStr.length()>0 && currStr.charAt(0) == '>'){				
								
				new_contig.setLength(new_contig.getSequence().length() );
				
				if(new_contig.getLength() > 10)
					contigList.add(new_contig);
				
				String[] str = currStr.split("\\s");
				
				
				
				String name = str[0].substring(1);
				
		//		System.out.println( name );
				
				//create a new gene object;
				new_contig = new contig(name, "", 0);
								
								
			} else {
								
				new_contig.setSequence(new_contig.getSequence() + currStr);
			}
							
							
							
		}//end while loop;
				
		System.out.println("There are " + (contigList.size()-1) + " contigs longer than 100bp in the ArrayList.");
					
				
		contigList.remove(0);
		
		
		return contigList;
		
	}

	
}//end of CBB520HW2_Part4
