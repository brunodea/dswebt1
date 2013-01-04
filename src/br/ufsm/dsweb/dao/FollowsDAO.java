package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufsm.dsweb.db.DBCore;
import br.ufsm.dsweb.model.Follows;
import br.ufsm.dsweb.model.User;

public class FollowsDAO extends ModelDAO<Follows> {
	public FollowsDAO() {
		super(new Follows(), "follows.csv");
	}
	
	public void unfollow(User follower, User followed) {
		if(isFollowing(follower, followed)) {
			HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
			col_vals.put(0, follower.getID()+"");
			col_vals.put(1, followed.getID()+"");
	
			removeByCols(col_vals);
		}
	}
	
	public boolean isFollowing(User follower, User followed) {
		HashMap<Integer, String> col_vals = new HashMap<Integer, String>();
		col_vals.put(0, follower.getID()+"");
		col_vals.put(1, followed.getID()+"");

		String row = DBCore.getRowByCols(getFilename(), col_vals);
		
		return row != null && !row.equals("");
	}
	
	public ArrayList<User> getFollowers(User followed) {
		ArrayList<User> followers = new ArrayList<User>();
		
		for(String csv : DBCore.getAllRowsByCol(getFilename(), 1, followed.getID()+"")) {
			Follows follows = new Follows();
			follows.fromCSV(csv);
			
			followers.add(follows.getFollower());
		}
		
		return followers;
	}
	
	public ArrayList<User> getFollowing(User follower) {
		ArrayList<User> following = new ArrayList<User>();
		
		for(String csv : DBCore.getAllRowsByCol(getFilename(), 0, follower.getID()+"")) {
			Follows follows = new Follows();
			follows.fromCSV(csv);

			following.add(follows.getFollowed());
		}
		
		return following;		
	}
}
