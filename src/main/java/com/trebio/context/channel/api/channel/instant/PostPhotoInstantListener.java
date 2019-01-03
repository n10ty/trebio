package com.trebio.context.channel.api.channel.instant;

import com.trebio.context.bot.model.PostingBot;
import com.trebio.context.channel.model.Channel;
import com.trebio.context.channel.model.PhotoPost;
import com.trebio.context.channel.model.Post;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PostPhotoInstantListener  implements ApplicationListener<PostPhotoInstantEvent> {

    private final static Logger LOGGER = Logger.getLogger("trebio");

    private PostRepository postRepository;
    private PostingBot bot;

    @Autowired
    public PostPhotoInstantListener(PostRepository postRepository, PostingBot bot) {
        this.postRepository = postRepository;
        this.bot = bot;
    }

    public void onApplicationEvent(PostPhotoInstantEvent event) {
        Optional<Post> postOptional = postRepository.findById(event.getPostId());

        if (postOptional.isPresent() && (postOptional.get() instanceof PhotoPost)) {
            try {
                PhotoPost post = (PhotoPost) postOptional.get();
                Channel channel = post.getChannel();

                SendPhoto sendPhotoMethod = new SendPhoto();
                sendPhotoMethod.setCaption(post.getCaption());
                sendPhotoMethod.setChatId(channel.getNameWithAt());
                File file = new File(post.getPhoto().getInternalPath());
                sendPhotoMethod.setNewPhoto(file);
                bot.sendPhoto(sendPhotoMethod);
                post.publish();
                postRepository.saveAndFlush(post);
                LOGGER.info("posted image:" + post.getPhoto().getInternalPath());
            } catch (TelegramApiException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
