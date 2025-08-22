package io.wliamp.msg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import io.wliamp.msg.dto.ChatMessage;
import io.wliamp.msg.entity.Message;
import io.wliamp.msg.repo.MessageRepo;

@Service
@RequiredArgsConstructor
public class PersistenceService {
    private final MessageRepo messageRepo;

    public Mono<Message> persist(ChatMessage msg) {
        return messageRepo.save(Message.builder()
                .sender(msg.sender())
                .content(msg.content())
                .channel(msg.channel())
                .timestamp(msg.timestamp())
                .build());
    }
}
