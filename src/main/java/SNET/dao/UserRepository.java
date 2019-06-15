package SNET.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

	
    User findByEmailAndEnabledTrue(String email);
    User findByEmail(String email);
    User findByLogin(String login);
	User findByToken(String code);
	
	int countByEmail(String email);
	int countByLogin(String login);
	List<User> findAllByFirstNameContainingOrderByIdDesc(String pattern);
	List<User> findAllByLastNameContainingOrderByIdDesc(String pattern);
	List<User> findAllByCityContainingOrderByIdDesc(String pattern);
	List<User> findAllByEducationContainingOrderByIdDesc(String pattern);
	List<User> findAllByEmailContainingOrderByIdDesc(String pattern);
}