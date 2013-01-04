package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.Date;

import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.util.Util;

public class Tweet extends Model implements Serializable {
	private User mUser;
	private Date mPubDate;
	private String mContent;
		
	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		this.mUser = user;
	}

	public Date getPubdate() {
		return mPubDate;
	}

	public void setPubdate(Date pubdate) {
		this.mPubDate = pubdate;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}

	@Override
	public String toCSV() {
		return getID()+DBCore.SEPARATOR+getUser().getID()+DBCore.SEPARATOR+
				Util.dateToString(getPubdate())+DBCore.SEPARATOR+getContent();
	}

	@Override
	public void fromCSV(String csv) {
		String[] vals = csv.split(DBCore.SEPARATOR);
		setID(Integer.parseInt(vals[0]));
		setUser(new UserDAO().getByID(Integer.parseInt(vals[1])));
		setPubdate(Util.stringToDate(vals[2]));
		setContent(vals[3]);
	}
}