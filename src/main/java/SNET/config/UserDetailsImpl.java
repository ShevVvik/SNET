package SNET.config;

/*
 * Данный класс реализует интерфейс UserDetails
 * Provides core user information.
 * 
 * Implementations are not used directly by Spring Security for security purposes. 
 * They simply store user information which is later encapsulated into Authentication objects.
 * This allows non-security related user information (such as email addresses, telephone numbers etc) to be stored in a convenient location.
 * 
 * Concrete implementations must take particular care to ensure the non-null contract detailed for each method is enforced. 
 * See User for a reference implementation (which you might like to extend or use in your code).
 * 
 * -------------------------------------------------------------------------------------------------
 * Предоставляет основную информацию о пользователе.
 * 
 * Реализации не используются непосредственно Spring Security в целях безопасности.
 * Они просто хранят пользовательскую информацию, которая позже инкапсулируется в Authenticationобъекты.
 * Это позволяет хранить пользовательскую информацию, не связанную с безопасностью (например, адреса электронной почты, номера телефонов и т. Д.), В удобном месте.
 * 
 * Конкретные реализации должны быть особенно внимательны, чтобы обеспечить соблюдение ненулевого контракта, детализированного для каждого метода. См. User
 * Справочную реализацию (которую вы можете расширить или использовать в своем коде).
*/


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SNET.domain.entity.User;
import SNET.domain.entity.UserRole;


public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 2877136743159387065L;
    private User user;
    private List<GrantedAuthority> roles = new ArrayList<>();

    public UserDetailsImpl(User user) {
        this.user = user;
        if (user != null) {
            for (UserRole role : user.getUserRoles()) {
                GrantedAuthority auth = new SimpleGrantedAuthority(role.getRole());
                roles.add(auth);
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
    	if (user == null) return false;
        return user.isEnabled();
    }

    public User getUser() {
        return user;
    }

}