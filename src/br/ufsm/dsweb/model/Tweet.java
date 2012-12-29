package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.Date;

public class Tweet extends Model implements Serializable {
	private User user;
	private Date pub_date;
	private String content;
	
	public Tweet(int id) {
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPub_date() {
		return pub_date;
	}

	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
