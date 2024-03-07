package com.example.farm.util;

import com.example.farm.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Getter
    private final Long id;
    @Getter
    private final String firstname;
    @Getter
    private final String lastname;
    @Getter
    private final String patronymic;
    @Getter
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,
                           String firstname,
                           String lastname,
                           String patronymic,
                           String email,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Employee employee) {
        String[] roles = {employee.getRole().name()};
        List<SimpleGrantedAuthority> authorities = stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UserDetailsImpl(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPatronymic(),
                employee.getEmail(),
                employee.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
