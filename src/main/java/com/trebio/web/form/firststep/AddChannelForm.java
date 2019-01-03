package com.trebio.web.form.firststep;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AddChannelForm {
    @NotNull
    @Length(min = 4, max = 256, message = "Введите корректное название канала")
    public String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
