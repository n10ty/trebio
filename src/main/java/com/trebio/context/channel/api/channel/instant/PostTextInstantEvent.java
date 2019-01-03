package com.trebio.context.channel.api.channel.instant;

import org.springframework.context.ApplicationEvent;

public class PostTextInstantEvent extends ApplicationEvent {
    private Long postId;

    public PostTextInstantEvent(Long postId) {
        super(new Object());
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
