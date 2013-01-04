package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufsm.dsweb.dao.FollowsDAO;
import br.ufsm.dsweb.model.Follows;
import br.ufsm.dsweb.model.User;


@ManagedBean(name="followsController")
@ViewScoped
public class FollowsController implements Serializable {
	public void startFollowing(User follower, User followed) {
		if(!isFollowing(follower, followed)) {
			Follows follows = new Follows();
			follows.setFollower(follower);
			follows.setFollowed(followed);
			
			new FollowsDAO().save(follows);
		}
	}
	public void unfollow(User follower, User followed) {
		new FollowsDAO().unfollow(follower, followed);
	}

	public boolean isFollowing(User follower, User followed) {
		return new FollowsDAO().isFollowing(follower, followed);
	}
	public List<User> getFollowers(User followed) {
		return new FollowsDAO().getFollowers(followed);
	}
	public List<User> getFollowing(User follower) {
		return new FollowsDAO().getFollowing(follower);
	}
}
