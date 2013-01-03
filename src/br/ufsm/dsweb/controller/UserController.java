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
	private User mUser;
	
	public UserController() {
		mUser = new User();
	}
	
	public void signup() {
		UserDAO udao = new UserDAO();
		FacesMessage message = null;
		if(udao.usernameExists(mUser.getUsername())) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalida username", "Username already taken.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			udao.save(mUser);
			login();
		}
	}
	
	public void login() {
		FacesMessage msg = null;
		
		User u = new UserDAO().login(mUser.getUsername(), mUser.getPassword());
		if(u != null) {
			mUser.fromCSV(u.toCSV());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", mUser);
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
		return new UserDAO().getAllTweets(getUser());
	}
	
	public User getUser() {
		return mUser;
	}
	public void setUser(User user) {
		mUser = user;
	}
}
