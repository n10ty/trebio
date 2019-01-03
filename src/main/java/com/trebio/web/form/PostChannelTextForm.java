package com.trebio.web.form;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Valid
public class PostChannelTextForm {

    @NotEmpty
    public String postText;
    public Date publishAt;

    public PostChannelTextForm(String postText, String publishAt) throws ParseException {
        this.postText = postText;
        if (!publishAt.isEmpty()) {
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss X");
            this.publishAt = parser.parse(publishAt);
        }
    }
}
