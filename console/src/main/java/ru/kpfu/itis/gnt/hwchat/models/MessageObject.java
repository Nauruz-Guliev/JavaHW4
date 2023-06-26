package ru.kpfu.itis.gnt.hwchat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageObject {
    private MessageData data;
    private MessageType type;
}
