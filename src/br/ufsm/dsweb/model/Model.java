package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public abstract class Model implements Serializable {
	public enum ModelType {
		NONE, USER, TWEET;
	}
	
	private int id;
	private ModelType type;

	public Model() {
		this.id = -1;
		setType(ModelType.NONE);
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}

	public ModelType getType() {
		return type;
	}
	public void setType(ModelType type) {
		this.type = type;
	}
	
	public abstract String toCSV();
	public abstract void fromCSV(String csv);
}