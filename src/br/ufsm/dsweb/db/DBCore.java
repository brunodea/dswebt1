package br.ufsm.dsweb.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DBCore {
	private static String DB_PATH = "";

	private interface RowReader {
		public boolean doSomething(String row, boolean has_next);
	}
	
	private static synchronized File createFileIfNotExists(String filename) {
		File file = new File(DB_PATH+filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				file = null;
			}
		}
		return file;
	}
	
	private static synchronized String readFile(String filename, RowReader row_reader) {
		if(createFileIfNotExists(filename) == null) {
			return "";
		}
		String line = "";
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			line = br.readLine();
			while(line != null) {
				String next = br.readLine();
				if(!row_reader.doSomething(line, next != null)) {
					break;
				}
				line = next;
			}
			br.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return line == null ? "" : line;
	}
	
	public static synchronized void appendToFile(String filename, String row) {
		if(createFileIfNotExists(filename) == null) {
			return;
		}
		try {
			FileWriter fw = new FileWriter(filename, true);
			fw.write(row+"\n");
			fw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static synchronized void removeRowByCol(String filename, final int column, final String value) {
		final String aux_filename = "aux_"+filename;
		File new_file = createFileIfNotExists(aux_filename);
		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String[] vals = row.split(",");
				if(!vals[column].equals(value)) {
					appendToFile(aux_filename, row);
				}
				return true;
			}
		});
		File old_file = createFileIfNotExists(filename);
		new_file.renameTo(old_file);
	}

	public static String getRowByCol(String filename, final int column, final String value) {
		String row = readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				boolean keep = true;
				String[] vals = row.split(",");
				if(vals[column].equals(value)) {
					keep = false;
				}
				return keep;
			}
		});
		
		return row;
	}
	
	public static ArrayList<String> getAllRowsByCol(String filename, final int column, final String value) {
		final ArrayList<String> res = new ArrayList<String>();
		
		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String []values = row.split(",");
				if(values[column].equals(value)) {
					res.add(row);
				}
				
				return true;
			}
		});
		
		return res;
	}
	
	public static String getLastRow(String filename) {
		return readFile(filename, new RowReader() {			
			@Override
			public boolean doSomething(String row, boolean has_next) {
				if(!has_next) {
					return false;
				}
				return true;
			}
		});
	}
}
