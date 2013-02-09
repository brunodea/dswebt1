package br.ufsm.dsweb.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class UserDAO extends ModelDAO<User> {
	public UserDAO() {
		super(User.class, "User");
	}

	public boolean usernameExists(String username) {
		return getByUsername(username) != null;
	}
	
	public User getByUsername(String username) {
		return getFirstByCol("mUsername", username);
	}
	
	public User login(String username, String password) {
		User res = null;
		User aux = getByUsername(username);
		if(aux != null && aux.getPassword().equals(password)) {
			res = aux;
		}
		return res;
	}
	
	public List<Tweet> getAllTweets(User user) {
		List<Tweet> tweets = new TweetDAO().getAllByCol("user_id", user.getID());		
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});

		return tweets;
	}

	@SuppressWarnings("unchecked")
	public List<User> following(User follower) {
		List<User> users = getEntityManager().createQuery(
				"select u" +
				" from twitcheruser u" +
				" join user_follows uf" +
				"  on u.id = uf.follower_id" +
				" where u.id = :id")
				.setParameter("id", follower.getID())
				.getResultList();
		
		follower.setFollowing(users);
		return users;
	}
	@SuppressWarnings("unchecked")
	public List<User> followers(User followed) {
		List<User> users = getEntityManager().createQuery(
				"select u" +
				" from twitcheruser u" +
				" join user_follows uf" +
				"  on u.id = uf.followed_id" +
				" where u.id = :id")
				.setParameter("id", followed.getID())
				.getResultList();
		
		followed.setFollowers(users);
		return users;
	}
	public boolean isFollowing(User follower, User followed) {
		boolean following = false;
		try {
			int i = (Integer) getEntityManager().createQuery(
					"select count(uf)" +
					" from user_follows uf" +
					" where uf.follower_id = :follower_id" +
					"  and uf.followed_id = :followed_id")
					.setParameter("follower_id", follower.getID())
					.setParameter("followed_id", followed.getID())
					.getSingleResult();
			following = i > 0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		//follower.getFollowing().contains(followed)
		return following;
	}
	public void follow(User follower, User to_follow) {
		follower.getFollowing().add(to_follow);
		getEntityManager().merge(follower);
	}
	public void unfollow(User follower, User followed) {
		if(isFollowing(follower, followed)) {
			
		}
	}
}
