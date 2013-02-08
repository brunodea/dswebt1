package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
