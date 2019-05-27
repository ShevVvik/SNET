package SNET.config;

import java.util.Collections;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SNET.dao.UserRepository;
import SNET.domain.entity.Role;
import SNET.domain.entity.User;

/*Core interface which loads user-specific data.
 * 
 * It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.
 * The interface requires only one read-only method, which simplifies support for new data-access strategies.
 * ----------------------------------------------------------------------------------------------------------
 * 
 * 
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);

        return new UserDetailsImpl(user);
    }

   /* public boolean addUser(User user) {
    	User userFromDb = userRepository.findByUsername(user.getLogin());
    	
    	if (userFromDb != null)
    	{
    		return false;
    		user.setActive(true);
    		user.setToken(user.hashCode());
    		
    		userRepository.save(user);
    	}
    if (!StringUtils.isEmpty(user.getEmail())) {
        String message = String.format(
                "Hello, %s! \n" +
                		"Welcome to SNET. Please, visit next link and activated you profile: http://localhost:8080/activate/%s",
                user.getFirstName(),
                user.getToken()
        );

        mailSender.send(user.getEmail(), "Activation code", message);
    }

    return true;
} */
}
