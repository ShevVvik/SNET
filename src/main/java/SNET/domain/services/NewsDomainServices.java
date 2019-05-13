package SNET.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.NewsRepository;
import SNET.domain.entity.News;
import SNET.domain.entity.User;

@Service
public class NewsDomainServices {

	@Autowired
	public NewsRepository newsDao;
	
	public List<News> getList() {
		return newsDao.findAll();
	}
	
	
	public List<News> getNewsByAuthor(Long id){
		return newsDao.findByAuthorId(id);
	}
}
