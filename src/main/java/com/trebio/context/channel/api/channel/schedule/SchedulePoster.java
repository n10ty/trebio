package com.trebio.context.channel.api.channel.schedule;

import com.trebio.context.channel.api.channel.instant.PostPhotoInstantEvent;
import com.trebio.context.channel.api.channel.instant.PostTextInstantEvent;
import com.trebio.context.channel.model.Post;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Send schedule to pool.
 */
public class SchedulePoster implements Callable {

    private Long postId;
    private ApplicationEventPublisher publisher;
    private PostRepository postRepository;

    SchedulePoster(Long postId, SchedulePosterContext context) {
        this.postId = postId;
        publisher = context.getPublisher();
        postRepository = context.getPostRepository();
    }

    @Override
    public Object call() throws Exception {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.isText()) {
                publisher.publishEvent(new PostTextInstantEvent(postId));
            } else if(post.isPhoto()) {
                publisher.publishEvent(new PostPhotoInstantEvent(postId));
            } else {
                throw new Exception("Unsupported type of instant publish");
            }
        }

        return null;
    }
}
