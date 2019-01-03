package com.trebio.context.channel.api.channel.schedule;

import com.trebio.context.channel.model.Post;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SchedulePostListener implements ApplicationListener<SchedulePostEvent> {
    private ScheduledPool pool;
    private ApplicationEventPublisher publisher;
    private PostRepository postRepository;

    @Autowired
    public SchedulePostListener(ScheduledPool pool, ApplicationEventPublisher publisher, PostRepository postRepository) {
        this.pool = pool;
        this.publisher = publisher;
        this.postRepository = postRepository;
    }

    @Override
    public void onApplicationEvent(SchedulePostEvent event)  {
        Optional<Post> postOptional = postRepository.findById(event.getPostId());
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            //Logger.d("APP", String.format("Scheduled: %s, %s", post.getId(), post.getPublishAt()));

            pool.schedule(
                    new SchedulePoster(event.getPostId(), new SchedulePosterContext(publisher, postRepository)),
                    post.getPublishAt()
                );
        }
    }
}
