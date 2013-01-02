package br.ufsm.dsweb.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.model.User;

@ManagedBean(name="userController")
@ViewScoped
public class UserController implements Serializable {
	private String mUsername;
	private String mPassword;
	
	public void login() {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logado com sucesso!", "Bem vindo!");
		
		User user = new UserDAO().login(mUsername, mPassword);
		if(user != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossível logar.", "nome de usuário ou senha incorretos.");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}
}
