package SNET.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentForm {

	private Long idComment;
	
	@NotNull
	private Long idNews;
	
	@NotNull
	@NotBlank
	private String text;
	
	public Long getIdNews() {
		return idNews;
	}
	public void setIdNews(Long idNews) {
		this.idNews = idNews;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getIdComment() {
		return idComment;
	}
	public void setIdComment(Long idComment) {
		this.idComment = idComment;
	}
	
		
}
