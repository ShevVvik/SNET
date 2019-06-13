package SNET.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FriendDTO {

	private UserDTO friend;
	private String token;
	public UserDTO getFriend() {
		return friend;
	}
	public void setFriend(UserDTO friend) {
		this.friend = friend;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
