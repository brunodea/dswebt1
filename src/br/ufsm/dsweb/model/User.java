package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ufsm.dsweb.db.DBCore;

@Entity
@Table(name="user")
public class User extends Model implements Serializable {
	@Column(name="full_name", nullable=false)
	private String mFullName;
	@Column(name="username", nullable=false)
	private String mUsername;
	@Column(name="password", nullable=false)
	private String mPassword;
	@Column(name="image_path", nullable=true)
	private String mImagePath;
	
	public User() {
		mImagePath = "../imgs/default.jpg";
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
	
	public String getImagePath() {
		return mImagePath;
	}
	
	public void setImagePath(String imagepath) {
		mImagePath = imagepath;
	}

	@Override
	public String toCSV() {
		return getID()+DBCore.SEPARATOR+getFullname()+DBCore.SEPARATOR+getUsername()+
				DBCore.SEPARATOR+getPassword()+DBCore.SEPARATOR+getImagePath();
	}

	@Override
	public void fromCSV(String csv) {
		String[] vals = csv.split(DBCore.SEPARATOR);
		setID(Integer.parseInt(vals[0]));
		setFullname(vals[1]);
		setUsername(vals[2]);
		setPassword(vals[3]);
		if(vals.length > 4) {
			setImagePath(vals[4]);
		}
	}
}
