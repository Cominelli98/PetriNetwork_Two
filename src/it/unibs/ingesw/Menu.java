package it.unibs.ingesw;

import java.util.ArrayList;


public class Menu {
	
	final String SELEZIONE = "Seleziona: ";
	
	final String MENUSTART[] = {
			"MENU:",
			"___________________________",
			"1:Crea Network",
			"2:Salva reti",
			"3:Visualizza reti",
			"4:Gestione reti di Petri", 
			"0:Esci",
			"___________________________",
			
	};
	
	final String MENUNETWORK[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea location",
			"2:Crea transition",
			"3:Crea link",
			"0:Indietro",
			"___________________________"};
	
	final String MENUSALVA[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Salva una rete",
			"2:Salva tutte le reti",
			"0:Indietro",
			"___________________________"};
	final String MENUVISUALIZZA[] = {
			"Cosa vuoi visualizzare?:",
			"___________________________",
			"1:Visualizza elenco locations",
			"2:Visualizza elenco transitions",
			"3:Visualizza elenco link ",
			"4:Visualizza rete complessiva",
			"0:Indietro",
			"___________________________"};
	
	final String MENUPETRI[] = {
			"Scegli cosa fare?:",
			"___________________________",
			"1:Crea una rete di Petri a partire da una rete esistente",
			"2:Visualizza una rete di Petri",
			"3:Salva una o più reti di Petri",
			"0:Indietro",
			"___________________________",
			
	};
	
	final String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	final String SALVATAGGIO = "Salvataggio eseguito";
	final String NOME_GIA_PRESENTE_RETE = "Esiste già una rete con questo nome";
	final String NOME_GIA_PRESENTE_LOCATION = "Esiste già una location con questo nome";
	final String NOME_GIA_PRESENTE_TRANSITION = "Esiste già una transition con questo nome";
	final String LINK_GIA_PRESENTE = "Link già presente";
	final String RICHIESTA_SALVATAGGIO = "Quale rete vuoi salvare?";
	
	private Network currentNetwork;
	private ArrayList<Network> networks;
	private ArrayList<Petri_network> petriNetworks;
	
	/**
	 * Costruisce un menù inizializzando l'array di reti e creando, se non esiste ancora, il file su cui verranno
	 * salvate
	 */
	public Menu() {
		
		networks = new ArrayList<>();
		petriNetworks = new ArrayList<>();
		WriteN.fileCreation();
		Network.network_id = Utility.getMax(ReadN.getNetIDsFromFile());
		
	}
	
	/**
	 *metodo principale di avvio del menù, switch che richiama tutte le funzionalità, 0 per uscire
	 */
	public void startMenu() {
		int select = -1;
		do {
			for (String s : MENUSTART) {
				System.out.println(s);
			}
			
			select = Utility.readLimitedInt(0, MENUSTART.length-4);
			switch (select) {
				case 1:
					createNetwork();
					break;
				case 2:
					if(networks.size() != 0)
						saveOption();
					else
						System.out.println("Non ci sono reti da salvare");
					break;
				case 3:
					if(networks.size() != 0)
						netViewer();
					else {
						System.out.println("Non ci sono reti da visualizzare");
					}
					break;
				case 4:
					//createPetri();
					petriMenu();
					break;
				case 0:
					Utility.close();
					break;
			}
		}while(select != 0);
		
	}
	

	private void petriMenu() {
		int select = -1;
		do {
			for (String s : MENUPETRI)
				System.out.println(s);
			
			select = Utility.readLimitedInt(0, MENUPETRI.length-4);
			switch(select) {
			
			case 0:
				break;
			case 1:
				createPetri();
				break;
			case 2: 
				if(petriNetworks.size() != 0)
					petriViewer();
				else {
					System.out.println("Non ci sono reti di Petri da visualizzare");
				}
				break;
			case 3:
				//petriSave();
				break;
			}
		}while (select!=0);
		
	}

