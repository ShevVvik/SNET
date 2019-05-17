package SNET.domain.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="friend_list")
public class FriendList {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
    @JoinColumn(name="user1Id", nullable=false)
 	private User user1;
	
	@ManyToOne
    @JoinColumn(name="user2Id", nullable=false)
 	private User user2;

	@Column(name="friendship")
	private boolean friendship;
	
	@Column(name="token")
	private String token;
	
	
	public boolean isFriendship() {
		return friendship;
	}

	public void setFriendship(boolean friendship) {
		this.friendship = friendship;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	 @Override
	 public int hashCode() {
		 return Objects.hash(id, user1, user2);
	 }

	 @Override
	 public boolean equals(Object obj) {
	     if (obj == null)
	         return false;
	     if (obj == this)
	         return true;

	     if (!(obj instanceof FriendList))
	        return false;

	     FriendList friends = (FriendList)obj;

	     if (friends.hashCode() == this.hashCode())
	        return true;

	        return false;
	    }
}
