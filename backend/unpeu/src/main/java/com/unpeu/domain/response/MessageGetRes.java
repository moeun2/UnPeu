package com.unpeu.domain.response;

import com.unpeu.domain.entity.Message;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@ApiModel("MessageGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageGetRes {

    private Long messageId;
    private String sender;
    private String content;
    private String category;
    private int price;
    private LocalDateTime createdAt;

    private Long presentId;
    private String presentImg;

    private Long userId;

    public MessageGetRes(Message message) {
        this.messageId = message.getMessageId();
        this.sender = message.getSender();
        this.content = message.getContent();
        this.category = message.getCategory();
        this.price = message.getPrice();
        this.createdAt = message.getCreatedAt();
        if (message.getPresent() != null) {
            this.presentId = message.getPresent().getPresentId();
            this.presentImg = message.getPresent().getPresentImg();
        } else {
            this.presentId = null;
            this.presentImg = null;
        }
        this.userId = message.getUser().getId();
    }
}
