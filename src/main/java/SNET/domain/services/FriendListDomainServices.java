package SNET.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.FriendListRepository;
import SNET.domain.entity.FriendList;
import SNET.domain.entity.User;

@Service
public class FriendListDomainServices {

	@Autowired
	public FriendListRepository friendListDao;
	
	@Autowired
	private UserDomainServices userService;
	
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
	
	public boolean isFriends(User user1, User user2) {
		FriendList friend1 = friendListDao.findByUser1IdAndUser2IdAndFriendshipTrue(user1.getId(), user2.getId());
		FriendList friend2 = friendListDao.findByUser1IdAndUser2IdAndFriendshipTrue(user2.getId(), user1.getId());
		if ((friend1 != null) || (friend2 != null)) {
			return true;
		}
		return false;
	}
	
	public void addFriend(User userFrom, long idUserTo) {
		FriendList newFriend = new FriendList();
		newFriend.setUser1(userFrom);
		newFriend.setUser2(userService.getById(idUserTo));
		newFriend.setFriendship(false);
		newFriend.setToken(UUID.randomUUID().toString());
		friendListDao.save(newFriend);
	}
}
