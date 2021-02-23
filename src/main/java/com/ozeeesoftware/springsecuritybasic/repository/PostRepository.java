package com.ozeeesoftware.springsecuritybasic.repository;

import com.ozeeesoftware.springsecuritybasic.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
