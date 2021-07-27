package it.unibs.ingesw;

public class Petri_location extends Location{
	
	private int token;

	public Petri_location(Location l, int petriNetId){
		super(petriNetId, l.getNodeId(), l.getName());
		this.token = 0;
	}
	
	/*public Petri_location(Location l, int petriNetId, int token) {
		super(petriNetId, l.getNodeId(), l.getName());
		this.token = token;
	}*/
	
	public void setToken (int token) {
		this.token = token;
	}
	
	public int getToken () {
		return this.token;
	}
	
}
