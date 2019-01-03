package com.trebio.context.channel.api.channel;

import com.trebio.context.channel.api.channel.instant.PostPhotoInstantEvent;
import com.trebio.context.channel.api.channel.schedule.SchedulePostEvent;
import com.trebio.context.channel.model.Photo;
import com.trebio.context.channel.model.PhotoPost;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostPhotoListener implements ApplicationListener<PostPhotoEvent> {

    private PostRepository postRepository;
    private ApplicationEventPublisher publisher;

    @Autowired
    public PostPhotoListener(PostRepository postRepository, ApplicationEventPublisher publisher) {
        this.postRepository = postRepository;
        this.publisher = publisher;
    }

    public void onApplicationEvent(@NotNull PostPhotoEvent event) {
        PhotoPost post = savePost(event);
        ApplicationEvent postEvent = event.isInstant()
                ? new PostPhotoInstantEvent(post.getId())
                : new SchedulePostEvent(post.getId());
        publisher.publishEvent(postEvent);

        //Logger.d("APP", String.format("Post photo: %s, %s", post.getId(), post.getPublishAt()));
    }

    private PhotoPost savePost(PostPhotoEvent event) {

        Date publishAt = event.isInstant() ? new Date() : event.getPublishAt();

        PhotoPost post = new PhotoPost(event.getChannel(), publishAt, event.getCaption());
        new Photo(post, event.getFilePath().toString());

        return postRepository.saveAndFlush(post);
    }
}
