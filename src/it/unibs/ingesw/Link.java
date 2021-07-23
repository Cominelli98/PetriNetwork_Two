package it.unibs.ingesw;

public class Link {
	
	private Node origin;
	private Node destination;		
	private int netId;
	
	public Link (Node origin, Node destination, int netId) {
		this.origin = origin;
		this.destination = destination;
		this.netId = netId;
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}


}
