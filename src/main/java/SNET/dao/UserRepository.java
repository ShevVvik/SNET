package SNET.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import springweb.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndEnabledTrue(String email);
    User findByEmail(String email);
    
    int countByEmail(String email);
}