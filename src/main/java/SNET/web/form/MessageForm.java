package SNET.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

public class MessageForm {
	
	private Long idTo;
	
	@NotNull
	@NotBlank
	private String subject;
	
	@NotNull
	@NotBlank
	private String text;
	
	public Long getIdTo() {
		return idTo;
	}
	public void setIdTo(Long idTo) {
		this.idTo = idTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
