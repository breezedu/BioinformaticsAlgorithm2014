package courseraBioinformatics2014;

import java.util.ArrayList;
import java.util.Scanner;

/***********
 * From an input sequence length Len, generate all possible Kmers with that length;
 * 
 * ex:
 * L=2, Kmers = {AA, AC, AG, AT, CC, CA, CG, CT, GG, GA, GC, GT, TA, TG, TC, TT};
 * put them all in an arrayList;
 * 
 * @author Jeff
 *
 */
public class Week_01_GenerateAllKmers {

	public static void main(String[] args){
		
		System.out.println("This is Generate All Kmers program.");
		
		//1st, ask user to input the length of Kmers;
		System.out.print("Please input the length: k = ");
		
		Scanner input = new Scanner(System.in);
		int Len = input.nextInt();
		
		
		//2nd, create an ArrayList<> to store all possible kmers;
		ArrayList<String> kmers_list = new ArrayList<String>();
		
		//add A, G, T, C to the original kmers_list ArrayList, this include all possible kmers with Len=1;
		//or, we can just add "" to the original kmers_list, which means all possible kmers with Len=0;
		kmers_list.add("A");
		kmers_list.add("G");
		kmers_list.add("T");
		kmers_list.add("C");
		
		
		//prolong the kmers in the kmers_list ArrayList, add A/T/G/C to each of the sequence;
		for(int i=1; i<Len; i++){
			
			//create a new arrayList;
			ArrayList<String> kmers_new = new ArrayList<String>();
			int size = kmers_list.size();
			
			//get each sub-sequence from kmers_list ArrayList;
			for(int j=0; j<size; j++){
				
				kmers_new.add(kmers_list.get(j) + "A");
				kmers_new.add(kmers_list.get(j) + "C");
				kmers_new.add(kmers_list.get(j) + "G");
				kmers_new.add(kmers_list.get(j) + "T");
								
			}//end for j<size loop;
			
			kmers_list.clear();		//clear the original kmer_list ArrayList;
			kmers_list = kmers_new;	//let the kmers_list point to the new kmers_new ArrayList;
			
		}//end for i<Len loop;
		
		printArrayList_string(kmers_list);
		
		
		//close the input scanner;
		input.close();
		
	}//end main();

	
	public ArrayList<String> run(int Len){
		
		System.out.println("This is Generate All Kmers program.");
		//we do not need to input Len here;
		
		//2nd, create an ArrayList<> to store all possible kmers;
		ArrayList<String> kmers_list = new ArrayList<String>();
		
		//add A, G, T, C to the original kmers_list ArrayList, this include all possible kmers with Len=1;
		//or, we can just add "" to the original kmers_list, which means all possible kmers with Len=0;
		kmers_list.add("A");
		kmers_list.add("G");
		kmers_list.add("T");
		kmers_list.add("C");
		
		
		//prolong the kmers in the kmers_list ArrayList, add A/T/G/C to each of the sequence;
		for(int i=1; i<Len; i++){
			
			//create a new arrayList;
			ArrayList<String> kmers_new = new ArrayList<String>();
			int size = kmers_list.size();
			
			//get each sub-sequence from kmers_list ArrayList;
			for(int j=0; j<size; j++){
				
				kmers_new.add(kmers_list.get(j) + "A");
				kmers_new.add(kmers_list.get(j) + "C");
				kmers_new.add(kmers_list.get(j) + "G");
				kmers_new.add(kmers_list.get(j) + "T");
								
			}//end for j<size loop;
			
			kmers_list.clear();		//clear the original kmer_list ArrayList;
			kmers_list = kmers_new;	//let the kmers_list point to the new kmers_new ArrayList;
			
		}//end for i<Len loop;
		
		//printArrayList_string(kmers_list);		
		
		//return the kmers_list;
		return kmers_list;
		
	}//end run();
	
	
	
	/***********
	 * printout all string in the ArrayList;
	 * @param strArrayList
	 */
	private static void printArrayList_string(ArrayList<String> strArrayList) {
		// TODO printout all string in the arrayList;
		int size = strArrayList.size();
		for(int i=0; i<size; i++){
			
			System.out.println(" " + strArrayList.get(i));
		
		}//end for i<size loop;
		
		System.out.println("There are " + size +" strings in this ArrayList.");
		
	} //end of printArrayList() method;
	
	
}//end Week_01_GenerateAllKmers class;
