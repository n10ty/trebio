package com.trebio.context.channel.persistence.jpa;

import com.trebio.context.channel.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends JpaRepository<Post, Long> {
}