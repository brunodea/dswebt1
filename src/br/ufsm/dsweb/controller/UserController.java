package br.ufsm.dsweb.controller;

import java.io.IOException;
import java.io.Serializable;
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
	private User mLoggedUser; //usuário logado.
	private String mViewUsername; //usuário sendo visualizado.

	public UserController() {
		mLoggedUser = new User();
		mViewUsername = null;
	}
	
	public void signup() {
		UserDAO udao = new UserDAO();
		FacesMessage message = null;
		if(udao.usernameExists(mLoggedUser.getUsername())) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalida username", "Username already taken.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			udao.save(mLoggedUser);
			login();
		}
	}
	
	public void login() {
		FacesMessage msg = null;
		
		User u = new UserDAO().login(mLoggedUser.getUsername(), mLoggedUser.getPassword());
		if(u != null) {
			mLoggedUser.fromCSV(u.toCSV());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", mLoggedUser);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
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
		return new UserDAO().getAllTweets(getCurrentUser());
	}
	
	public boolean getCurrentUserIsLoggedUser() {
		return mLoggedUser == getCurrentUser();
	}
	public User getCurrentUser() {
		User user = null;
		if(mViewUsername != null) {
			user = new UserDAO().getByUsername(mViewUsername);
		}
		if(user == null) {
			user = mLoggedUser;
		}
		return user;
	}
	public void setCurrentUser(User user) {
		mLoggedUser = user;
	}
	public User getLoggedUser() {
		return mLoggedUser;
	}
	
	public String getViewUsername() {
		return mViewUsername;
	}
	public void setViewUsername(String username) {
		mViewUsername = username;
	}
}
