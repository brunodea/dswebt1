package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.TweetDAO;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

@ManagedBean(name="tweetController")
@ViewScoped
public class TweetController implements Serializable {
	private Tweet mTweet;
	
	public TweetController() {
		mTweet = new Tweet();
	}
	
	public void doTweet(User user) {
		mTweet.setUser(user);
		mTweet.setPubdate(new Date());
		new TweetDAO().save(mTweet);
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Your tweet was successfully save!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
		
	public Tweet getTweet() {
		return mTweet;
	}
	public void setTweet(Tweet tweet) {
		mTweet = tweet;
	}
}
