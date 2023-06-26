package ru.kpfu.itis.gnt.hwchat.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageData {
    private String text;
    private String sender;
    private Date dateCreated;
}
