package SNET.domain.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import SNET.domain.entity.User;

@Entity
@Table(name="chat_message")
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;

	@OneToOne
	@JoinColumn(name = "authorUserId")
	private User authorUser;

	@OneToOne
	@JoinColumn(name = "recipientUserId")
	private User recipientUser;

	@NotNull
	private Date timeSent;

	@NotNull
	private String contents;
  
	public ChatMessage() {}
	
	public ChatMessage(User authorUser, User recipientUser, @NotNull String contents) {
		super();
		this.authorUser = authorUser;
		this.recipientUser = recipientUser;
		this.contents = contents;
		this.timeSent = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAuthorUser() {
		return authorUser;
	}

	public void setAuthorUser(User authorUser) {
		this.authorUser = authorUser;
	}

	public User getRecipientUser() {
		return recipientUser;
	}

	public void setRecipientUser(User recipientUser) {
		this.recipientUser = recipientUser;
	}

	public Date getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
