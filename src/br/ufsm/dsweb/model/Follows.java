package br.ufsm.dsweb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="follows")
public class Follows extends Model implements Serializable {

	@ManyToOne
	@JoinColumn(name="follower_id")
	private User mFollower;
	@ManyToOne
	@JoinColumn(name="followed_id")
	private User mFollowed;

	public User getFollower() {
		return mFollower;
	}

	public void setFollower(User following) {
		this.mFollower = following;
	}

	public User getFollowed() {
		return mFollowed;
	}

	public void setFollowed(User followed) {
		this.mFollowed = followed;
	}
}
