package courseraBioinformatics2014;

class IndexScorePair implements Comparable<IndexScorePair>{
	
	private int index;
	private int score;
	
	public IndexScorePair(int index, int score){
		super();
		
		this.index = index;
		this.score = score;
	}
	
	public int getIndex(){
		return index;
	}
	public void setIndex(int index){
		this.index = index;
	}
	
	
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}
	
		

	public int compareTo(IndexScorePair pair) {
		// TODO Auto-generated method stub
		return( this.getScore() > pair.getScore() ) ? -1: (this.getScore() < pair.getScore()) ? 1:0 ;
	}

	
	
	/******
	public int compareTo(IndexScorePair comparePair){
		
		int compareScore = ((IndexScorePair) comparePair).getScore();
		
		return -(this.score - compareScore);
	}
	
	
	public static Comparator<IndexScorePair> ScoreComparator = new Comparator<IndexScorePair>(){
		
		public int compare(IndexScorePair pair1, IndexScorePair pair2){
			
			int score1 = pair1.getScore();
			int score2 = pair2.getScore();
			
			return score1 - score2;
			
		}
		
	};
	*/
	
}//end of IndexScorePair class