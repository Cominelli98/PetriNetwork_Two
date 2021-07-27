package it.unibs.ingesw;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public final class WriteN {
	
	
	public static String SAVE_ERROR = "errore nel salvataggio";
	
	
	public static void save(Network net) {
		Gson gson = new Gson();
		File data = new File("data.txt");
		boolean exist = data.exists();
		
		try (FileWriter f = new FileWriter(data, exist)){
			
			f.append(gson.toJson(net, net.getClass())+"\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(SAVE_ERROR);
		}
		
	}
	
	public static void save(Petri_network pNet) {
		Gson gson = new Gson();
		File data = new File("petri_data.txt");
		boolean exist = data.exists();
		
		try (FileWriter f = new FileWriter(data, exist)){
			
			f.append(gson.toJson(pNet, pNet.getClass())+"\n");
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
	}
	
}
	
