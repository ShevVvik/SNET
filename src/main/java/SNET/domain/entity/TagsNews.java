package SNET.domain.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tagsnews")
public class TagsNews implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3669236798007801435L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@ManyToOne
    @JoinColumn(name="idNews", nullable=false)
	private News news;
	
	@ManyToOne
    @JoinColumn(name="idTags", nullable=false)
	private Tags tag;

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

	public Tags getTag() {
		return tag;
	}

	public void setTag(Tags tags) {
		this.tag = tags;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, news, tag);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof TagsNews))
        return false;

        TagsNews tagsNews = (TagsNews)obj;

        if (tagsNews.hashCode() == this.hashCode())
            return true;

        return false;
    }
}
