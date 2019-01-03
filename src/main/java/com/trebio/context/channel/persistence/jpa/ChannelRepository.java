package com.trebio.context.channel.persistence.jpa;

import com.trebio.context.channel.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ChannelRepository extends JpaRepository <Channel, Long>{


}
