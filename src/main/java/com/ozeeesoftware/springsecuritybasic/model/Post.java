package com.ozeeesoftware.springsecuritybasic.model;

import com.ozeeesoftware.springsecuritybasic.common.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private String subject;
    private String description;
    private String postUserName;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

}
