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

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import SNET.dao.CommentsRepository;
import SNET.dao.NewsRepository;
import SNET.dao.TagsNewsRepository;
import SNET.dao.TagsRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.FriendDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.News;
import SNET.domain.entity.Role;
import SNET.domain.entity.Tags;
import SNET.domain.entity.TagsNews;
import SNET.domain.entity.User;
import SNET.web.form.CommentForm;
import SNET.web.form.NewNewsForm;

@Service
public class NewsDomainServices {

	//@Value("${project.manager.news.dir.path}")
	@Value("C:\\Folder\\News")
    private String newsDirPath;
	
	@Autowired
	public NewsRepository newsDao;
	
	@Autowired
	public CommentsRepository commentsDao;
	
	@Autowired
	public TagsRepository tagsDao;
	
	@Autowired
	public TagsNewsRepository tagsNewsDao;
	
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
		List<News> news = new ArrayList<News>();
		boolean all = true;
		if ((userAut.getHighLevelRole() == Role.ROLE_ADMIN) || 
				(friendsService.isFriends(userService.getById(id), userAut)) ||
				(id == userAut.getId())){
			all = true;
		} else {
			all = false;
		}
		char tagMarker = ' ';
		if (pattern.length() != 0) tagMarker = pattern.charAt(0);
		if (tagMarker == '#') {
			Tags tag = tagsDao.findByName(pattern.substring(1));
			List<TagsNews> tagsNews = tagsNewsDao.findByTag(tag);
			for (TagsNews tagNews : tagsNews) {
				if(all) {
					news.add(tagNews.getNews());
				} else {
					if (!tagNews.getNews().isForFriends()) {
						news.add(tagNews.getNews());
					}
				}
			}
		} else {
			if (all){
				news = newsDao.findAllByTextContainingAndAuthorIdOrderByIdDesc(pattern, id);
			} else {
				news = newsDao.findAllByTextContainingAndAuthorIdAndForFriendsFalseOrderByIdDesc(pattern, id);
			}
		}
		return transformToDTO(news, id);
	}

	private List<NewsDTO> transformToDTO(List<News> news, Long id) {
		List<NewsDTO> newsJson = null;
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userService.getById(id), userDTO);
		
		if (news != null && news.size() > 0) {
			newsJson = new ArrayList<>();
			
			for (News u : news) {
				NewsDTO newsDTO = new NewsDTO();
				
				newsDTO.setId((long)u.getId());
				newsDTO.setText(u.getText());
				newsDTO.setAuthor(userDTO);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
				newsDTO.setDate(dateFormat.format(u.getNewsDate()));
				newsDTO.setForFriends(u.isForFriends());
				newsDTO.setImageToken(u.getImageToken());
				List<String> tags = new ArrayList<String>();
				for (Tags tagName : u.getTagsList()) {
					tags.add(tagName.getName());
				}
				newsDTO.setTags(tags);
				List<CommentsDTO> comments = new ArrayList<CommentsDTO>();
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
	
	public void addNewNews(NewNewsForm form, User user) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
		News news = new News();
		news.setAuthor(user);
		news.setText(form.getNewNewsText());
		news.setForFriends(form.isForFriends());
		news.setNewsDate(date);
		if (form.getFile() != null) {
			news.setImageToken(UUID.randomUUID().toString());
			saveImages(form.getFile(), news.getImageToken());
		}
		List<Tags> newsTags = new ArrayList<Tags>();
		for(String tagName : form.getTags()) {
			Tags tag = tagsDao.findByName(tagName);
			if (tag == null) {
				Tags newTag = new Tags();
				newTag.setName(tagName);
				newsTags.add(newTag);
			} else {
				newsTags.add(tag);
			}
		}
		news.setTags(new HashSet<Tags>(newsTags));
		newsDao.save(news);
	}
	
	public void saveImages(MultipartFile file, String token) {
		String filePath = newsDirPath + File.separator + token + File.separator;
		if(! new File(filePath).exists()) {
			new File(filePath).mkdirs();
		}     
		try {
			FileUtils.cleanDirectory(new File(filePath));
			String fullFilePath = filePath + token + ".png";
			File dest = new File(fullFilePath);
	        file.transferTo(dest);      
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean deleteNews(Long id, User user) {
		News newNews = newsDao.getOne(id);
		if ((user.getHighLevelRole().equals(Role.ROLE_ADMIN)) || newNews.getAuthor().equals(user)) {
			try {
				newsDao.deleteById(id);
				return true;
			} catch(EmptyResultDataAccessException err) {
				return false;
			}
		}
		return false;
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
	
	public boolean updateComment(CommentForm form, User user) {
		Comments comment = commentsDao.getOne(form.getIdComment());
		if (comment.getCommentator().equals(user)) {
			comment.setText(form.getText());
			Calendar cal = Calendar.getInstance();
        	Date date=cal.getTime(); 
        	comment.setCommentDate(date);
        	commentsDao.save(comment);
        	return true;
		}
		return false;
	}


	public boolean updateNewNews(NewNewsForm form, User user) {
		News newNews = newsDao.getOne(form.getIdNews());
		if (newNews.getAuthor().equals(user)) {
			newNews.setText(form.getNewNewsText());
			if (form.getFile() != null) {
				newNews.setImageToken(UUID.randomUUID().toString());
				saveImages(form.getFile(), newNews.getImageToken());
			}
			newsDao.save(newNews);
			return true;
		}
		return false;
	}

	public List<News> getAllFriendsNews(User user) {
		List<News> news = new ArrayList<News>();
		List<Long> friendsIdArray = new ArrayList<Long>();
		for(FriendDTO friends : friendsService.getActiveFriends(user.getId())) {
			friendsIdArray.add(friends.getFriend().getId());
		}
		
		Set<News> newsSet = newsDao.findByAuthorIdIn(friendsIdArray);
		
		if (newsSet != null) { 
			for(News newsElem : newsSet) {
				news.add(newsElem);
			}
		}
		
		return news;
	}
}
