package SNET.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.CommentsRepository;
import SNET.domain.entity.Comments;


@Service
public class CommentsDomainServices {

	@Autowired
	public CommentsRepository commentsDao;
	
	
	public List<Comments> getCommentsByNews(Long id){
		return commentsDao.findAll();
	}
	
	public Comments getCommentById(Long id) {
		return commentsDao.getOne(id);
	}
}
