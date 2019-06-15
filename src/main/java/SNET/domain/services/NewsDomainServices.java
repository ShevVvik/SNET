package SNET.domain.services;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import SNET.dao.CommentsRepository;
import SNET.dao.NewsRepository;
import SNET.dao.TagsRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.News;
import SNET.domain.entity.Role;
import SNET.domain.entity.Tags;
import SNET.domain.entity.User;
import SNET.web.form.CommentForm;
import SNET.web.form.NewNewsForm;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class NewsDomainServices {

	@Autowired
	public NewsRepository newsDao;
	
	@Autowired
	public CommentsRepository commentsDao;
	
	@Autowired
	public TagsRepository tagsDao;
	
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
				newsDTO.setImageToken(u.getImageToken());
				List<CommentsDTO> comments = new ArrayList<>();
				
				for (Comments com : u.getCommentsList()) {
					CommentsDTO comment = new CommentsDTO();
					comment.setId(com.getId());
					comment.setText(com.getText());
					UserDTO userCommentDTO = new UserDTO();
					BeanUtils.copyProperties(com.getCommentator(), userCommentDTO);
					comment.setCommentator(userCommentDTO);
					comment.setDate(dateFormat.format(com.getCommentDate()));
					comments.add(comment);
				}
				
				newsDTO.setComments(comments);
				newsJson.add(newsDTO);
			}
		}
		
		return newsJson;
	}


	public void addNewNews(NewNewsForm form) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
		News news = new News();
		news.setAuthor(userService.getById(form.getIdAuthor()));
		news.setText(form.getNewNewsText());
		news.setForFriends(form.isForFriends());
		news.setNewsDate(date);
		if (form.getFile() != null) {
			news.setImageToken(UUID.randomUUID().toString());
			saveImages(form.getFile(), news.getImageToken());
		}
		List<Tags> newsTags = new ArrayList<Tags>();
		for(String tagName : form.getTags()) {
			Tags tag = new Tags();
			
			
			/*if (null) {
				System.out.println("Check");
				newsTags.add(tagDao);
			} else {*/
				System.out.println("Check" + tagName);
				tag.setName(tagName);
				newsTags.add(tag);
			//}
			
		}
		news.setTags(new HashSet<Tags>(newsTags));
		newsDao.save(news);
	}
	
	public void saveImages(MultipartFile file, String id) {
	            String filePath = "C:\\Folder\\News" + File.separator + id + File.separator;
	    
	            if(! new File(filePath).exists()) {
	                new File(filePath).mkdirs();
	            }
	            
	            try {
	                FileUtils.cleanDirectory(new File(filePath));
	        
	                String orgName = file.getOriginalFilename();
	                String fullFilePath = filePath + id + ".png";
	        
	                File dest = new File(fullFilePath);
	                file.transferTo(dest);
	                
	            } catch (IllegalStateException e) {
	                System.out.println(e);
	                e.printStackTrace();
	            } catch (IOException e) {
	                System.out.println(e);
	                e.printStackTrace();
	            }
	}
	
	public void deleteNews(Long id) {
		try {
			newsDao.deleteById(id);
		} catch(EmptyResultDataAccessException err) {
			
		}
	}


	public void addComment(CommentForm form, User user) {
		Comments comment = new Comments();
		comment.setCommentator(user);
		comment.setNews(newsDao.getOne(form.getIdNews()));
		comment.setText(form.getText());
		Calendar cal = Calendar.getInstance();
        Date date=cal.getTime(); 
        comment.setCommentDate(date);
        commentsDao.save(comment);
	}
	
	public void updateComment(CommentForm form) {
		Comments comment = commentsDao.getOne(form.getIdComment());
		System.out.println(form.getIdComment());
		comment.setText(form.getText());
		Calendar cal = Calendar.getInstance();
        Date date=cal.getTime(); 
        comment.setCommentDate(date);
        commentsDao.save(comment);
	}
}
