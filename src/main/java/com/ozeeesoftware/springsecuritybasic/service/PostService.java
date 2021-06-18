package com.ozeeesoftware.springsecuritybasic.service;

import com.ozeeesoftware.springsecuritybasic.common.PostStatus;
import com.ozeeesoftware.springsecuritybasic.model.Post;
import com.ozeeesoftware.springsecuritybasic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public String createPost(Post post, Principal principal){
        post.setPostStatus(PostStatus.PENDING);
        post.setPostUserName(principal.getName());
        postRepository.save(post);

        return principal.getName()+"your post published successfully!";
    }

    public String approvePost(long postId){

        Post existingPost= postRepository.findById(postId).get();
        existingPost.setPostStatus(PostStatus.APPROVED);
        postRepository.save(existingPost);

        return "Post approved";

    }

    public String approveAll(){

        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatus.APPROVED);
                    postRepository.save(post);
                });

        return "All posts are approved";
    }

    public String rejectPost(long postId){

        Post existingPost= postRepository.findById(postId).get();
        existingPost.setPostStatus(PostStatus.REJECTED);
        postRepository.save(existingPost);

        return "Post rejected";

    }

    public String rejectAll(){

        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatus.REJECTED);
                    postRepository.save(post);
                });

        return "All posts are rejected";
    }

    public List<Post> posts(){
        return postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatus.APPROVED)).collect(Collectors.toList());
    }

}
