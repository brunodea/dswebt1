package br.ufsm.dsweb.dao;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.User;

public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(new User(), "users.csv");
	}
	public UserDAO(User user) {
		super(user, "users.csv");
	}

	public boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(getFilename(), 2, username); 
		return r != null && !r.equals("");
	}
}
