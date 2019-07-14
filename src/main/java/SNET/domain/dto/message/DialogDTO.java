package SNET.domain.dto.message;

import SNET.domain.dto.UserDTO;

public class DialogDTO {
	
	private UserDTO userOne;
	
	private UserDTO authorLastMessage;
	
	private String contents;

	public DialogDTO() {}
	
	public DialogDTO(UserDTO userOne, UserDTO authorLastMessage, String contents) {
		super();
		this.userOne = userOne;
		this.authorLastMessage = authorLastMessage;
		this.contents = contents;
	}

	public UserDTO getUserOne() {
		return userOne;
	}

	public void setUserOne(UserDTO userOne) {
		this.userOne = userOne;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public UserDTO getAuthorLastMessage() {
		return authorLastMessage;
	}

	public void setAuthorLastMessage(UserDTO authorLastMessage) {
		this.authorLastMessage = authorLastMessage;
	}
	
}
