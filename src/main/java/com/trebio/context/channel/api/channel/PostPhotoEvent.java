package com.trebio.context.channel.api.channel;

import com.trebio.context.channel.model.Channel;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;
import java.util.Date;

public class PostPhotoEvent extends ApplicationEvent {
    protected Channel channel;
    protected Path filePath;
    protected String caption;
    private Date publishAt;

    public PostPhotoEvent(Channel channel, Path filePath, String caption) {
        super(new Object());
        this.channel = channel;
        this.filePath = filePath;
        this.caption = caption;
    }

    public PostPhotoEvent(Channel channel, Path filePath, String caption, Date publishAt) {
        super(new Object());
        this.channel = channel;
        this.filePath = filePath;
        this.caption = caption;
        this.publishAt = publishAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getCaption() {
        return caption;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public boolean isInstant() {
        return publishAt == null;
    }
}
