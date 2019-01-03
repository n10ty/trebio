package com.trebio.context.channel.api.channel.schedule;

import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.springframework.context.ApplicationEventPublisher;

public class SchedulePosterContext {
    private ApplicationEventPublisher publisher;
    private PostRepository postRepository;

    SchedulePosterContext(ApplicationEventPublisher publisher, PostRepository postRepository) {
        this.publisher = publisher;
        this.postRepository = postRepository;
    }

    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }
}
