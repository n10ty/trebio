package com.trebio.web.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostChannelPhotoForm {
    public String caption;
    public Date publishAt;
    public PostChannelPhotoForm(String caption, String publishAt)  throws ParseException {
        this.caption = caption;
        if (!publishAt.isEmpty()) {
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss X");
            this.publishAt = parser.parse(publishAt);
        }
    }
}
