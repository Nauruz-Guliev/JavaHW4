package ru.kpfu.itis.gnt.registration.data.repository.message;

import ru.kpfu.itis.gnt.registration.entity.MessageEntity;

import java.util.Locale;
import java.util.Optional;

public interface MessageSourceRepository {
    Optional<MessageEntity> findByKeyAndLocale(String key, Locale locale);

    Optional<MessageEntity> findByKey(String key);
}
