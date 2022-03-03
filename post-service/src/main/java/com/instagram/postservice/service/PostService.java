package com.instagram.postservice.service;

import com.instagram.postservice.exception.NotAllowedException;
import com.instagram.postservice.exception.ResourceNotFoundException;
import com.instagram.postservice.messaging.PostEventSender;
import com.instagram.postservice.module.Post;
import com.instagram.postservice.payload.PostRequest;
import com.instagram.postservice.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostEventSender postEventSender;

    public PostService(PostRepository postRepository, PostEventSender postEventSender) {
        this.postRepository = postRepository;
        this.postEventSender = postEventSender;
    }

    public Post createPost(PostRequest postRequest) {
        log.info("creating post image url {}", postRequest.getImageUrl());

        Post post = new Post(postRequest.getImageUrl(), postRequest.getCaption());

        post = postRepository.save(post);
        postEventSender.sendPostCreated(post);

        log.info("post {} is saved successfully for user {}", post.getId(), post.getUsername());

        return post;
    }

    public void deletePost(String postId, String username) {
        log.info("deleting post {}", postId);

        postRepository
                .findById(postId)
                .map(post -> {
                    if (!post.getUsername().equals(username)) {
                        log.warn("user {} is not allowed to delete post id {}", username, postId);
                        throw new NotAllowedException(username, "post id " + postId, "delete");
                    }

                    postRepository.delete(post);
                    postEventSender.sendPostDeleted(post);
                    return post;
                })
                .orElseThrow(() -> {
                    log.warn("post not found id {}", postId);
                    return new ResourceNotFoundException(postId);
                });
    }

    public List<Post> postsByUsername(String username) {
        return postRepository.findByUsernameOrderByCreatedAtDesc(username);
    }

    public List<Post> postsByIdIn(List<String> ids) {
        return postRepository.findByIdInOrderByCreatedAtDesc(ids);
    }
}
