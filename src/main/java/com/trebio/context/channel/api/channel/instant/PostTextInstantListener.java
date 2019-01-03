package com.trebio.context.channel.api.channel.instant;

import com.trebio.context.bot.model.PostingBot;
import com.trebio.context.channel.model.Channel;
import com.trebio.context.channel.model.Post;
import com.trebio.context.channel.model.TextPost;
import com.trebio.context.channel.persistence.jpa.PostRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class PostTextInstantListener implements ApplicationListener<PostTextInstantEvent> {
    private PostingBot bot;
    private PostRepository postRepository;

    public PostTextInstantListener(PostingBot bot, PostRepository postRepository) {
        this.bot = bot;
        this.postRepository = postRepository;
    }

    @Override
    public void onApplicationEvent(PostTextInstantEvent event) {
        Optional<Post> optionalPost = postRepository.findById(event.getPostId());
        if (optionalPost.isPresent()) {
            TextPost post = (TextPost) optionalPost.get();
            Channel channel = post.getChannel();
            try {
                bot.execute(new SendMessage(channel.getNameWithAt(), post.getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //Logger.d("API", responseMessage.toString());
            //Logger.d("ERROR", e.getMessage());
        }
    }
}
