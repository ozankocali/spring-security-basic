package com.ozeeesoftware.springsecuritybasic.controller;

import com.ozeeesoftware.springsecuritybasic.model.User;
import com.ozeeesoftware.springsecuritybasic.service.ForumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ForumUserService forumUserService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user){
        return forumUserService.createUser(user);
    }

    @GetMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String giveAccessToUser(@PathVariable long userId,@PathVariable String userRole, Principal principal){
        return forumUserService.giveAccessToUser(userId,userRole,principal);
    }

    @GetMapping("/listAll")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> listAllUsers(){
        return forumUserService.listAllUsers();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String testAdmin(){
        return forumUserService.testAdmin();
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String testModerator(){
        return forumUserService.testModerator();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUser(){
        return forumUserService.testUser();
    }
}
