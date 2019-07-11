package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.Tags;
import SNET.domain.entity.TagsNews;

public interface TagsNewsRepository extends JpaRepository<TagsNews, Long>{
	List<TagsNews> findByTag(Tags Id);
}
