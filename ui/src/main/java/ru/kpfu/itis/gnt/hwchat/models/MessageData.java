package ru.kpfu.itis.gnt.hwchat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageData {
    private String text;
    private String sender;
    private Date dateCreated;
    private Sticker sticker;
    private List<String> availableStickerList;
}
