package com.trebio.context.channel.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Post")
@Table(name = "channel_post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.STRING,
        name = "type",
        columnDefinition = "enum('photo','text') NOT NULL DEFAULT 'photo'"
)
abstract public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    protected Channel channel;

    @Column(name = "publish_at", nullable = false)
    private Date publishAt;

    @Column(name = "published_at")
    private Date publishedAt;

    protected Post(Channel channel, Date publishAt) {
        this.channel = channel;
        this.publishAt = publishAt;
    }

    public boolean isText() {
        return this instanceof TextPost;
    }

    public boolean isPhoto() {
        return this instanceof PhotoPost;
    }

    public Channel getChannel() {
        return channel;
    }

    public void publish()
    {
        if (publishedAt == null) {
            publishedAt = new Date();
        }
    }

    public boolean isPublished() {
        return publishedAt == null;
    }

    public Long getId() {
        return id;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    protected Post(){}
}
