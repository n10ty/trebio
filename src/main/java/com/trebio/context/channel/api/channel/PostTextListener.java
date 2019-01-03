package com.trebio.context.channel.api.channel;

import com.trebio.context.channel.api.channel.instant.PostTextInstantEvent;
import com.trebio.context.channel.api.channel.schedule.SchedulePostEvent;
import com.trebio.context.channel.model.*;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostTextListener implements ApplicationListener<PostTextEvent> {

    private PostRepository postRepository;
    private ApplicationEventPublisher publisher;

    public PostTextListener(PostRepository postRepository, ApplicationEventPublisher publisher) {
        this.postRepository = postRepository;
        this.publisher = publisher;
    }

    public void onApplicationEvent(@NotNull PostTextEvent event) {
        //Logger.d("APP", String.format("Text post: %s", event.text));
        TextPost post = savePost(event);
        ApplicationEvent postEvent = event.isInstant()
                ? new PostTextInstantEvent(post.getId())
                : new SchedulePostEvent(post.getId());
        publisher.publishEvent(postEvent);
    }

    private TextPost savePost(PostTextEvent event) {

        Date publishAt = event.isInstant() ?  new Date() : event.getPublishAt();

        TextPost post = new TextPost(event.getChannel(), event.getText(), publishAt);

        return postRepository.saveAndFlush(post);
    }
}
