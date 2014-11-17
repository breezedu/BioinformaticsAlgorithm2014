package courseraBioinformatics2014;


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
