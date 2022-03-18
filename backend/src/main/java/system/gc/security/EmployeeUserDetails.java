package system.gc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import system.gc.entities.Employee;
import system.gc.services.ServiceImpl.EmployeeService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeUserDetails implements UserDetailsConvert {
    @Autowired
    EmployeeService employeeService;
    private final Employee userDetail;

    public EmployeeUserDetails(Employee employeeDTOUser) {
        this.userDetail = employeeDTOUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userDetail.getRole().getName()));
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
    public UserAuthenticatedView getUser() {
        return new UserAuthenticatedView(userDetail.getName(), userDetail.getRole().getName());
    }
}
