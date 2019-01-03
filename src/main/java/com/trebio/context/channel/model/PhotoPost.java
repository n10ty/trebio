package com.trebio.context.channel.model;

import javax.persistence.*;
import java.util.*;

@Entity(name = "PhotoPost")
@DiscriminatorValue("photo")
public class PhotoPost extends Post {
    @OneToMany(targetEntity = Photo.class, mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Photo> photos;
    private String caption;

    public PhotoPost(Channel channel, Date publishAt, String caption) {
        super(channel, publishAt);
        this.caption = caption;
        this.photos = new HashSet<>();
    }

    public Photo getPhoto() {
        return photos.iterator().next();
    }

    public String getCaption() {
        return caption;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    private PhotoPost() {
    }
}
