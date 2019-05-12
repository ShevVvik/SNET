package SNET.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="comments")
public class Comments implements Serializable {
	private static final long serialVersionUID = 8316344084865363418L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id_comment")
		private int id;
		
		@ManyToOne
	    @JoinColumn(name="id_commentator", nullable=false)
	 	private User commentator;
		
		public User getCommentator() {
			return commentator;
		}

		public void setCommentator(User commentator) {
			this.commentator = commentator;
		}

		@Column(name="comment_text", length=255, nullable=false)
		private String text;
		
		//ИСПРАВИТЬ КАК БУДЕТ ВОЗМОЖНОСТЬ
		//@Column(name="comment_date", nullable=false)
		// private DATETAMETYPE_CHECK_ME comment_date;
		
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@ManyToOne
	    @JoinColumn(name="id_news", nullable=false)
	 	private News id_news;
}

