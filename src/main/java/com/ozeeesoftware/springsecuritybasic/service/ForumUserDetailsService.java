package com.ozeeesoftware.springsecuritybasic.service;

import com.ozeeesoftware.springsecuritybasic.model.User;
import com.ozeeesoftware.springsecuritybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForumUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);

        return user.map(ForumUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found by username: "+userName ));
    }
}
