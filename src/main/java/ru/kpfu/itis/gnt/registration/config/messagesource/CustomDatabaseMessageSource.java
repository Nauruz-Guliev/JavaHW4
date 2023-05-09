package ru.kpfu.itis.gnt.registration.config.messagesource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.registration.data.repository.message.MessageSourceRepository;
import ru.kpfu.itis.gnt.registration.entity.MessageEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.exceptions.MessageSourceException;

import java.text.MessageFormat;
import java.util.Locale;


@Component
public class CustomDatabaseMessageSource extends ReloadableResourceBundleMessageSource {
    private final MessageSourceRepository repository;


    @Autowired
    public CustomDatabaseMessageSource(MessageSourceRepository repository) {
        this.repository = repository;
    }

    /**
     * @param key    ключ, по которому находится сообщение в базе данных.
     * @param locale Не может быть null, так как {@link ru.kpfu.itis.gnt.registration.config.localeresolver.CustomSessionLocaleResolver}
     *               всегда возвращает какую-то локаль.
     * @return Объект MessageFormat, который может и не содержать сообщения, если не было создана
     * запись
     */
    @Override
    protected MessageFormat resolveCode(@Nullable String key, @NonNull Locale locale) {
        try {
            if (key == null) {
                return null;
            }
            // сначала поиск по заданной локали, затем по дефолтной, которая берётся из системы
            MessageEntity entity = repository.findByKeyAndLocale(key, locale).orElse(
                    repository.findByKeyAndLocale(key, getDefaultLocale()).orElse(null)
            );
            if (entity == null) {
                // если с заданными локалями ничего не нашлось, то попробуем по ключу хоть какое-то сообщение найти.
                entity = repository.findByKey(key).orElse(null);
            }
            if (entity != null) {
                // можно было бы самому реализовать интерфейс MessageSource и все его 3 метода
                // но эту логику в данном случае выполняет класс MessageFormat
                return new MessageFormat(entity.getValue(), locale);
            } else {
                // если по ключу вообще ничего не нашлось, то вернем null
                return null;
            }
        } catch (DBException ex) {
            // Logger logger = Logger.getLogger(CustomDatabaseMessageSource.class.getName());
            // logger.log(Level.WARNING, ex.getMessage());
            System.err.println(ex.getMessage());
            // пользователю необязательно знать детали.
            throw new MessageSourceException("Message with key \"" + key + "\" was not found");
        }
    }
}
