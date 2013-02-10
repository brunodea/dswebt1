package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufsm.dsweb.dao.UserDAO;
import br.ufsm.dsweb.model.User;


@ManagedBean(name="followsController")
@ViewScoped
public class FollowsController implements Serializable {
	public void startFollowing(User follower, User followed) {
		if(!isFollowing(follower, followed)) {
			new UserDAO().follow(follower, followed);
		}
	}
	public void unfollow(User follower, User followed) {
		new UserDAO().unfollow(follower, followed);
	}
	
	public void toggleFollow(User follower, User followed) {
		if(isFollowing(follower, followed)) {
			unfollow(follower, followed);
		} else {
			startFollowing(follower, followed);
		}
	}

	public boolean isFollowing(User follower, User followed) {
		return new UserDAO().isFollowing(follower, followed);
	}
	public List<User> getFollowers(User followed) {
		return new UserDAO().followers(followed);
	}
	public List<User> getFollowing(User follower) {
		return new UserDAO().following(follower);
	}
}
