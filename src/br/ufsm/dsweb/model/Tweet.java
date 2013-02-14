package br.ufsm.dsweb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;

@Entity
@Table(name="tweet")
public class Tweet extends Model implements Serializable {
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User mUser;
	
	@ManyToMany(mappedBy="mRetweets")
	private List<User> mRetweeters;

	@Column(name="pub_date", nullable=false)
	//@Temporal(TemporalType.TIMESTAMP)
	private Date mPubDate;
	@Column(name="content", nullable=false)
	private String mContent;

	public Tweet() {
		Hibernate.initialize(mRetweeters);
	}
	
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
	
	public List<User> getRetweeters() {
		return mRetweeters;
	}
	public void setRetweeters(List<User> retweeters) {
		mRetweeters = retweeters;
	}
}