	/**
	 * Metodo di gestione della creazione di reti:
	 * Crea una Network chiedendo all'utente un nome e imponendo la creazione di un posto, una transition
	 * e un link iniziale. Permette la creazione guidata di nuovi nodi e link garantendo correttezza sintattica
	 * Garantisce unicità previa check sul nome della rete.
	 */
	public void createNetwork() {
		String name;
		boolean exists = false;
		do {
			System.out.println("Inserisci il nome della nuova rete:");
			name = Utility.readString();
			exists = checkNetExistence(name);
			if (exists)
				System.out.println(NOME_GIA_PRESENTE_RETE);
		}while(exists);
		Network network = new Network(name);
		networks.add(network);
		currentNetwork = network;
		int select = -1;
		
		createBase();
		
		do {
			for (String s : MENUNETWORK) {
				System.out.println(s);
			}
			select = Utility.readLimitedInt(0, MENUNETWORK.length-4);
			int num = -1;
			switch (select) {
				
				case 0:
					break;
				case 1:
					createLocation();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getTransitionsList());
					num = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					createLink(currentNetwork.getTransition(num), currentNetwork.getLastLocation());
					num = -1;
					break;
				case 2:
					createTransition();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					num = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					createLink(currentNetwork.getLastTransition(), currentNetwork.getLocation(num));
					num = -1;
					break;
				case 3:
					int loc;
					int trans;
					System.out.println("ELENCO LOCATIONS");
					System.out.print(currentNetwork.getLocationsList());
					loc = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					System.out.println(ASKLINK);
					System.out.println("ELENCO TRANSITIONS");
					System.out.print(currentNetwork.getTransitionsList());
					trans = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					createLink(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc));
					break;
			}
		}while(select != 0);
	}
	
	/**
	 * Metodo dedicato al salvataggio delle reti su file, permette di salvare una rete unica o tutte quelle create.
	 */
	private void saveOption() {
		int select = -1;
		for (String s : MENUSALVA)
			System.out.println(s);
		select = Utility.readLimitedInt(0, MENUSALVA.length-4);
		switch (select) {
		case 0:
			break;
		case 1:
			System.out.println("Quale rete vuoi salvare?");
			System.out.println(getNetworksList());
			int i = Utility.readLimitedInt(0, networks.size());
			saveNetOnFile(networks.get(i));
			break;
		case 2:
			for (Network n : networks)
				saveNetOnFile(n);
			break;
		}
	}
	
	/**
	 * Stampa a video elenco di posti, transizioni, link e reti complessive
	 */
	private void netViewer() {
		System.out.println("Quale rete vuoi visualizzare?");
		System.out.println(getNetworksList());
		int i = Utility.readLimitedInt(0, networks.size());
		int select = -1;
		do {
			for (String s : MENUVISUALIZZA)
				System.out.println(s);
			select = Utility.readLimitedInt(0, MENUVISUALIZZA.length-4);
			
			switch(select) {
			case 1:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(networks.get(i).getLocationsList());
				break;
			case 2:
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(networks.get(i).getTransitionsList());
				break;
			case 3:
				System.out.println("ELENCO LINKS:");
				System.out.println(networks.get(i).getLinksList());
				break;
			case 4:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(networks.get(i).getLocationsList());
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(networks.get(i).getTransitionsList());
				System.out.println("ELENCO LINKS:");
				System.out.println(networks.get(i).getLinksList());
				break;
			case 0:
				break;
			}
		}while (select != 0);
		
	}
	
	private void petriViewer() {
		System.out.println("Quale rete di Petri vuoi visualizzare?");
		System.out.println(getPNetworksList());
		int i = Utility.readLimitedInt(0, petriNetworks.size());
		int select = -1;
		do {
			for (String s : MENUVISUALIZZA)
				System.out.println(s);
			select = Utility.readLimitedInt(0, MENUVISUALIZZA.length-4);
			
			switch(select) {
			case 1:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(petriNetworks.get(i).getLocationsList());
				break;
			case 2:
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(petriNetworks.get(i).getTransitionsList());
				break;
			case 3:
				System.out.println("ELENCO LINKS:");
				System.out.println(petriNetworks.get(i).getLinksList());
				break;
			case 4:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(petriNetworks.get(i).getLocationsList());
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(petriNetworks.get(i).getTransitionsList());
				System.out.println("ELENCO LINKS:");
				System.out.println(petriNetworks.get(i).getLinksList());
				break;
			case 0:
				break;
			}
		}while (select != 0);
		
	}
	
	private void createPetri() {
		
		System.out.println(getNetworksList());
		int select = -1;
		System.out.println("Da quale rete vuoi partire?");
		select = Utility.readLimitedInt(0, networks.size()-1);
		String name;
		do {
		System.out.println("Come vuoi chiamare questa rete di Petri?");
		name = Utility.readString();
		if(checkPNetExistence(name))
			System.out.println("Esiste già una rete di Petri con questo nome");
		}while(checkPNetExistence(name));
		Petri_network toAdd = new Petri_network(networks.get(select), name);
		setCosts(toAdd);
		setTokens(toAdd);
		if (!petriExistence(toAdd))
			petriNetworks.add(toAdd);
		else
			System.out.println("Questa rete di Petri esiste già");
	}
	
	
	
	private boolean petriExistence(Petri_network toAdd) {
		
		if (petriNetworks.size() == 0) {
			return false;
		}
		
		for (Petri_network pn : petriNetworks) {
			if(petriSingleCheck(pn, toAdd))
				return true;
		
		}
		return false;

	}
	
	
	
	private boolean petriSingleCheck(Petri_network pn, Petri_network toCheck) {
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

	/**
	 * Crea una base per la rete imponendo la creazione di un posto, una transizione e creando un link tra esse
	 */
	private void createBase() {
		createLocation();
		createTransition();
		createLink(currentNetwork.getTransition(0), currentNetwork.getLocation(0));
		
	}
	
	/**
	 * Medoto che crea un nuovo posto nella rete con l'inserimento di un "nome" univoco
	 */
	private void createLocation() {
		
		System.out.println("Inserisci il nome della nuova location: ");
		boolean isEqual;
		String name;
		do {
			isEqual = false;
			name = Utility.readString();
			for (Location l : currentNetwork.getLocations()) {
				if(Utility.nameCheck(l, name)) {
					isEqual = true;
					System.out.println(NOME_GIA_PRESENTE_LOCATION);
					break;
				}
			}
		}while(isEqual);
		currentNetwork.addLocation(name);
	}
	
	/**
	 * Medoto che crea una nuova transizione nella rete con l'inserimento di un "nome" univoco
	 */
	private void createTransition() {
			
			System.out.println("Inserisci il nome della nuova transition: ");
			boolean isEqual;
			String name;
			do {
				isEqual = false;
				name = Utility.readString();
				for (Transition l : currentNetwork.getTransitions()) {
					if(Utility.nameCheck(l, name)) {
						isEqual = true;
						System.out.println(NOME_GIA_PRESENTE_TRANSITION);
					}
				}
			}while(isEqual);
			currentNetwork.addTransition(name);
		}
	
	/**
	 * Aggiunge un link alla rete corrente 
	 * @param t un oggetto transizione
	 * @param l un oggetto location
	 */
	public void createLink(Transition t, Location l) {
		int scelta;
		System.out.println("Com'è orientato il collegamento? \n 0)" + 
				l.getName() + "---->" + t.getName() + "\n 1)" +
				t.getName() + "---->" + l.getName());
		scelta = Utility.readLimitedInt(0, 1);
		if (scelta == 0)
			if (checkLinkExistence(l, t))
				System.out.println(LINK_GIA_PRESENTE);
			else
				currentNetwork.addLink(new Link(l, t, currentNetwork.getNetId()));
		else 
			if (checkLinkExistence(t, l))
				System.out.println(LINK_GIA_PRESENTE);
			else {
				currentNetwork.addLink(new Link(t, l, currentNetwork.getNetId()));
			}
	}
	
	/**
	 * Metodo check sull'esistenza di un link
	 * @param t transition
	 * @param l location
	 * @return boolean
	 */
	private boolean checkLinkExistence(Node origin, Node destination) {
		
		for (int i = 0; i < currentNetwork.getNetLinks().size(); i++) {
			if(currentNetwork.getNetLinks().get(i).getOrigin().equals(origin) &&
					currentNetwork.getNetLinks().get(i).getDestination().equals(destination))
				return true;
		}
		return false;
	}
	
	/**
	 * Check sull'univocità del nome di una rete
	 * @param name
	 * @return yes se esiste già una rete con quel nome su file o in locale
	 */
	private boolean checkNetExistence(String name) {
		if(networks.size()>0) {
			for (Network n : networks) {
				if(Utility.nameCheck(n, name)){
					return true;
				}
			}
		}
		
		return (ReadN.checkNetNameExistence(name));
		
	}
	
	private boolean checkPNetExistence (String name) {
		if(petriNetworks.size()>0) {
			for (Petri_network pn : petriNetworks) {
				if(Utility.nameCheck(pn, name)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Ritorna uno StringBuffer con la lista di tutte le network create dall'avvio del programma
	 * @return StringBuffer formattato: 0)prima rete 1)seconda rete 2)terza rete etc.
	 */
	private StringBuffer getNetworksList(){
		StringBuffer s = new StringBuffer("");
		int i = 0;
		for (Network n : networks) {
			s.append(i++ + ")" + n.getName() + "\n");
		}
		return s;
	}
	
	private StringBuffer getPNetworksList(){
		StringBuffer f = new StringBuffer("");
		int i = 0;
		for (Petri_network n : petriNetworks) {
			f.append(i++ + ")" + n.getName() + "\n");
		}
		return f;
	}
	
	
	/**
	 * Metodo che richiama dalla classe statica WriteN il salvataggio su file delle reti create
	 */
	private void saveNetOnFile(Network n){
		if (!ReadN.checkIdExistence(n.getNetId()))
			WriteN.save(n);
		System.out.println(SALVATAGGIO + " rete " + n.getName());
	}
	
	private void setCosts(Petri_network toSet) {
		for (Petri_transition pt : toSet.getTransitions()) {
			System.out.println("Inserisci il costo della transizione "+pt.getName() + " (1 per default)");
			pt.setCost(Utility.readLowLimitInt(1));
	
		}
	}
	
	private void setTokens(Petri_network toSet) {
		for (Petri_location pl : toSet.getLocations()) {
			System.out.println("Inserisci la marcatura iniziale della posizione "+pl.getName() + " (0 per default)");
			pl.setToken(Utility.readLowLimitInt(0));
		}
	}
}
