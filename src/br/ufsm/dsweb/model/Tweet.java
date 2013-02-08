package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.util.Util;

@Entity
@Table(name="tweet")
public class Tweet extends Model implements Serializable {
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User mUser;
	@Column(name="pub_date", nullable=false)
	//@Temporal(TemporalType.TIMESTAMP)
	private Date mPubDate;
	@Column(name="content", nullable=false)
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