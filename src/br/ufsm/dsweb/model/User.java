package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class User extends Model implements Serializable {
	private String fullname;
	private String username;
	private String password;
	
	public User() {
		setType(ModelType.USER);
	}
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
