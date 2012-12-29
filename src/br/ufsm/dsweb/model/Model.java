package br.ufsm.dsweb.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
	private int id;

	public Model() {
		this.id = -1;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
	public abstract String toCSV();
	public abstract void fromCSV(String csv);
}