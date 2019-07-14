package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SNET.domain.entity.User;
import SNET.domain.entity.message.ChatChannel;

public interface ChatChannelRepository extends JpaRepository<ChatChannel, Long> {
	
	@Query(" SELECT uuid"
		      + "  FROM ChatChannel c"
		      + "  WHERE c.userOne.id IN (:userIdOne, :userIdTwo)"
		      + "  AND c.userTwo.id IN (:userIdOne, :userIdTwo)")
	public String getChannelUuid(@Param("userIdOne") long userIdOne, @Param("userIdTwo") long userIdTwo);
	
	public ChatChannel findByUuid(String uuid);
	
	
	public List<ChatChannel> findByUserOneAndUserTwo(User userIdOne, User userIdTwo);
	public List<ChatChannel> findByUserOneOrUserTwo(User userIdOne, User userIdTwo);
}
