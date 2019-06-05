package SNET.domain.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewsDTO {
	
	private Long id;
	private String text;
	private List<CommentsDTO> comments;
	
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
}
