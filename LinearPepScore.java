package courseraBioinformatics2014;

public class LinearPepScore implements Comparable<LinearPepScore>{
	
	String seq;
	int score;
	
	public LinearPepScore(String seq, int score){
		super();
		this.seq = seq;
		this.score = score;
		
	}
	
	public int compareTo(LinearPepScore linearPepScore) {  
	       return (this.score >= linearPepScore.score ) ? -1: (this.score < linearPepScore.score ) ? 1:0 ;  
	    }  
		
	
}//end class LinearpepScore class;
