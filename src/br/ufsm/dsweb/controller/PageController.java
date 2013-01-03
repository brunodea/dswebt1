package br.ufsm.dsweb.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="pageController")
@ViewScoped
public class PageController implements Serializable {
	public String getTopMenu() {
		return (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) ?
				"./topmenulogged.xhtml" : "./topmenu.xhtml";
	}
}
