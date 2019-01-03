package com.trebio.context.channel.service;

import com.trebio.context.account.model.Account;
import com.trebio.context.account.persistence.jpa.AccountRepository;
import com.trebio.context.channel.model.Channel;
import com.trebio.context.channel.persistence.jpa.ChannelRepository;
import com.trebio.context.channel.service.dto.AddChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChannelInitializer {

    AccountRepository accountRepository;
    private ChannelRepository channelRepository;

    @Autowired
    public ChannelInitializer(AccountRepository accountRepository, ChannelRepository channelRepository) {
        this.accountRepository = accountRepository;
        this.channelRepository = channelRepository;
    }

    public void addChannelToAccount(AddChannel addChannel) {
        Optional<Account> optionalAccount = accountRepository.findById(addChannel.accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Channel channel = new Channel(addChannel.channelName, account);
            channelRepository.saveAndFlush(channel);
            accountRepository.saveAndFlush(account);
        }
    }
}
