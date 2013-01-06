package br.ufsm.dsweb.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DBCore {
	private static final String DB_PATH = "";
	public static final String SEPARATOR = ",,,,,"; //separador de colunas.

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
		HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
		col_vals.put(column, value);
		removeRowByCols(filename, col_vals);
	}
	public static synchronized void removeRowByCols(String filename, final HashMap<Integer, String> col_vals) {
		final String aux_filename = "aux_"+filename;
		File new_file = createFileIfNotExists(aux_filename);
		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String[] vals = row.split(SEPARATOR);
				boolean found = true;
				for(Integer col : col_vals.keySet()) {
					if(!vals[col].equals(col_vals.get(col))) {
						found = false;
						break;
					}
				}
				if(!found) {
					appendToFile(aux_filename, row);
				}
				return true;
			}
		});
		File old_file = createFileIfNotExists(filename);
		new_file.renameTo(old_file);
	}

	public static synchronized String getRowByCol(String filename, final int column, final String value) {
		HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
		col_vals.put(column, value);
		
		return getRowByCols(filename, col_vals);
	}
	public static synchronized String getRowByCols(String filename, final HashMap<Integer, String> col_vals) {
		String row = readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String[] vals = row.split(SEPARATOR);
				boolean found = true;
				for(Integer col : col_vals.keySet()) {
					if(!vals[col].equals(col_vals.get(col))) {
						found = false;
						break;
					}
				}
				return !found;
			}
		});
		
		return row;
	}
	
	public static synchronized void updateRowByCol(String filename, String new_row, int column, String value) {
		HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
		col_vals.put(column, value);
		
		updateRowByCols(filename, new_row, col_vals);
	}
	
	public static synchronized void updateRowByCols(String filename, final String new_row, final HashMap<Integer, String> col_vals) {
		final String aux_filename = "aux_"+filename;
		File new_file = createFileIfNotExists(aux_filename);
		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String[] vals = row.split(SEPARATOR);
				boolean found = true;
				for(Integer col : col_vals.keySet()) {
					if(!vals[col].equals(col_vals.get(col))) {
						found = false;
						break;
					}
				}
				if(found) {
					row = new_row;
				}
				appendToFile(aux_filename, row);
				return true;
			}
		});
		File old_file = createFileIfNotExists(filename);
		new_file.renameTo(old_file);
	}
	
	public static synchronized ArrayList<String> getAllRowsByCol(String filename, final int column, final String value) {
		HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
		col_vals.put(column, value);
		
		return getAllRowsByCols(filename, col_vals);
	}
	public static synchronized ArrayList<String> getAllRowsByCols(String filename, final HashMap<Integer, String> col_vals) {
		final ArrayList<String> res = new ArrayList<String>();

		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				String[] vals = row.split(SEPARATOR);
				boolean found = true;
				for(Integer col : col_vals.keySet()) {
					if(!vals[col].equals(col_vals.get(col))) {
						found = false;
						break;
					}
				}
				if(found) {
					res.add(row);
				}
				
				return true;
			}
		});
		
		return res;
	}
	public static synchronized ArrayList<String> getAllRows(String filename) {
		final ArrayList<String> res = new ArrayList<String>();

		readFile(filename, new RowReader() {
			@Override
			public boolean doSomething(String row, boolean has_next) {
				res.add(row);
				
				return true;
			}
		});
		
		return res;		
	}
	
	public static synchronized String getLastRow(String filename) {
		return readFile(filename, new RowReader() {			
			@Override
			public boolean doSomething(String row, boolean has_next) {
				return has_next;
			}
		});
	}
}
