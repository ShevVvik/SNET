package SNET.domain.dto.message;

import SNET.domain.dto.UserDTO;

public class EstablishedChatChannelDTO {
	
	private String channelUuid;
	
	private UserDTO userOne;
	  
	private UserDTO userTwo;
	
	public EstablishedChatChannelDTO(String channelUuid, UserDTO userOne, UserDTO userTwo) {
		super();
		this.channelUuid = channelUuid;
		this.userOne = userOne;
		this.userTwo = userTwo;
	}

	public String getChannelUuid() {
		return channelUuid;
	}

	public void setChannelUuid(String channelUuid) {
		this.channelUuid = channelUuid;
	}

	public UserDTO getUserOne() {
		return userOne;
	}

	public void setUserOne(UserDTO userOne) {
		this.userOne = userOne;
	}

	public UserDTO getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(UserDTO userTwo) {
		this.userTwo = userTwo;
	}

	
}
