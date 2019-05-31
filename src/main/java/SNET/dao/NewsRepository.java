package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.News;
import SNET.domain.entity.User;

public interface NewsRepository extends JpaRepository<News, Long> {

	List<News> findByAuthorId(Long id);
	List<News> findAllByTextContainingAndAuthorId(String text, Long id);
}
