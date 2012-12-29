package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBActions;

@Named("model")
@SessionScoped
public abstract class Model implements Serializable {
	public enum ModelType {
		NONE, USER, TWEET;
	}
	
	private int mId;
	private ModelType mType;

	public Model() {
		this.mId = -1;
		setType(ModelType.NONE);
	}
	
	public int getID() {
		return mId;
	}
	public void setID(int id) {
		this.mId = id;
	}

	public ModelType getType() {
		return mType;
	}
	public void setType(ModelType type) {
		this.mType = type;
	}
	
	public abstract String toCSV();
	public abstract void fromCSV(String csv);
	
	public void save() {
		if(getID() < 0) {
			DBActions.saveModel(this);
		}
	}
}