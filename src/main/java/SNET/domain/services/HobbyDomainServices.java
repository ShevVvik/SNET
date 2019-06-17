package SNET.domain.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.HobbyRepository;
import SNET.domain.entity.Hobby;

@Service
public class HobbyDomainServices {
	
	@Autowired
	private HobbyRepository hobbyDao;
	
	public List<Hobby> getAllHobby(){
		return hobbyDao.findAll();
	}
	
	public Set<Hobby> getAllHobbyByName(List<String> hobbies){
		return hobbyDao.findByNameHobbyIn(hobbies);
	}
	
	public Hobby getHobbyByName(String name){
		return hobbyDao.findByNameHobby(name);
	}
}
