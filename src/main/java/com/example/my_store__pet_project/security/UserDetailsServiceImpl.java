package com.example.my_store__pet_project.security;

import com.example.my_store__pet_project.enums.UserStatus;
import com.example.my_store__pet_project.model.Users;
import com.example.my_store__pet_project.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    private boolean isBlocked;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException, SecurityException {
        Users users = usersRepository.findUsersByName(userName).orElseThrow(EntityNotFoundException::new);

        if (users.getStatus() == UserStatus.BLOCKED) {
            isBlocked = true;
            throw new SecurityException();
        }

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(users.getRole().name()));

        return new User(users.getName(), users.getPassword(), roles);
    }

    public boolean isBlocked() {
        boolean value = this.isBlocked;
        if (value) {
            setIsBlocked();
        }
        return value;
    }

    private void setIsBlocked() {
        this.isBlocked = false;
    }
}
