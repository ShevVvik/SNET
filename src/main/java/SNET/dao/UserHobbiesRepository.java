package SNET.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.Hobby;
import SNET.domain.entity.UserHobby;

public interface UserHobbiesRepository extends JpaRepository<UserHobby, Long>{
	List<UserHobby> findByHobby(Hobby hobby);
}
