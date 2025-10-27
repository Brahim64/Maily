package com.api.back.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastMessage {
    private String messageId;
    private String senderId;
    private String content;
    private Date timestamp;
}
