package br.ufsm.dsweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.FollowsDAO;
import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

@ManagedBean(name="userController")
@SessionScoped
public class UserController implements Serializable {
	private User mDumbUser; //usuário que não está no BD.

	public UserController() {
		mDumbUser = new User();
	}
	
	public void signup() {
		UserDAO udao = new UserDAO();
		FacesMessage message = null;
		if(udao.usernameExists(getDumbUser().getUsername())) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalida username", "Username already taken.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			udao.save(getDumbUser());
			login();
		}
	}
	
	public void login() {
		FacesMessage msg = null;
		
		User u = new UserDAO().login(getDumbUser().getUsername(), getDumbUser().getPassword());
		if(u != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", u);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml?username="+u.getUsername());
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossível logar.", "nome de usuário ou senha incorretos.");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Tweet> getTimeline() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(User user : getCurrentUserFollowing()) {
			tweets.addAll(new UserDAO().getAllTweets(user));
		}
		tweets.addAll(new UserDAO().getAllTweets(getCurrentUser()));
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});
		return tweets;
	}
	
	public boolean getCurrentUserIsLoggedUser() {
		User curr = getCurrentUser();
		User logged = getLoggedUser();
		return curr != null && logged != null && curr.getID() == logged.getID();
	}

	public User getCurrentUser() {
		User user = null;
		if(mDumbUser.getUsername() != null) {
			user = new UserDAO().getByUsername(mDumbUser.getUsername());
		}
		return user;
	}
	public User getLoggedUser() {
		User user = null;
		if(isLogged()) {
			user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		}
		return user;
	}
	public User getDumbUser() {
		return mDumbUser;
	}
	
	public boolean isLogged() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user");
	}
	public List<User> getCurrentUserFollowers() {
		return new FollowsDAO().getFollowers(getCurrentUser());
	}
	public List<User> getCurrentUserFollowing() {
		return new FollowsDAO().getFollowing(getCurrentUser());
	}
}
