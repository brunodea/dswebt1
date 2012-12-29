package br.ufsm.dsweb.model;

import java.io.Serializable;

public class Model implements Serializable {
	private int id;
	
	public Model(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
}
