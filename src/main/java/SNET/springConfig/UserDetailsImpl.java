package SNET.springConfig;
/*
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SNET.domain.entity.LogIn;
import SNET.domain.entity.User;
import SNET.domain.entity.UserRole;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 2877136743159387065L;
    private LogIn user;
    private List<GrantedAuthority> roles = new ArrayList<>();

    public UserDetailsImpl(LogIn user) {
        this.user = user;

        for (UserRole role : user.getUserRoles()) {
            GrantedAuthority auth = new SimpleGrantedAuthority(role.getRole());
            roles.add(auth);
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
        return user.isEnabled();
    }

    public User getUser() {
        return user;
    }

}*/