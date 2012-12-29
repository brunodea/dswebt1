package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBActions;
import br.ufsm.dsweb.util.Util;

@Named
@SessionScoped
public class Tweet extends Model implements Serializable {
	private User user;
	private Date pubdate;
	private String content;
	
	public Tweet() {
		setType(ModelType.TWEET);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toCSV() {
		return getID()+","+getUser().getID()+","+Util.dateToString(getPubdate())+","+getContent();
	}

	@Override
	public void fromCSV(String csv) {
		String[] vals = csv.split(",");
		setID(Integer.parseInt(vals[0]));
		setUser(DBActions.getUserByID(Integer.parseInt(vals[1])));
		setPubdate(Util.stringToDate(vals[2]));
		setContent(vals[3]);
	}
}
