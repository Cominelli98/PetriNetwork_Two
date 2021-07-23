package it.unibs.ingesw;

import java.util.ArrayList;


public class Network implements NameGiver{
	
	private ArrayList<Location> locations;
	private ArrayList<Transition> transitions;
	private ArrayList<Link> netLinks;
	private int netId; 
	private String name;
	static int network_id = 0;
	
	public Network (String name) {
		locations = new ArrayList<Location>();
		transitions = new ArrayList<Transition>();
		netLinks = new ArrayList<Link>();
		this.name = name;
		this.netId = ++network_id;
	}

	public void addLocation (String name) {
		locations.add(new Location(netId, locations.size() /*da cambiare se voglio aggiungere la rimozione*/, name));
	}


	public void addTransition (String name) {
		transitions.add(new Transition(netId, transitions.size(), name));
	}
	
	public void addLink (Link l) {
		netLinks.add(l);
	}
	/*
	public void removeLocation (Location l) {
		locations.remove(l);
	}
	
	public void removeTransition (Transition t) {
		transitions.remove(t);
	}
	
	public void removeLink (Link l) {
		netLinks.remove(l);
	}
	*/
	
	
	public ArrayList<Location> getLocations() {
		return locations;
	}
	
	public Location getLocation(int i) {
		return this.locations.get(i);
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
	
	public Transition getTransition(int i) {
		return this.transitions.get(i);
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public ArrayList<Link> getNetLinks() {
		return netLinks;
	}

	public void setNetLinks(ArrayList<Link> netLinks) {
		this.netLinks = netLinks;
	}

	public int getNetId() {
		return netId;
	}

	public void setNetId(int netId) {
		this.netId = netId;
	}
	
	
	public StringBuffer getTransitionsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<transitions.size(); i++) {
			s.append(i + ")" + transitions.get(i).getName() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLocationsList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i<locations.size(); i++) {
			s.append(i + ")" + locations.get(i).getName() + "\n");
		}
		return s;
	}
	
	public StringBuffer getLinksList() {
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < netLinks.size(); i++) {
			s.append(i + ")" + netLinks.get(i).getLocation().getName() + "----" + netLinks.get(i).getTransition().getName() + "\n");
		}
		return s;
	}
	
	public Transition getLastTransition() {
		return transitions.get(transitions.size()-1);
	}
	
	public Location getLastLocation() {
		return locations.get(locations.size()-1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
