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
	
	public User login(String username, String password) {
		User res = null;
		String csv = DBCore.getRowByCol(getFilename(), 2, username);
		if(csv != null && !csv.equals("")) {
			User aux = new User();
			aux.fromCSV(csv);
			if(aux.getPassword().equals(password)) {
				res = aux;
			}
		}
		return res;
	}
}
