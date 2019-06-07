package SNET.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;



@Entity
@Table(name="comments")
public class Comments implements Serializable {
	private static final long serialVersionUID = 8316344084865363418L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="idComment")
		private Long id;
		
		@ManyToOne
	    @JoinColumn(name="idCommentator", nullable=false)
	 	private User commentator;
		
		@ManyToOne
	    @JoinColumn(name="idNews", nullable=false)
	 	private News news;
		
		@Column(name="commentsText", length=255, nullable=false)
		private String text;
		
		public User getCommentator() {
			return commentator;
		}

		public void setCommentator(User commentator) {
			this.commentator = commentator;
		}

		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="commentsDate", nullable=false)
		@LastModifiedDate
		private Date commentDate;
		
		public String getText() {
			return text;
		}

		public Comments getComments() {
			return this;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		 public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public News getNews() {
			return news;
		}

		public void setNews(News news) {
			this.news = news;
		}

		public Date getCommentDate() {
			return commentDate;
		}

		public void setCommentDate(Date commentDate) {
			this.commentDate = commentDate;
		}

		@Override
		    public int hashCode() {
		        return Objects.hash(id, commentator, text, news);
		 }
		 @Override
		    public boolean equals(Object obj) {
		        if (obj == null)
		            return false;

		        if (obj == this)
		            return true;

		        if (!(obj instanceof News))
		        return false;

		        Comments comments = (Comments)obj;

		        if (comments.hashCode() == this.hashCode())
		            return true;

		        return false;
		    }
}

