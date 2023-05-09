package ru.kpfu.itis.gnt.registration.entity;

import java.util.Objects;

public class MessageEntity {
    private Long id;
    private String key;
    private String value;
    private Long language_id;

    public MessageEntity(String key, String value, Long language_id) {
        this.key = key;
        this.value = value;
        this.language_id = language_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Long language_id) {
        this.language_id = language_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity entity = (MessageEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(key, entity.key) && Objects.equals(value, entity.value) && Objects.equals(language_id, entity.language_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, value, language_id);
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", language_id=" + language_id +
                '}';
    }
}
