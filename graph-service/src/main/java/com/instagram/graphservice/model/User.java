package com.instagram.graphservice.model;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Data
@Builder
@NodeEntity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    private String username;
    private String name;
    private String profilePic;
    @Relationship(type = "IS_FOLLOWING")
    private Set<Friendship> friendships;

}
