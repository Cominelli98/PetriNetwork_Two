package it.unibs.ingesw;

import java.util.ArrayList;

public class Petri_network implements NameGiver, JsonAble{
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
	
	public int getFatherNetId() {
		return fatherNetId;
	};

	@Override
	public String getName() {
		return name;
	}
	
	public int getNetId() {
		return petriNetId;
	}
	
	public StringBuffer getTransitionsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<petriTransitions.size(); i++) {
			s.append(i + ")" + petriTransitions.get(i).getName() + " costo: " + petriTransitions.get(i).getCost() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLocationsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<petriLocations.size(); i++) {
			s.append(i + ")" + petriLocations.get(i).getName() + " marcatura iniziale:" + petriLocations.get(i).getToken() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLinksList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < petriNetLinks.size(); i++) {
			s.append(i + ")" + petriNetLinks.get(i).getOrigin().getName() + "---->" + petriNetLinks.get(i).getDestination().getName() + "\n");
		}
		return s;
	}
}
