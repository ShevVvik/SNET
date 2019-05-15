package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.FriendList;
import SNET.domain.entity.News;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
	
	List<FriendList> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
	FriendList findByToken(String token);
}
