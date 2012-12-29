package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBActions;

@Named
@SessionScoped
public class User extends Model implements Serializable {
	private String mFullName;
	private String mUsername;
	private String mPassword;
	
	public User() {
		setType(ModelType.USER);
	}
	
	public String getFullname() {
		return mFullName;
	}

	public void setFullname(String fullname) {
		this.mFullName = fullname;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		this.mUsername = username;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}

	@Override
	public String toCSV() {
		return getID()+","+getFullname()+","+getUsername()+","+getPassword();
	}

	@Override
	public void fromCSV(String csv) {
		String[] vals = csv.split(",");
		setID(Integer.parseInt(vals[0]));
		setFullname(vals[1]);
		setUsername(vals[2]);
		setPassword(vals[3]);
	}
}
