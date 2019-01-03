package com.trebio.web.controller;

import com.github.badoualy.telegram.tl.api.TLAbsMessage;
import com.github.badoualy.telegram.tl.api.TLChannel;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.trebio.context.account.model.Account;
import com.trebio.context.account.security.User;
import com.trebio.context.bot.model.KotloBot;
import com.trebio.context.channel.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class HistoryController {
    private KotloBot bot;

    @Autowired
    public HistoryController(KotloBot bot) {
        this.bot = bot;
    }

    @GetMapping(value = "/history/{channelId}/{pageNumber}")
    public ModelAndView showHistory(
            @PathVariable("channelId") Channel channel,
            @PathVariable("pageNumber") Integer pageNumber
    ) throws Exception {
        TLVector<TLAbsMessage> messages = bot.parseHistory(channel, pageNumber - 1);
        TLChannel tlChannel = bot.getChannelInfo(channel).get();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("history");
        // start

        // finish
        mav.addObject("messages", messages);
        mav.addObject("pageNumber", pageNumber);
        mav.addObject("channel", tlChannel);
        mav.addObject("channelId", channel.getId());


        return mav;
    }

    @GetMapping(value = "/history/{channelId}")
    public ModelAndView showHistoryFirstPagde(
            @PathVariable("channelId") Channel channel
    ) throws Exception {
        return this.showHistory(channel, 1);
    }

    private Account getAccount() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal.getAccount();
    }
}
