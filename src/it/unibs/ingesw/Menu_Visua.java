package it.unibs.ingesw;

import java.util.ArrayList;

public final class Menu_Visua {
	
	final static String MENUVISUALIZZA[] = {
			"Cosa vuoi visualizzare?:",
			"___________________________",
			"1:Visualizza elenco locations",
			"2:Visualizza elenco transitions",
			"3:Visualizza elenco link ",
			"4:Visualizza rete complessiva",
			"0:Indietro",
			"___________________________"};
	private final static String NO_RETI ="non ci sono reti da visualizzare";
	private final static String NO_P_RETI ="non ci sono reti di petri da visualizzare";
	
	/**
	 * Stampa a video elenco di posti, transizioni, link e reti complessive
	 */
	public static void netViewer(ArrayList<Network> ns) {
		if(ns.size() == 0) {
			System.out.println(NO_RETI);
			return;
		}
		System.out.println("Quale rete vuoi visualizzare?");
		System.out.println(getNetworksList(ns));
		int i = Utility.readLimitedInt(0, ns.size());
		int select = -1;
		do {
			for (String s : MENUVISUALIZZA)
				System.out.println(s);
			select = Utility.readLimitedInt(0, MENUVISUALIZZA.length-4);
			
			switch(select) {
			case 1:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(ns.get(i).getLocationsList());
				break;
			case 2:
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(ns.get(i).getTransitionsList());
				break;
			case 3:
				System.out.println("ELENCO LINKS:");
				System.out.println(ns.get(i).getLinksList());
				break;
			case 4:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(ns.get(i).getLocationsList());
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(ns.get(i).getTransitionsList());
				System.out.println("ELENCO LINKS:");
				System.out.println(ns.get(i).getLinksList());
				break;
			case 0:
				break;
			}
		}while (select != 0);
		
	}
	
	
	
	public static StringBuffer getNetworksList(ArrayList<Network> ns ){
		StringBuffer s = new StringBuffer("");
		int i = 0;
		for (NameGiver n : ns) {
			s.append(i++ + ")" + n.getName() + "\n");
		}
		return s;
	}


	
	public static void petriNetViewer(ArrayList<Petri_network> pn) {
		if(pn.size() == 0) {
			System.out.println(NO_P_RETI);
			return;
		}
		System.out.println("Quale rete di Petri vuoi visualizzare?");
		System.out.println(getPNetworksList(pn));
		int i = Utility.readLimitedInt(0, pn.size());
		int select = -1;
		do {
			for (String s : MENUVISUALIZZA)
				System.out.println(s);
			select = Utility.readLimitedInt(0, MENUVISUALIZZA.length-4);
			
			switch(select) {
			case 1:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(pn.get(i).getLocationsList());
				break;
			case 2:
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(pn.get(i).getTransitionsList());
				break;
			case 3:
				System.out.println("ELENCO LINKS:");
				System.out.println(pn.get(i).getLinksList());
				break;
			case 4:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(pn.get(i).getLocationsList());
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(pn.get(i).getTransitionsList());
				System.out.println("ELENCO LINKS:");
				System.out.println(pn.get(i).getLinksList());
				break;
			case 0:
				break;
			}
		}while (select != 0);
		
	}
	
	public static StringBuffer getPNetworksList(ArrayList<Petri_network> pn){
		StringBuffer f = new StringBuffer("");
		int i = 0;
		for (Petri_network n : pn) {
			f.append(i++ + ")" + n.getName() + "\n");
		}
		return f;
	}
}
