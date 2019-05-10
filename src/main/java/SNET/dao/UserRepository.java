package SNET.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import SNET.domain.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndEnabledTrue(String email);
    User findByEmail(String email);
    User findByLogin(String login);
}