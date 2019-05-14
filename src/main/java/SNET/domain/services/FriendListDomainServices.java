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
}
