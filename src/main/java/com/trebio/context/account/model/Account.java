package com.trebio.context.account.model;

import com.trebio.context.account.exception.ChannelNotFoundException;
import com.trebio.context.channel.model.Channel;

import javax.persistence.*;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Entity(name = "user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column()
    private boolean enabled;

    @Column()
    private boolean verified;

    @OneToMany(mappedBy = "account", targetEntity = Channel.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Channel> channels;


    public Account(String email,  String password) {
        this.email = email;
        this.password = password;
        enable();
    }

    private void enable() {
        this.enabled = true;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean enabled() {
        return enabled;
    }

    public boolean verified() {
        return true;
    }

    public boolean hasNoChannel() {
        return channels.isEmpty();
    }

    public boolean hasAnyChannel() {
        return !hasNoChannel();
    }

    public Channel getChannel() throws ChannelNotFoundException {
        Optional<Channel> first = channels.stream().findFirst();
        if (first.isPresent()) {
            return first.get();
        }

        throw new ChannelNotFoundException();
    }

    public Long getChannelId() throws ChannelNotFoundException {
        return getChannel().getId();
    }

    public Long getId() {
        return id;
    }

    private Account() {
    }

    public void addChannel(Channel channel) {
        if (hasNoChannel()) {
            channels.add(channel);
        }
    }
}