package com.trebio.web.controller;

import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import com.trebio.context.bot.model.KotloBot;
import com.trebio.context.channel.api.channel.PostPhotoEvent;
import com.trebio.context.channel.api.channel.PostTextEvent;
import com.trebio.context.channel.model.Channel;
import com.trebio.web.form.PostChannelPhotoForm;
import com.trebio.web.form.PostChannelTextForm;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Controller
@RequestMapping(value="/post")
public class PostController {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public PostController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping(value = "/{channelId}/text")
    public ModelAndView showAddChannelTextForm(@PathVariable("channelId") Channel channel) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("channelId", channel.getId());
        mav.setViewName("channel/post_text");

        return mav;
    }

    @GetMapping(value = "/{channelId}/photo")
    public ModelAndView showAddChannelPhotoForm(@PathVariable("channelId") Channel channel) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("channelId", channel.getId());
        mav.setViewName("channel/post_photo");

        return mav;
    }

    @GetMapping(value = "/test")
    public ModelAndView showsend() throws IOException, RpcErrorException, NoSuchAlgorithmException {

        HashMap<String, Object> properties = new HashMap<>();
        ModelAndView m = new ModelAndView("test", properties);
        m.addObject("testtext","One\ntwo\nthree <in tag>");

        return m;
    }

    @PostMapping(value = "/{channelId}/text")
    public ModelAndView postChannelText(
            @PathVariable("channelId") Channel channel,
            @ModelAttribute("postTextForm") @Valid PostChannelTextForm postChannelTextForm,
            BindingResult bindingResult
    ) {
        ApplicationEvent event = new PostTextEvent(channel, postChannelTextForm.postText, postChannelTextForm.publishAt);
        publisher.publishEvent(event);
        HashMap<String, String> properties = new HashMap<>();
        properties.put("channelId", String.valueOf(channel.getId()));

        return new ModelAndView("channel/post_text", properties);
    }

    @PostMapping(value = "/{channelId}/photo")
    public ModelAndView postChannelPhoto(
            @PathVariable ("channelId") Channel channel,
            @ModelAttribute("postPhotoForm") @Valid PostChannelPhotoForm postChannelPhotoForm,
            @RequestParam("file") MultipartFile file,
            BindingResult bindingResult
    ) throws IOException {

        Path filePath = uploadPhoto(file);
        ApplicationEvent event = new PostPhotoEvent(channel, filePath, postChannelPhotoForm.caption, postChannelPhotoForm.publishAt);
        publisher.publishEvent(event);

        HashMap<String, String> properties = new HashMap<>();
        properties.put("channelId", String.valueOf(channel.getId()));

        return new ModelAndView("channel/post_photo", properties);
    }

    private Path uploadPhoto(MultipartFile file) throws IOException {
        Path filePath = Paths.get(
                String.format(
                        "%s/src/main/webapp/resources/image/post/%s.%s",
                        System. getProperty("user.dir"),
                        System.currentTimeMillis(),
                        FilenameUtils.getExtension(file.getOriginalFilename())
                )
        );
        File tmpFile = new File(filePath.toString());

        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            throw new IOException("Can't create file with path " + filePath);
        }
        file.transferTo(tmpFile);

        return filePath;
    }

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
    }
}
