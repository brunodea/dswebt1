package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufsm.dsweb.model.Follows;
import br.ufsm.dsweb.model.User;

public class FollowsDAO extends ModelDAO<Follows> {
	public FollowsDAO() {
		super(Follows.class, "follows");
	}
	
	public void unfollow(User follower, User followed) {
		if(isFollowing(follower, followed)) {
			HashMap<String, String> col_vals = new HashMap<String, String>();
			col_vals.put("follower_id", follower.getID()+"");
			col_vals.put("followed_id", followed.getID()+"");
	
			removeByCols(col_vals);
		}
	}
	
	public boolean isFollowing(User follower, User followed) {
		HashMap<String, String> col_vals = new HashMap<String, String>();
		col_vals.put("follower_id", follower.getID()+"");
		col_vals.put("followed_id", followed.getID()+"");

		return getFirstByCols(col_vals) != null;
	}
	
	public ArrayList<User> getFollowers(User followed) {
		ArrayList<User> followers = new ArrayList<User>();
		UserDAO udao = new UserDAO();
		for(Follows follows : getAllByCol("followed_id", followed.getID())) {
			followers.add(udao.getByID(follows.getID()));
		}
		return followers;
	}
	
	public ArrayList<User> getFollowing(User follower) {
		ArrayList<User> following = new ArrayList<User>();
		UserDAO udao = new UserDAO();
		for(Follows follows : getAllByCol("follower_id", follower.getID())) {
			following.add(udao.getByID(follows.getID()));
		}		
		return following;		
	}
}
