package br.ufsm.dsweb.dao;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.User;

@Named("userDAO")
@RequestScoped
public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(new User(), "users.csv");
	}

	public boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(getFilename(), 2, username); 
		return r != null && !r.equals("");
	}
}
