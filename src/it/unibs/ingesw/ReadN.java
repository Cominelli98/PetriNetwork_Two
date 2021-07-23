package it.unibs.ingesw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;



public final class ReadN {
	
	final static private int ID_INIZIALE = -1;
	
	/**
	 * legge da file tutte le righe in cui sono state le network
	 * @return	le righe del file data.txt
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> readNets() throws FileNotFoundException {
		String line;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))){
			line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	/**
	 * ritorna tutti gli id delle network salvate
	 * @return id delle network
	 */
	public static ArrayList<Integer> getNetIDsFromFile() {
		ArrayList<Integer> IDs = new ArrayList<>();
		try {
			ArrayList<String> nets = readNets();
			Gson gson = new Gson();
			for(String s : nets) {
				Network net = gson.fromJson(s, Network.class);
				IDs.add(net.getNetId());
			}
		} catch (FileNotFoundException e) {
			IDs.add(ID_INIZIALE);
		}
		return IDs;
	}
	
	public static boolean checkIdExistence(int id) {
		for (Integer intero : getNetIDsFromFile())
			if(intero == id)
				return true;
		return false;
	}
	
	/**
	 * ritorna la lista dei nomi delle network salvate utile per fare delle scelte 
	 * @return lista nomi network
	 */
	public static StringBuffer getNetNamesList() {
		StringBuffer names = new StringBuffer();
		try {
			ArrayList<String> nets = readNets();
			int i = 0;
			for(String s : nets) {
				Network net = jsonToNetwork(s);
				names.append(i+")"+net.getName());
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	/**
	 * metodo per ottenere i nomi delle reti salvate
	 * @return un arraylist di stringhe con tutti i nomi delle nostre network salvate
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> getNames() throws FileNotFoundException{
		ArrayList<String> nets = readNets();
		ArrayList<String> names = new ArrayList<>();
		for(String s : nets) {
			Network n = jsonToNetwork(s);
			names.add(n.getName());
		}
		return names;
	}
	
	public static boolean checkNetNameExistence(String s) {
		try {
			for (String string : getNames())
			if (string.equals(s))
				return true;
			return false;
		}catch(FileNotFoundException exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	/**
	 * metodo per caricare una network con un dato id
	 * @param netID della rete da caricare 
	 * @return la network con l'id specificato
	 */
	public static Network getNetworkFromFile(int netID) {
		Network net = null;
		try {
			ArrayList<String> nets = readNets();
			for(String s : nets) {
				net = jsonToNetwork(s);
				if(net.getNetId() == netID) {
					return net;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return net;
	}
	
	/**
	 * converte una stringa json in una network
	 * @param s
	 * @return network
	 */
	public static Network jsonToNetwork(String s) {
		Gson gson = new Gson();
		return gson.fromJson(s, Network.class);
	}
	
	public static boolean dataIsEmpty() {
		
		File f = new File("data.txt");
		if(f.length()==0)
			return true;
		return false;
	}
}
