package it.unibs.ingesw;

import java.util.ArrayList;

public final class Converter {
	
	
	public static Petri_location toPetri (Location l, int petriNetId) {
		return new Petri_location(l, petriNetId);
	}
	
	public static ArrayList<Petri_location> toPetriLocations (ArrayList<Location> l, int petriNetId){
		
		ArrayList<Petri_location> temp = new ArrayList<>();
		for (Location location : l) {
			temp.add(toPetri(location, petriNetId));
		}
		return temp;
	}
	
	public static Petri_transition toPetri (Transition t, int petriNetId) {
		return new Petri_transition(t, petriNetId);
	}
	
	public static ArrayList<Petri_transition> toPetriTransitions (ArrayList<Transition> t, int petriNetId){
		
		ArrayList<Petri_transition> temp = new ArrayList<>();
		for (Transition transition : t) {
			temp.add(toPetri(transition, petriNetId));
		}
		return temp;
	}
	
	private static Node petriOrigin(ArrayList<Petri_location> pl, ArrayList<Petri_transition> pt, Link l) {
		if (l.getOrigin().getClass()==Location.class) {
			for (Location location : pl) {
				if(location.getNodeId() == l.getOrigin().getNodeId())
					return location;
			}
		}
		else 
			for(Transition transition : pt) {
				if(transition.getNodeId() == l.getOrigin().getNodeId())
					return transition;
			}
		return null;
	}
	
	private static Node petriDestination(ArrayList<Petri_location> pl, ArrayList<Petri_transition> pt, Link l) {
		if (l.getDestination().getClass()==Location.class) {
			for (Location location : pl) {
				if(location.getNodeId() == l.getDestination().getNodeId())
					return location;
			}
		}
		else 
			for(Transition transition : pt) {
				if(transition.getNodeId() == l.getDestination().getNodeId())
					return transition;
			}
		return null;
	}
	
	
	
	public static Petri_link toPetri (ArrayList<Petri_location> pl, ArrayList<Petri_transition> pt, Link link, int petriNetId) {
		return new Petri_link(petriOrigin(pl,pt, link), petriDestination(pl,pt, link), petriNetId);
	}
	
	public static ArrayList<Petri_link> toPetriLinks (ArrayList<Petri_location> pl, ArrayList<Petri_transition> pt, ArrayList<Link> l, int petriNetId){
		ArrayList<Petri_link> temp = new ArrayList<>();
		for(Link link : l) {
			temp.add(toPetri(pl, pt, link, petriNetId));
		}
		return temp;
	}
}
