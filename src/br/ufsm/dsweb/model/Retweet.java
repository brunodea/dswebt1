package br.ufsm.dsweb.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufsm.dsweb.dao.TweetDAO;
import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.db.DBCore;

@Entity
@Table(name="retweet")
public class Retweet extends Model {

	@ManyToOne
	@JoinColumn(name="user_id")
	private User mUser;
	@ManyToOne
	@JoinColumn(name="tweet_id")
	private Tweet mTweet;
	
	public Tweet getTweet() {
		return mTweet;
	}

	public void setTweet(Tweet tweet) {
		this.mTweet = tweet;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		this.mUser = user;
	}

	@Override
	public String toCSV() {
		return getUser().getID()+DBCore.SEPARATOR+getTweet().getID();
	}

	@Override
	public void fromCSV(String csv) {
		String[] vals = csv.split(DBCore.SEPARATOR);
		setUser(new UserDAO().getByID(Integer.parseInt(vals[0])));
		setTweet(new TweetDAO().getByID(Integer.parseInt(vals[1])));
	}
}
