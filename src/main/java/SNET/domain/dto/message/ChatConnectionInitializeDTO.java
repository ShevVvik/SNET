package SNET.domain.dto.message;

public class ChatConnectionInitializeDTO {
	
	private long userIdOne;

	private long userIdTwo;

	public long getUserIdOne() {
		return userIdOne;
	}

	public void setUserIdOne(long userIdOne) {
		this.userIdOne = userIdOne;
	}

	public long getUserIdTwo() {
		return userIdTwo;
	}

	public void setUserIdTwo(long userIdTwo) {
		this.userIdTwo = userIdTwo;
	}
	
}
