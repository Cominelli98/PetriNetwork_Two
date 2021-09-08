package it.unibs.ingesw;

import java.util.ArrayList;

public final class Menu_Petri {

	private final static String MENUPETRI[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea una rete di Petri a partire da una rete esistente",
			"2:Visualizza una rete di Petri",
			"3:Salva una o più reti di Petri",
			"0:Indietro",
			"___________________________",
			
	};
	private final static String MESSAGGI_MENU[] = {
			"da quale rete vuoi partire?",
			"Come vuoi chiamare questa rete di Petri?",
			"Esiste già una rete di petri con questo nome",
			"Questa rete di petri esiste già",
	};
	private final static String NO_RETI="non ci sono reti salvate";
	private static final String NO_RETI_V = "Non ci sono reti di petri da visualizzare";
	private static final String NO_RETI_S = "Non ci sono reti di petri da salvare";
	
	public static void petriMenu(ArrayList<Petri_network> pn, ArrayList<Network> ns) {
		int select = -1;
		do {
			for (String s : MENUPETRI)
				System.out.println(s);
			
			select = Utility.readLimitedInt(0, MENUPETRI.length-4);
			switch(select) {
			
			case 0:
				break;
			case 1:
				createPetri(pn, ns);
				break;
			case 2: 
				if(pn.size() != 0)
					Menu_Visua.petriNetViewer(pn);
				else {
					System.out.println(NO_RETI_V);
				}
				break;
			case 3:
				if(pn.size() != 0)
					Menu_Salva.pSaveOption(pn);
				else 
					System.out.println(NO_RETI_S);
				break;
			}
		}while (select!=0);
		
	}
	
	private static void createPetri(ArrayList<Petri_network> pn, ArrayList<Network> ns) {
		if(ns.size() == 0) {
			System.out.println(NO_RETI);
			return;
		}
		System.out.println(Menu_Visua.getNetworksList(ns));
		int select = -1;
		System.out.println(MESSAGGI_MENU[0]);
		select = Utility.readLimitedInt(0, ns.size()-1);
		String name;
		do {
		System.out.println(MESSAGGI_MENU[1]);
		name = Utility.readString();
		if(checkPNetExistence(name, pn))
			System.out.println(MESSAGGI_MENU[2]);
		}while(checkPNetExistence(name, pn));
		Petri_network toAdd = new Petri_network(ns.get(select), name);
		setCosts(toAdd);
		setTokens(toAdd);
		if (!petriExistence(toAdd, pn))
			pn.add(toAdd);
		else
			System.out.println(MESSAGGI_MENU[3]);
	}
	
	
	
	private static boolean petriExistence(Petri_network toAdd, ArrayList<Petri_network> pn) {
		
		if (pn.size() == 0) {
			return false;
		}
		
		for (Petri_network pns : pn) {
			if(petriSingleCheck(pns, toAdd))
				return true;
		
		}
		return false;

	}
	
	
	
	private static boolean petriSingleCheck(Petri_network pn, Petri_network toCheck) {
		if (toCheck.getFatherNetId() == pn.getFatherNetId()){
			for(int i=0; i<toCheck.getLocations().size(); i++) {
				if(toCheck.getLocations().get(i).getToken() != pn.getLocations().get(i).getToken())
					return false;
			}
			
			for (int j=0; j<toCheck.getTransitions().size(); j++) {
				if(toCheck.getTransitions().get(j).getCost() != pn.getTransitions().get(j).getCost())
					return false;
			}
			return true;
		}
		return false;
	}
	
	private static boolean checkPNetExistence (String name, ArrayList<Petri_network> pn) {
		if(pn.size()>0) {
			for (Petri_network pns : pn) {
				if(Utility.nameCheck(pns, name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void setCosts(Petri_network toSet) {
		for (Petri_transition pt : toSet.getTransitions()) {
			System.out.println("Inserisci il costo della transizione "+pt.getName() + " (1 per default)");
			pt.setCost(Utility.readLowLimitInt(1));
	
		}
	}
	
	private static void setTokens(Petri_network toSet) {
		for (Petri_location pl : toSet.getLocations()) {
			System.out.println("Inserisci la marcatura iniziale della posizione "+pl.getName() + " (0 per default)");
			pl.setToken(Utility.readLowLimitInt(0));
		}
	}
}
