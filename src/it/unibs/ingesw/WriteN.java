package it.unibs.ingesw;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public final class WriteN {
	
	
	public static String SAVE_ERROR = "errore nel salvataggio";
	private static final String FILE_NET = "data.txt";
	private static final String FILE_PNET = "petri_data.txt";

	public static void save(JsonAble net) {
		Gson gson = new Gson();
		String data;
		if (net.getClass() == Network.class) 
			data = FILE_NET;
		else if (net.getClass() == Petri_network.class)
			data = FILE_PNET;
		else 
			throw new IllegalArgumentException("tipo non valido");
		File file = new File(data);
		boolean exist = file.exists();
		
		try (FileWriter f = new FileWriter(data, exist)){
			
			f.append(gson.toJson(net, net.getClass())+"\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(SAVE_ERROR);
		}
	}
	
	public static void fileCreation() {
		
		File f = new File("data.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File fp = new File("petri_data.txt");
		if (!fp.exists()) {
			try {
				fp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
	
