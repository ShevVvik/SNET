package SNET.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class NewNewsForm {
	
	private String newNewsText;
	private Long idAuthor;
	private boolean forFriends;
	private MultipartFile file;
	private List<String> tags;
	
	public String getNewNewsText() {
		return newNewsText;
	}
	public void setNewNewsText(String newNewsText) {
		this.newNewsText = newNewsText;
	}
	public Long getIdAuthor() {
		return idAuthor;
	}
	public void setIdAuthor(Long idAuthor) {
		this.idAuthor = idAuthor;
	}
	public boolean isForFriends() {
		return forFriends;
	}
	public void setForFriends(boolean forFriends) {
		this.forFriends = forFriends;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
