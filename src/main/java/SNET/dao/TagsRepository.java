package SNET.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.News;
import SNET.domain.entity.Tags;

public interface TagsRepository extends JpaRepository<News, Long>{

	Set<Tags> findByTagsNameIn(List<String> tagsName);

	Tags findByTagsName(String tagsName);
	
}
