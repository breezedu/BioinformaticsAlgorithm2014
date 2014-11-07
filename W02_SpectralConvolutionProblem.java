package courseraBioinformatics2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**********************************
 * https://stepic.org/lesson/The-Spectral-Convolution-Saves-the-Day-104/
 * 		   step/4?course=Bioinformatics-Algorithms&unit=425
 * 
 * Spectral Convolution Problem: Compute the convolution of a spectrum.
 *    Input: A collection of integers Spectrum.
 *    Output: The list of elements in the convolution of Spectrum. If an element has
 *    multiplicity k, it should appear exactly k times; you may return the elements in any order.
 *
 * CODE CHALLENGE: Solve the Spectral Convolution Problem.
 *
 * Sample Input:
 *    0 137 186 323
 *
 * Sample Output:
 *    137 137 186 186 323 49
 * 
 * @author Jeff
 *
 */
public class W02_SpectralConvolutionProblem {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.out.println("Solve the Spectral Convolution Problem.");
		
		//1st, create an arrayList of mass spectrum
		String routine = "D:/BioinformaticsCoursera/TXT/";
		String doc_name = "spectral_convolution_data.txt";
		
		ArrayList<Integer> massList = readMassList(routine, doc_name);
		
		Collections.sort(massList);
		
		
		//2nd, create a convolution ArrayList;
		ArrayList<Integer> convoList = new ArrayList<Integer>();
		
		for(int i=0; i<massList.size(); i++){
			
			for(int j=0; j<i; j++){
				
				int convolution = massList.get(i) - massList.get(j);
				
				if(convolution > 0)
					convoList.add(convolution);
			}
		}
		
		
		Collections.sort(convoList);
		
		printArrayList(convoList);
		
		
		//3rd, create a hashMap to store each mass peak and it's repeats;
		HashMap<Integer, Integer> massRepeat = new HashMap<Integer, Integer>();
		ArrayList<Integer> soloMassList = new ArrayList<Integer>();
		
		for(int i=0; i<convoList.size(); i++){
			
			if(massRepeat.containsKey(convoList.get(i))){
				
				int repeat = massRepeat.get(convoList.get(i)) + 1;
				massRepeat.put(convoList.get(i), repeat);
				
			} else {
				
				massRepeat.put(convoList.get(i), 1);
				soloMassList.add(convoList.get(i));
				
			}//end if-else conditions;
			
		}//end for i<convoList.size() loop;
		
		
		
		//create IndexScorePair objects;
		ArrayList<IndexScorePair> pairList = new ArrayList<IndexScorePair>();
		
		for(int i=0; i<soloMassList.size(); i++){
			
			IndexScorePair currPair = new IndexScorePair(i, massRepeat.get(soloMassList.get(i)));
			pairList.add(currPair);
		}
		
		Collections.sort(pairList);
		
		//printout the sorted list, with repeats:
		System.out.println("\n\nPrintout the sorted list, with repeats!");
		
		for(int i=0; i<pairList.size(); i++){
			
			int index = pairList.get(i).getIndex();			
			System.out.println();
			
			for(int j=0; j<massRepeat.get( soloMassList.get(index) ); j++){
				
				System.out.print(" " + soloMassList.get(index));
			}
		}

		
	}//end of main();

	private static void printArrayList(ArrayList<Integer> List) {
		// TODO Auto-generated method stub
		
		int Len = List.size();
		for(int i=0; i<Len; i++){
			
			if(i%100 == 0) System.out.println();
			
			System.out.print(" " + List.get(i));
		}
	}//end printArrayList() method;
	

	private static ArrayList<Integer> readMassList(String routine, String doc_name) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<Integer> massList = new ArrayList<Integer>();
		
		Scanner readIn = new Scanner(new File(routine + doc_name));
		
		while(readIn.hasNext()){
			
			int mass = readIn.nextInt();
			massList.add(mass);
			
		}
		
		readIn.close();
		
		return massList;
		
	}//end readMassList method;

}//end of W02_SpectralConvolutionProblem class;
