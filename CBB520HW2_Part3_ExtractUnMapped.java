package courseraBioinformatics2014;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CBB520HW2_Part3_ExtractUnMapped {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("Extract unmapped sequence from ver6h_short.sam document.");
		
		
		//1st, create a scanner to readin data
		//data document locates: D:\BioinformaticsCoursera\TXT\cbb520
		String routine = "D:/BioinformaticsCoursera/TXT/cbb520/";
		String sam_file = "verth";
		// String sam_file = "velvet";
		// String sam_file = "abyss";
		
		Scanner readIn = new Scanner(new File(routine + sam_file +"_short.sam"));


		//		Scanner readIn = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/abyss_short.sam"));
		//		Scanner readIn = new Scanner(new File("D:/BioinformaticsCoursera/TXT/cbb520/velvet_short.sam"));
		
		//2nd, create a writter to write unmapped sequence into a fastq file

		String doc_name = sam_file + "_unmapped.fastq";
		
		File output_file = new File(routine + doc_name);
		BufferedWriter output = new BufferedWriter(new FileWriter(output_file));	
		
		
		//the first 2000 lines are short information like: 
		/*********************
		@SQ     SN:45   LN:87
		@SQ     SN:46   LN:87
		@SQ     SN:50   LN:67
		@SQ     SN:53   LN:67
		@SQ     SN:54   LN:65
		@SQ     SN:55   LN:87
		*/
		
		//the line with * in the third colum means that read could not be aligned to the reference:
		/*********************
		 * D5ZTB5M1:245:D0GT6ACXX:8:1101:5240:3449 77      *       0       0       *       *       0       0       
		 *   AGCGACAGAGAGGGCAAAAGAAAATAAAAGTAAGATTTTAGTTTGTAATGGGAGGGGGGGTTTAGTCATGGAGTACAAGTGTGAGGAAAAGTAGTTGGGAG   
		 *   :;8=?:<<:<:+A@:C8EGA=?G;E9CCDG?F>>BF<FE4;DDAFFF>FE>F3;A';857B7?A<443@AA@?:?A@A>>3@A:4<>?A@B4:A@ABA9@@
		 
		 * D5ZTB5M1:245:D0GT6ACXX:8:1101:5240:3449 141     *       0       0       *       *       0       0       
		 *   TTTCTTCCCAAATTGTATCTCTCAATACGCATCAACCCATGTCAATTAAACACGCTGTATAGAGACTAGGCAGATCTGACGATCACCTAGCGACTCTCTCC   
		 *   8?@BDA>DABFDH@+<,ACAAI3A@?EBBBEHGBGG88?*)*:BBBGFHI<?F<EEG@GCCFF;@GGEEE>CA??@D>(.9;?AB>=AC@C=?<95A34:3

		 */
		int count = 0;
		int unmapped = 0;
		while(readIn.hasNextLine()){
			
			String currLine = readIn.nextLine();
			
			
			if(currLine.length() > 100){
				
				count++;
				
				String[] str = currLine.split("\t");
				
				if(str[2].equals("*")){
					
					unmapped++;
					
					output.write("@" + str[0] +"\n");
					output.write(str[9] + "\t" + str[10] + "\n\n");
					
				}//end inner if str[2].equals("*");
			
			}//end outer if currLine.length() > 100;
			
		}//end while loop;
		
		
		System.out.println("Of all the " +count + " reads, " + unmapped + " could not be mapped into the reference.");
		double ratio = (double) unmapped/count;
		System.out.println("The ratio is: " + ratio*100 + "%.");

		//close scanner and writter;
		readIn.close();
		output.close();
		
	}//end main();

}
