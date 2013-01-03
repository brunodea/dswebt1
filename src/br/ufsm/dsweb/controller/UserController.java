package br.ufsm.dsweb.controller;

import java.io.IOException;
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
	public void login(User user) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logado com sucesso!", "Bem vindo!");
		
		User u = new UserDAO().login(user.getUsername(), user.getPassword());
		if(u != null) {
			user.fromCSV(u.toCSV());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossível logar.", "nome de usuário ou senha incorretos.");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
