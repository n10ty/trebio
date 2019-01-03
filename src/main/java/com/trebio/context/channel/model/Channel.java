package com.trebio.context.channel.model;

import com.trebio.context.account.model.Account;

import javax.persistence.*;

@Entity(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Channel(String name, Account account) {
        this.name = name;
        this.account = account;
        account.addChannel(this);
    }

    public String getName() {
        return name;
    }

    protected Channel() {
    }

    public Long getId() {
        return id;
    }

    public String getNameWithAt() {
        return "@" + this.name;
    }
}