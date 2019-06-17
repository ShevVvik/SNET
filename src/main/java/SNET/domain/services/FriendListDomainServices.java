package SNET.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.FriendListRepository;
import SNET.domain.dto.FriendDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.FriendList;
import SNET.domain.entity.User;

@Service
public class FriendListDomainServices {

	@Autowired
	public FriendListRepository friendListDao; 
	
	@Autowired
	private UserDomainServices userService;
	
	public List<FriendDTO> getRequestFriends(Long userId, User user) {
		List<FriendDTO> friends = new ArrayList<FriendDTO>();
		if (user.getId() == userId) {
			List<FriendList> friendList = friendListDao.findByUser2IdAndFriendshipFalse(userId);
			for (FriendList fr : friendList) {
				UserDTO userDTO = new UserDTO();
				FriendDTO friend = new FriendDTO();
				if (userId == fr.getUser1().getId()) {
					BeanUtils.copyProperties(fr.getUser2(), userDTO);
					friend.setFriend(userDTO);
					friend.setToken(fr.getToken());
					friends.add(friend);
				} else {
					BeanUtils.copyProperties(fr.getUser1(), userDTO);
					friend.setFriend(userDTO);
					friend.setToken(fr.getToken());
					friends.add(friend);
				}
			}
		}
		return friends;
	}
	
	public List<FriendDTO> getActiveFriends(Long userId) {
		List<FriendList> friendList = friendListDao.findByUser1IdAndFriendshipTrueOrUser2IdAndFriendshipTrue(userId, userId);
		List<FriendDTO> friends = new ArrayList<FriendDTO>();
		for (FriendList fr : friendList) {
			UserDTO userDTO = new UserDTO();
			FriendDTO friend = new FriendDTO();
			if (userId == fr.getUser1().getId()) {
				BeanUtils.copyProperties(fr.getUser2(), userDTO);
				friend.setFriend(userDTO);
				friend.setToken(fr.getToken());
				friends.add(friend);
			} else {
				BeanUtils.copyProperties(fr.getUser1(), userDTO);
				friend.setFriend(userDTO);
				friend.setToken(fr.getToken());
				friends.add(friend);
			}
		}
		return friends;
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
	
	public boolean isFriendsRequest(User user1, User user2) {
		FriendList friend1 = friendListDao.findByUser1IdAndUser2Id(user1.getId(), user2.getId());
		FriendList friend2 = friendListDao.findByUser1IdAndUser2Id(user2.getId(), user1.getId());
		if ((friend1 != null) || (friend2 != null)) {
			return true;
		}
		return false;
	}
	
	public boolean addFriend(User userFrom, long idUserTo) {
		if (!isFriendsRequest(userFrom, userService.getById(idUserTo))) {
			FriendList newFriend = new FriendList();
			newFriend.setUser1(userFrom);
			newFriend.setUser2(userService.getById(idUserTo));
			newFriend.setFriendship(false);
			newFriend.setToken(UUID.randomUUID().toString());
			friendListDao.save(newFriend);
			return true;
		}
		return false;
	}

	public void deleteFriend(Long idUser, User userAut) {
		FriendList friend1 = friendListDao.findByUser1IdAndUser2Id(idUser, userAut.getId());
		FriendList friend2 = friendListDao.findByUser1IdAndUser2Id(userAut.getId(), idUser);
		if (friend1 != null) {
			friendListDao.delete(friend1);
		} else if (friend2 != null) {
			friendListDao.delete(friend2);
		}
	}
}
