package com.trebio.context.channel.api.channel.schedule;

import org.springframework.context.ApplicationEvent;

public class SchedulePostEvent  extends ApplicationEvent {

    private Long postId;

    public SchedulePostEvent(Long postId) {
        super(new Object());
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
