package com.trebio.context.channel.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "TextPost")
@DiscriminatorValue("text")
public class TextPost extends Post {
    @Column(name = "text")
    private String text;

    public TextPost(Channel channel, String text, Date publishAt) {
        super(channel, publishAt);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private TextPost() {
        super();
    }
}
