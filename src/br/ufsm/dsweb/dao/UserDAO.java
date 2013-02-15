package br.ufsm.dsweb.dao;

import java.util.ArrayList;
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
		List<Tweet> tweets = tweets(user);
		Collections.sort(tweets, new Comparator<Tweet>() {
			@Override
			public int compare(Tweet lhs, Tweet rhs) {
				return rhs.getPubdate().compareTo(lhs.getPubdate());
			}
		});

		return tweets;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<User> following(User follower) {
		List<User> users = new ArrayList<User>();
		try {
			users = getEntityManager().createQuery(
				"select u" +
				" from User u" +
				" join fetch u.mFollowers uf2" +
				" where uf2.mId = :mId")
				.setParameter("mId", follower.getID())
				.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return users;
	}
	@SuppressWarnings("unchecked")
	public synchronized List<User> followers(User followed) {
		List<User> users = new ArrayList<User>();
		try {
			users = getEntityManager().createQuery(
				"select u" +
				" from User u" +
				" join fetch u.mFollowing uf2" +
				" where uf2.mId = :mId")
				.setParameter("mId", followed.getID())
				.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return users;
	}
	public boolean isFollowing(User follower, User followed) {
		return following(follower).contains(followed);
	}
	public synchronized void follow(User follower, User to_follow) {
		if(!isFollowing(follower, to_follow)) {
			follower.setFollowing(following(follower));
			follower.getFollowing().add(to_follow);
			update(follower);
		}
	}
	public synchronized void unfollow(User follower, User followed) {
		if(isFollowing(follower, followed)) {
			follower.setFollowing(following(follower));
			follower.getFollowing().remove(followed);			
			update(follower);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Tweet> tweets(User user) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			tweets = getEntityManager().createQuery(
				"select u.mTweets" +
				" from User u" +
				" where u.mId = :mId")
				.setParameter("mId", user.getID())
				.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return tweets;		
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<Tweet> retweets(User user) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			tweets = getEntityManager().createQuery(
				"select u.mRetweets" +
				" from User u" +
				" where u.mId = :mId")
				.setParameter("mId", user.getID())
				.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return tweets;
	}
	public List<User> retweeters(Tweet tweet) {
		return new TweetDAO().getRetweeters(tweet);
	}

	@SuppressWarnings("unchecked")
	public synchronized List<User> usersLike(String value) {
		List<User> users = new ArrayList<User>();
		try {
			users = getEntityManager().createQuery(
						"select u" +
						" from User u" +
						" where u.mUsername like '%"+value+"%'" +
						" or u.mFullName like '%"+value+"%'")
						.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return users;
	}
	
	public synchronized void retweet(User user, Tweet tweet) {
		user.getRetweets().add(tweet);
		update(user);
	}
	public synchronized void cancelRetweet(User user, Tweet tweet) {
		user.setRetweets(retweets(user));
		user.getRetweets().remove(tweet);
		update(user);
/*		try {
			getEntityManager().createQuery(
					"delete from User u" +
					" join fetch u.Retweets r" +
					" where r.mId = :mId")
					.setParameter("mId", tweet.getID())
					.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}*/
	}
}
