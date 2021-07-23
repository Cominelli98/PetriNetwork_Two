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
		
		String variabileNik;
		try (FileWriter f = new FileWriter(data, exist)){
			
			variabileNik = gson.toJson(net, net.getClass())+"\n";
			f.append(variabileNik);
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
	
