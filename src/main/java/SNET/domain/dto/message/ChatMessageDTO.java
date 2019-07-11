package SNET.domain.dto.message;

public class ChatMessageDTO {

	private long fromUserId;
	  
	private long toUserId;

	private String contents;
	
	public ChatMessageDTO() {}
	
	public ChatMessageDTO(String contents2, Long id, Long id2) {
		this.fromUserId = id;
		this.toUserId = id2;
		this.contents = contents2;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	
}
