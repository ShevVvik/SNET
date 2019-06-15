package SNET.domain.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewsDTO {
	
	private Long id;
	private String text;
	private String date;
	private boolean forFriends;
	private String imageToken;
	private List<CommentsDTO> comments;
	private UserDTO author;
	private List<String> tags;
	
	public List<CommentsDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentsDTO> comments) {
		this.comments = comments;
	}
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
	public UserDTO getAuthor() {
		return author;
	}
	public void setAuthor(UserDTO author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isForFriends() {
		return forFriends;
	}
	public void setForFriends(boolean forFriends) {
		this.forFriends = forFriends;
	}
	public String getImageToken() {
		return imageToken;
	}
	public void setImageToken(String imageToken) {
		this.imageToken = imageToken;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
