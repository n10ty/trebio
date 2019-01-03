package com.trebio.context.channel.api.channel.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.*;

@Component
public class ScheduledPool {

    private ScheduledExecutorService pool;

    @Autowired
    public ScheduledPool() {
        pool = Executors.newScheduledThreadPool(5);
    }

    public void schedule(Callable callable, Date publishAt) {
        Long waitToPublish = Duration.between(Instant.now(), publishAt.toInstant()).getSeconds();
        //Logger.d("APP", String.format("Scheduled after: %s seconds", waitToPublish));
        pool.schedule(callable, waitToPublish, TimeUnit.SECONDS);
    }
}
