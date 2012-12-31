package br.ufsm.dsweb.util;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.User;

public class DBUtil {
	public static boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(ModelUtil.filenameFromModel(new User()), 2, username); 
		return r != null && !r.equals("");
	}
}
