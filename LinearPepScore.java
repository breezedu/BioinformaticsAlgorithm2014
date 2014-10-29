package courseraBioinformatics2014;

public class LinearPepScore implements Comparable<LinearPepScore>{
	
	String seq;
	int score;
	
	public LinearPepScore(String seq, int score){
		super();
		this.seq = seq;
		this.score = score;
		
	}
	
	//is this package, we will sort LinearPepScore in descend order;
	//so the compare will return -1 if this.score > that.score;
	public int compareTo(LinearPepScore linearPepScore) {  
	    
		return (this.getScore() > linearPepScore.getScore() ) ? -1:0 ;  
	}  
	
	
	public int getScore() {
	
		return this.score;
	}
	
	
	public String getSeq() {
		
		return this.seq;
	}
	
}//end class LinearpepScore class;
