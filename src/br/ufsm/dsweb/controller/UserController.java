package br.ufsm.dsweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

@ManagedBean(name="userController")
@SessionScoped
public class UserController implements Serializable {
	private User mDumbUser; //usuário que não está no BD.
	private String mSearchQuery;
	
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
	public void logout() {
		if(isLogged()) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void editprofile() {
		UserDAO udao = new UserDAO();
		User curr = mDumbUser;
		mDumbUser.setID(getCurrentUser().getID());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Changes made with success.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		udao.update(curr);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml?username="+curr.getUsername());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Tweet> getTimeline() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		/*for(User user : getCurrentUserFollowing()) {
			tweets.addAll(new UserDAO().getAllTweets(user));
			tweets.addAll(new RetweetDAO().getRetweetsOf(user));
		}
		tweets.addAll(new UserDAO().getAllTweets(getCurrentUser()));
		tweets.addAll(new RetweetDAO().getRetweetsOf(getCurrentUser()));
				
		Iterator<Tweet> it = tweets.iterator();
		while(it.hasNext()) {
			Tweet t = it.next();
			int num = 0;
			for(Tweet twt : tweets) {
				if(t.getID() == twt.getID()) {
					num++;
				}
				if(num > 1) {
					it.remove();
					break;
				}
			}
		}
		
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});*/
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
	public String getSearchQuery() {
		return mSearchQuery;
	}
	public void setSearchQuery(String query) {
		mSearchQuery = query;
	}
	
	public boolean isLogged() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user");
	}
	public boolean getIsLogged() {
		return isLogged();
	}
	public List<User> getCurrentUserFollowers() {
		return new ArrayList<User>();
		//return new FollowsDAO().getFollowers(getCurrentUser());
	}
	public List<User> getCurrentUserFollowing() {
		return new ArrayList<User>();
		//return new FollowsDAO().getFollowing(getCurrentUser());
	}
	
	private boolean containsSimilar(String[] list, String value) {
		for(int i = 0; i < list.length; i++) {
			String word = list[i].trim();
			if(value.indexOf(word) > -1) {
				return true;
			}
		}
		return false;
	}
	
	public void search() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml?query="+getSearchQuery());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public List<User> getSearchUsers() {
		setSearchQuery(getSearchQuery().trim());
		String[] words = getSearchQuery().split(" ");
		ArrayList<User> res = new ArrayList<User>();
		for(User user : new UserDAO().getAll()) {
			if(containsSimilar(words, user.getUsername())) {
				res.add(user);
			} else {
				for(int i = 0; i < words.length; i++) {
					if(containsSimilar(user.getFullname().split(" "), words[i])) {
						res.add(user);
						break;
					}
				}
			}
		}
		
		return res;
	}
}
