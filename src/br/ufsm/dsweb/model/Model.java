package br.ufsm.dsweb.model;

import java.io.Serializable;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        Model obj2 = (Model) obj;
        return this.getID() == obj2.getID();
    }
	public abstract String toCSV();
	public abstract void fromCSV(String csv);
}