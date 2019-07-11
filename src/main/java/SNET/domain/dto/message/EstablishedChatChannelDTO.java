package SNET.domain.dto.message;

public class EstablishedChatChannelDTO {
	
	private String channelUuid;
	
	private String userOneFullName;
	  
	private String userTwoFullName;
	
	public EstablishedChatChannelDTO(String channelUuid, String userOneFullName, String userTwoFullName) {
		super();
		this.channelUuid = channelUuid;
		this.userOneFullName = userOneFullName;
		this.userTwoFullName = userTwoFullName;
	}

	public String getChannelUuid() {
		return channelUuid;
	}

	public void setChannelUuid(String channelUuid) {
		this.channelUuid = channelUuid;
	}

	public String getUserOneFullName() {
		return userOneFullName;
	}

	public void setUserOneFullName(String userOneFullName) {
		this.userOneFullName = userOneFullName;
	}

	public String getUserTwoFullName() {
		return userTwoFullName;
	}

	public void setUserTwoFullName(String userTwoFullName) {
		this.userTwoFullName = userTwoFullName;
	}
}
