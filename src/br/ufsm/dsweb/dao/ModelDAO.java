package br.ufsm.dsweb.dao;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Model;


public abstract class ModelDAO<T extends Model> {
	private T mSubModel;
	private String mFilename;
	public ModelDAO(T submodel, String filename) {
		mSubModel = submodel;
		mFilename = filename;
	}

	public T getByID(int model_id) {
		T res = null;
		String csv = DBCore.getRowByCol(mFilename, 0, String.valueOf(model_id));
		if(!csv.equals("")) {
			mSubModel.fromCSV(csv);
			res = mSubModel;
		}
		return res;
	}
	public void save() {
		mSubModel.setID(getLastID()+1);
		DBCore.appendToFile(mFilename, mSubModel.toCSV());
	}
	public int getLastID() {
		int last_id = -1;
		
		String last_row = DBCore.getLastRow(mFilename);
		if(!last_row.equals("")) {
			String[] vals = last_row.split(",");
			last_id = Integer.parseInt(vals[0]);
		}
		
		return last_id;
	}
	
	public String getFilename() {
		return mFilename;
	}
}
