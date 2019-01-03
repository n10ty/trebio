package com.trebio.web.converter;

import com.trebio.context.channel.model.Channel;
import com.trebio.context.channel.persistence.jpa.ChannelRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChannelByIdConverter implements Converter<Long, Channel> {
    private ChannelRepository channelRepository;

    public ChannelByIdConverter(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel convert(Long source) {
        return channelRepository.findById(source).get();
    }
}
