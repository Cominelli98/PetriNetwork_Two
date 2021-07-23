package it.unibs.ingesw;

import java.util.ArrayList;

public class Petri_network {
	private ArrayList<Petri_location> petriLocations;
	private ArrayList<Petri_transition> petriTransitions;
	private ArrayList<Petri_link> petriNetLinks;
	private int petriNetId; 
	private int fatherNetId;
	private String name;
	static int petriNetworkId = 0;
	
	public Petri_network(Network n, String name) {
		this.petriNetId = ++petriNetworkId;
		petriLocations = Converter.toPetriLocations(n.getLocations(), petriNetId);
		petriTransitions = Converter.toPetriTransitions(n.getTransitions(), petriNetId);
		petriNetLinks = Converter.toPetriLinks(petriLocations, petriTransitions, n.getNetLinks(), petriNetId);
		this.fatherNetId = n.getNetId();
		this.name = name;
	}
	
	public ArrayList<Petri_location> getLocations(){
		return petriLocations;
	}
	
	public ArrayList<Petri_transition> getTransitions(){
		return petriTransitions;
	}
}
