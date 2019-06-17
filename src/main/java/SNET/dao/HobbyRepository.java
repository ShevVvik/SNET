package SNET.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.Hobby;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

	Set<Hobby> findByNameHobbyIn(List<String> hobbies);
	Hobby findByNameHobby(String Hobby);
}
