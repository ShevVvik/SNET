package SNET.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SNET.dao.UserRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);

        return new UserDetailsImpl(user);
    }

}
