package SNET.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	List<News> findByAuthorIdOrderByIdDesc(Long id);
	List<News> findByAuthorIdAndForFriendsFalseOrderByIdDesc(Long id);
	List<News> findAllByTextContainingAndAuthorIdOrderByIdDesc(String text, Long id);
	List<News> findAllByTextContainingAndAuthorIdAndForFriendsTrueOrderByIdDesc(String pattern, Long id);
	List<News> findAllByTextContainingAndAuthorIdAndForFriendsFalseOrderByIdDesc(String pattern, Long id);
	
	Set<News> findByAuthorIdIn(List<Long> id);
}
