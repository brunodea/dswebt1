package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufsm.dsweb.util.ModelUtil;

@Named("model")
@SessionScoped
public abstract class Model implements Serializable {
	private int mId;

	public Model() {
		this.mId = -1;
	}
	
	public int getID() {
		return mId;
	}
	public void setID(int id) {
		this.mId = id;
	}

	public abstract String toCSV();
	public abstract void fromCSV(String csv);
}