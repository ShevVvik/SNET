package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
}
