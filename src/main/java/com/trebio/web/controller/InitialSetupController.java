package com.trebio.web.controller;

import com.trebio.context.account.exception.ChannelNotFoundException;
import com.trebio.context.account.model.Account;
import com.trebio.context.account.security.User;
import com.trebio.context.bot.model.KotloBot;
import com.trebio.context.bot.model.PostingBot;
import com.trebio.context.channel.model.Channel;
import com.trebio.context.channel.persistence.jpa.ChannelRepository;
import com.trebio.context.channel.service.ChannelInitializer;
import com.trebio.context.channel.service.dto.AddChannel;
import com.trebio.web.form.firststep.AddChannelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping(value = "/init")
public class InitialSetupController {

    private PostingBot bot;
    private ChannelInitializer channelInitializer;
    private ChannelRepository channelRepository;

    @Autowired
    public InitialSetupController(PostingBot bot, ChannelInitializer channelInitializer, ChannelRepository channelRepository) {
        this.bot = bot;
        this.channelInitializer = channelInitializer;
        this.channelRepository = channelRepository;
    }

    @GetMapping(value = "/steps/1")
    public ModelAndView showFirstStepForm() {
        if (getAccount().hasAnyChannel()) {
            return new ModelAndView("redirect:/init/steps/2");
        }
        return new ModelAndView("first_steps/first", "addChannelForm", new AddChannelForm());
    }

    @PostMapping(value = "/steps/1")
    public ModelAndView submitFirstStepForm(
            @ModelAttribute @Valid AddChannelForm addChannelForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("first_steps/first");
        }

        channelInitializer.addChannelToAccount(new AddChannel(addChannelForm.channelName, getAccount().getId()));

        return new ModelAndView("redirect:/init/steps/2");
    }

    @GetMapping(value = "/steps/2")
    public ModelAndView showSecondStepForm() {
        return new ModelAndView("first_steps/second", "addChannelForm", new AddChannelForm());
    }

    @GetMapping(value = "/steps/3")
    public ModelAndView showThirdStepForm() {
        HashMap<String, String> properties = new HashMap<>();
        try {
            properties.put("channelId", String.valueOf(getAccount().getChannelId()));
        } catch (ChannelNotFoundException e) {
            e.printStackTrace();
        }

        return new ModelAndView("first_steps/third", properties);
    }

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
    }

    private Account getAccount() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal.getAccount();
    }
}
