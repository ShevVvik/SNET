package SNET.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentsDTO {
	
	private Long id;
	private String text;
	private String date;
	private UserDTO commentator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public UserDTO getCommentator() {
		return commentator;
	}
	public void setCommentator(UserDTO commentator) {
		this.commentator = commentator;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
	