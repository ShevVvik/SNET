package SNET.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.FriendListRepository;
import SNET.domain.entity.FriendList;

@Service
public class FriendListDomainServices {

	@Autowired
	public FriendListRepository friendListDao;
	
	
	public List<FriendList> getFriends(Long userId) {
		return friendListDao.findByUser1IdOrUser2Id(userId, userId);
	}
	
	public List<FriendList> getActiveFriends(Long userId) {
		return friendListDao.findByUser1IdOrUser2IdAndFriendshipTrue(userId, userId);
	}
	
	public FriendList getFriendsByToken(String token) {
		return friendListDao.findByToken(token);
	}
	
	public void createFriendship(FriendList friendship) {
		friendship.setFriendship(true);
		friendListDao.save(friendship);
	}
	
	
	public void addFriend(FriendList friendship) {
		friendListDao.save(friendship);
	}
}
