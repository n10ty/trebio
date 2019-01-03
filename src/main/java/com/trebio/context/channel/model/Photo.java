package com.trebio.context.channel.model;

import com.github.badoualy.telegram.tl.api.TLAbsMessage;
import com.github.badoualy.telegram.tl.api.TLMessage;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_path")
    private String internalPath;

    @ManyToOne(targetEntity = PhotoPost.class)
    private PhotoPost post;

    public Photo(PhotoPost post, String internalPath) {
        this.post = post;
        post.addPhoto(this);
        this.internalPath = internalPath;
    }

    public String getInternalPath() {
        return internalPath;
    }

    private Photo() {
    }

    private Photo(TLAbsMessage message) {
        if (message instanceof TLMessage) {

        }
    }
}
