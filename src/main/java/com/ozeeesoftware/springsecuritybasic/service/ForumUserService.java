package com.ozeeesoftware.springsecuritybasic.service;

import com.ozeeesoftware.springsecuritybasic.common.UserConstant;
import com.ozeeesoftware.springsecuritybasic.model.User;
import com.ozeeesoftware.springsecuritybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createUser(User user){
        user.setUserRoles(UserConstant.DEFAULT_ROLE);
        String encryptedPassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return "Welcome to our forum"+user.getUserName();
    }

    private List<String> getLoggedUsersRoles(Principal principal){
        String roles=getLoggedUser(principal).getUserRoles();
        List<String> assignRoles= Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if(assignRoles.contains("ROLE_ADMIN")){
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }
        if(assignRoles.contains("ROLE_MODERATOR")){
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private User getLoggedUser(Principal principal){
        return userRepository.findByUserName(principal.getName()).get();
    }


    public String giveAccessToUser(long userId, String userRole, Principal principal){

        User user=userRepository.findById(userId).get();
        List<String> activeRoles=getLoggedUsersRoles(principal);
        String newRole="";

        if(activeRoles.contains(userRole)){
            newRole=user.getUserRoles()+","+userRole;
            user.setUserRoles(newRole);
        }
        userRepository.save(user);
        return principal.getName()+"Assigned new role to you"+user.getUserName();

    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public String testAdmin(){
        return "Only admins can see this";
    }

    public String testModerator(){
        return "Only moderators can see this";
    }

    public String testUser(){
        return "Only users can see this";
    }
}
