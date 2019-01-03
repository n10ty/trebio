package com.trebio.context.channel.service.dto;

public class AddChannel {
    public String channelName;
    public Long accountId;

    public AddChannel(String channelName, Long accountId) {
        this.channelName = channelName;
        this.accountId = accountId;
    }
}
