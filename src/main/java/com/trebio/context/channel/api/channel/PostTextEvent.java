package com.trebio.context.channel.api.channel;

import com.trebio.context.channel.model.Channel;
import org.springframework.context.ApplicationEvent;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Validated
public class PostTextEvent extends ApplicationEvent {
    protected Channel channel;
    @NotNull
    protected String text;
    protected Date publishAt;

    public PostTextEvent(Channel channel, String text) {
        super(new Object());
        this.channel = channel;
        this.text = text;
    }

    public PostTextEvent(Channel channel, String text, Date publishAt) {
        super(new Object());
        this.channel = channel;
        this.text = text;
        this.publishAt = publishAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getText() {
        return text;
    }

    public boolean isInstant() {
        return this.publishAt == null;
    }

    public Date getPublishAt() {
        return publishAt;
    }
}
