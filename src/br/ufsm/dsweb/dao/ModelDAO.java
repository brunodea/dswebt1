package br.ufsm.dsweb.dao;

import java.util.HashMap;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Model;

public abstract class ModelDAO<T extends Model> {
	private T mModel;
	private String mFilename;

	public ModelDAO(T model, String filename) {
		mModel = model;
		mFilename = filename;
	}

	public T getByID(int model_id) {
		T res = null;
		String csv = DBCore.getRowByCol(mFilename, 0, String.valueOf(model_id));
		if(!csv.equals("")) {
			mModel.fromCSV(csv);
			res = mModel;
		}
		return res;
	}
	public void save(T model) {
		model.setID(getLastID()+1);
		DBCore.appendToFile(mFilename, model.toCSV());
	}
	public void removeByID(int model_id) {
		DBCore.removeRowByCol(mFilename, 0, model_id+"");
	}
	public void removeByCols(HashMap<Integer,String> col_vals) {
		DBCore.removeRowByCols(mFilename, col_vals);
	}
	public void update(T model) {
		DBCore.updateRowByCol(mFilename, model.toCSV(), 0, model.getID()+"");
	}
	
	public String getFilename() {
		return mFilename;
	}

	private int getLastID() {
		int last_id = -1;
		
		String last_row = DBCore.getLastRow(mFilename);
		if(!last_row.equals("")) {
			String[] vals = last_row.split(DBCore.SEPARATOR);
			last_id = Integer.parseInt(vals[0]);
		}
		
		return last_id;
	}
}
