package ru.kpfu.itis.gnt.hwchat.models;

import java.util.Objects;

public record Sticker(String encodedSticker, String code) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sticker sticker)) return false;
        return Objects.equals(encodedSticker, sticker.encodedSticker) && Objects.equals(code, sticker.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encodedSticker, code);
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "encodedSticker='" + encodedSticker + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
