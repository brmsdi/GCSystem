package system.gc.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import system.gc.entities.Lessee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public class LesseeUserDetails implements UserDetailsConvert {
    private final Lessee userDetail;

    public LesseeUserDetails(Lessee lessee) {
        this.userDetail = lessee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+ userDetail.getRole().getName()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userDetail.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetail.getCpf();
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
        return true;
    }

    @Override
    public UserAuthenticatedView getNameAndRoleUser() {
        return new UserAuthenticatedView(userDetail.getName(), userDetail.getRole().getName());
    }
}