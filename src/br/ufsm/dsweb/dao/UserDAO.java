package br.ufsm.dsweb.dao;

import br.ufsm.dsweb.model.User;

public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(new User());
	}
}
