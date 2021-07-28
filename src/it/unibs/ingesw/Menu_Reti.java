package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public final class Menu_Reti {
	
	final static String MENU_RETI[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea location",
			"2:Crea transition",
			"3:Crea link",
			"4:Visualizza una rete",
			"5:Salva una o più reti",
			"0:Indietro",
			"___________________________"};
	final static String NOME_GIA_PRESENTE_RETE = "Esiste già una rete con questo nome";
	final static String NOME_GIA_PRESENTE_LOCATION = "Esiste già una location con questo nome";
	final static String NOME_GIA_PRESENTE_TRANSITION = "Esiste già una transition con questo nome";
	final static String LINK_GIA_PRESENTE = "Link già presente";
	final static String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	private static final String NO_RETI_V = "Non ci sono reti di petri da visualizzare";
	private static final String NO_RETI_S = "Non ci sono reti di petri da salvare";
	
	/**
	 * Metodo di gestione della creazione di reti:
	 * Crea una Network chiedendo all'utente un nome e imponendo la creazione di un posto, una transition
	 * e un link iniziale. Permette la creazione guidata di nuovi nodi e link garantendo correttezza sintattica
	 * Garantisce unicità previa check sul nome della rete.
	 * @throws FileNotFoundException 
	 */
	public static void createNetwork(Network n, ArrayList<Network> ns )  {
		String name;
		boolean exists = false;
		do {
			System.out.println("Inserisci il nome della nuova rete:");
			name = Utility.readString();
			try {
				exists = checkNetExistence(name, ns);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exists)
				System.out.println(NOME_GIA_PRESENTE_RETE);
		}while(exists);
		Network network = new Network(name);
		ns.add(network);
		n = network;
		int select = -1;
		
		createBase(n);
		
		do {
			for (String s : MENU_RETI) {
				System.out.println(s);
			}
			select = Utility.readLimitedInt(0, MENU_RETI.length-4);
			int num = -1;
			switch (select) {
				
				case 0:
					break;
				case 1:
					createLocation(n);
					System.out.println(ASKLINK);
					System.out.print(n.getTransitionsList());
					num = Utility.readLimitedInt(0, n.getTransitions().size()-1);
					createLink(n.getTransition(num), n.getLastLocation(), n);
					num = -1;
					break;
				case 2:
					createTransition(n);
					System.out.println(ASKLINK);
					System.out.print(n.getLocationsList());
					num = Utility.readLimitedInt(0, n.getLocations().size()-1);
					createLink(n.getLastTransition(), n.getLocation(num), n);
					num = -1;
					break;
				case 3:
					int loc;
					int trans;
					System.out.println("ELENCO LOCATIONS");
					System.out.print(n.getLocationsList());
					loc = Utility.readLimitedInt(0, n.getLocations().size()-1);
					System.out.println(ASKLINK);
					System.out.println("ELENCO TRANSITIONS");
					System.out.print(n.getTransitionsList());
					trans = Utility.readLimitedInt(0, n.getTransitions().size()-1);
					createLink(n.getTransition(trans), n.getLocation(loc), n);
					break;
				case 4:
					if(ns.size() != 0)
						Menu_Visua.netViewer(ns);
					else {
						System.out.println(NO_RETI_V);
					}
					break;
				case 5:
					if(ns.size() != 0)
						Menu_Salva.saveOption(ns);
					else 
						System.out.println(NO_RETI_S);
					break;
			}
		}while(select != 0);
	}
	
	/**
	 * Crea una base per la rete imponendo la creazione di un posto, una transizione e creando un link tra esse
	 */
	private static void createBase(Network n) {
		createLocation(n);
		createTransition(n);
		createLink(n.getTransition(0), n.getLocation(0), n);
		
	}
	
	/**
	 * Metodo che crea un nuovo posto nella rete con l'inserimento di un "nome" univoco
	 */
	private static void createLocation(Network n) {
		
		System.out.println("Inserisci il nome della nuova location: ");
		boolean isEqual;
		String name;
		do {
			isEqual = false;
			name = Utility.readString();
			for (Location l : n.getLocations()) {
				if(Utility.nameCheck(l, name)) {
					isEqual = true;
					System.out.println(NOME_GIA_PRESENTE_LOCATION);
					break;
				}
			}
		}while(isEqual);
		n.addLocation(name);
	}
	
	/**
	 * Medoto che crea una nuova transizione nella rete con l'inserimento di un "nome" univoco
	 */
	private static void createTransition(Network n) {
			
			System.out.println("Inserisci il nome della nuova transition: ");
			boolean isEqual;
			String name;
			do {
				isEqual = false;
				name = Utility.readString();
				for (Transition l : n.getTransitions()) {
					if(Utility.nameCheck(l, name)) {
						isEqual = true;
						System.out.println(NOME_GIA_PRESENTE_TRANSITION);
					}
				}
			}while(isEqual);
			n.addTransition(name);
		}
	
	/**
	 * Aggiunge un link alla rete corrente 
	 * @param t un oggetto transizione
	 * @param l un oggetto location
	 */
	public static void createLink(Transition t, Location l, Network n) {
		int scelta;
		System.out.println("Com'è orientato il collegamento? \n 0)" + 
				l.getName() + "---->" + t.getName() + "\n 1)" +
				t.getName() + "---->" + l.getName());
		scelta = Utility.readLimitedInt(0, 1);
		if (scelta == 0)
			if (checkLinkExistence(l, t, n))
				System.out.println(LINK_GIA_PRESENTE);
			else
				n.addLink(new Link(l, t, n.getNetId()));
		else 
			if (checkLinkExistence(t, l, n))
				System.out.println(LINK_GIA_PRESENTE);
			else {
				n.addLink(new Link(t, l, n.getNetId()));
			}
	}
	
	/**
	 * Metodo check sull'esistenza di un link
	 * @param t transition
	 * @param l location
	 * @return boolean
	 */
	private static boolean checkLinkExistence(Node origin, Node destination, Network n) {
		
		for (int i = 0; i < n.getNetLinks().size(); i++) {
			if(n.getNetLinks().get(i).getOrigin().equals(origin) &&
					n.getNetLinks().get(i).getDestination().equals(destination))
				return true;
		}
		return false;
	}
	
	/**
	 * Check sull'univocità del nome di una rete
	 * @param name
	 * @return yes se esiste già una rete con quel nome su file o in locale
	 */
	private static boolean checkNetExistence(String name, ArrayList<Network> ns) throws FileNotFoundException{
		if(ns.size()>0) {
			for (Network n : ns) {
				if(Utility.nameCheck(n, name)){
					return true;
				}
			}
		}
		
		return ReadN.checkNetNameExistence(name, Network.class);
		
	}
}
