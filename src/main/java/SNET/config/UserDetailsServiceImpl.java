package SNET.config;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import SNET.dao.UserRepository;
import SNET.domain.entity.Role;
import SNET.domain.entity.User;
import SNET.domain.services.MailSender;

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

    public boolean addUser(User user) {
    	User userFromDb = userRepository.findByLogin(user.getLogin());
    	
    	if (userFromDb != null)
    	{
    		return false;
    		
    	}
    	
    	user.setEnabled(true);
    	 user.setToken(UUID.randomUUID().toString());

		
		userRepository.save(user);
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
}

	public  boolean activateUser(String code) {
		 User user = userRepository.findByToken(code);

	        if (user == null) {
	            return false;
	        }

	        user.setToken(null);

	        userRepository.save(user);

	        return true;
	} 
}
