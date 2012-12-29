package br.ufsm.dsweb.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class DBCore {
	private static String DB_PATH = "db/";

	public static boolean createFileIfNotExists(String filename) {
		boolean ret = true;
		File file = new File(DB_PATH+filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				ret = false;
			}
		}
		return ret;
	}
	
	public static void appendToFile(String filename, String row) {
		if(!createFileIfNotExists(filename)) {
			return;
		}
		try {
			FileWriter fw = new FileWriter(filename, true);
			fw.write(row);
			fw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static String getRowByCol(String filename, int column, String value) {
		if(!createFileIfNotExists(filename)) {
			return "";
		}
		String row = "";
		
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			String line = br.readLine();
			while(line != null) {
				line = br.readLine();
				String[] vals = line.split(",");
				if(vals[column].equals(value)) {
					row = line;
					break;
				}
			}
			br.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return row;
	}
}
