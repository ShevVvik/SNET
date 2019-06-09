package SNET.domain.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.NewsRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.News;
import SNET.domain.entity.Role;
import SNET.domain.entity.User;

@Service
public class NewsDomainServices {

	@Autowired
	public NewsRepository newsDao;
	
	@Autowired
	public UserDomainServices userService;
	
	@Autowired
	public FriendListDomainServices friendsService;
	
	public List<News> getList() {
		return newsDao.findAll();
	}
	
	
	public List<News> getNewsByAuthor(Long id, User userAut){
		
		List<News> news = null;
		
		if ((userAut.getHighLevelRole() == Role.ROLE_ADMIN) || 
				(friendsService.isFriends(userService.getById(id), userAut)) ||
				(id == userAut.getId())){
			news = newsDao.findByAuthorIdOrderByIdDesc(id);
		} else {
			news = newsDao.findByAuthorIdAndForFriendsFalseOrderByIdDesc(id);
		}
		
		return news;
	}
	
	public List<NewsDTO> searchNewsByPatternAsJson(String pattern, Long id, User userAut) {
		
		List<News> news = null;
		
		if ((userAut.getHighLevelRole() == Role.ROLE_ADMIN) || 
				(friendsService.isFriends(userService.getById(id), userAut)) ||
				(id == userAut.getId())){
			news = newsDao.findAllByTextContainingAndAuthorIdOrderByIdDesc(pattern, id);
		} else {
			news = newsDao.findAllByTextContainingAndAuthorIdAndForFriendsFalseOrderByIdDesc(pattern, id);
		}
		if (news == null) news = new ArrayList<News>();
		List<NewsDTO> newsJson = null;
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userAut, userDTO);
		
		if (news != null && news.size() > 0) {
			newsJson = new ArrayList<>();
			
			for (News u : news) {
				NewsDTO newsDTO = new NewsDTO();
				
				newsDTO.setId((long)u.getId());
				newsDTO.setText(u.getText());
				newsDTO.setAuthor(userDTO);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				newsDTO.setDate(dateFormat.format(u.getNewsDate()));
				newsDTO.setForFriends(u.isForFriends());
				
				
				
				List<CommentsDTO> comments = new ArrayList<>();
				
				for (Comments com : u.getCommentsList()) {
					CommentsDTO comment = new CommentsDTO();
					comment.setId(com.getId());
					comment.setText(com.getText());
					comments.add(comment);
				}
				
				newsDTO.setComments(comments);
				newsJson.add(newsDTO);
			}
		}
		
		return newsJson;
	}


	public void addNewNews(String text, Long id) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();        
		News news = new News();
		news.setAuthor(userService.getById(id));
		news.setText(text);
		news.setNewsDate(date);
		newsDao.save(news);
	}
	
	public void deleteNews(Long id) {
		newsDao.deleteById(id);
	}
}
