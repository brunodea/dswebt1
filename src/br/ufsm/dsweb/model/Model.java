package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Model implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private int mId;
	
	@Version
	@Column(name="version")
	private int version;

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
}