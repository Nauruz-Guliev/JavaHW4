package ru.kpfu.itis.gnt.registration.data.repository.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.entity.LocaleEntity;
import ru.kpfu.itis.gnt.registration.entity.MessageEntity;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.utils.enums.ErrorEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Locale;
import java.util.Optional;

@Repository
public class MessageSourceRepositoryImpl implements MessageSourceRepository {
    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_FIND_VALUE_BY_KEY_AND_LOCALE = "SELECT * FROM messagesource WHERE key = ? AND languageid = ?;";

    //language=sql
    private static final String SQL_FIND_VALUE_BY_KEY = "SELECT * FROM messagesource WHERE key = ? LIMIT 1;";

    //language=SQL
    private static final String SQL_INSERT_LOCALE = "INSERT INTO locale (language, country) VALUES (?,?)";
    //language=SQL
    private static final String SQL_INSERT_MESSAGE = "INSERT INTO messagesource (key, value, languageid) VALUES (?, ?, ?)";
    //language=SQL
    private static final String SQL_FIND_LOCALE_BY_LANGUAGE_AND_COUNTRY = "SELECT * FROM locale WHERE language=? AND country=?";

    @Autowired
    public MessageSourceRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean insertMessage(MessageEntity message, Locale locale) {
        try {
            long localeId = getOrInsertLocale(locale);
            return jdbcTemplate.update(
                    SQL_INSERT_MESSAGE,
                    message.getKey(),
                    message.getValue(),
                    localeId) > 0;
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }

    public Optional<MessageEntity> findByKeyAndLocale(String key, Locale locale) {
        long localeId = getOrInsertLocale(locale);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_VALUE_BY_KEY_AND_LOCALE, new Object[]{key, localeId}, messageMapper));
        } catch (EmptyResultDataAccessException ex) {
            throw new DBException(key + ": " +  ErrorEnum.MESSAGE_NOT_FOUND.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString() + ": " + ex.getMessage());
        }
    }

    @Override
    public Optional<MessageEntity> findByKey(String key) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_VALUE_BY_KEY, new Object[]{key}, messageMapper));
        } catch (EmptyResultDataAccessException ex) {
            throw new DBException(ErrorEnum.MESSAGE_NOT_FOUND.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }

    private Long getOrInsertLocale(Locale locale) throws DBException {
        Optional<LocaleEntity> localeEntity = findLocale(locale);
        long localeId;
        if (localeEntity.isPresent()) {
            localeId = localeEntity.get().getId();
        } else {
            localeId = (long) insertLocale(locale).orElseThrow(() -> new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString()));
        }
        return localeId;
    }

    private Optional<LocaleEntity> findLocale(Locale locale) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_LOCALE_BY_LANGUAGE_AND_COUNTRY, new Object[]{locale.getLanguage(), locale.getCountry()}, localeMapper));
        } catch (EmptyResultDataAccessException ex) {
            throw new DBException(ErrorEnum.LOCALE_NOT_FOUND.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString() + ": " + ex.getMessage());
        }
    }

    public Optional<Number> insertLocale(Locale locale) throws DBException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LOCALE, new String[]{"id"});
                ps.setString(1, locale.getLanguage());
                ps.setString(2, locale.getCountry());
                return ps;
            }, keyHolder);

            return Optional.ofNullable(keyHolder.getKey());
        } catch (DuplicateKeyException ex) {
            throw new DBException(ErrorEnum.DUPLICATE_LOCALE_FIELDS_EXCEPTION.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString() + ": " + ex.getMessage());
        }
    }

    private final RowMapper<MessageEntity> messageMapper = (row, rowNumber) -> {
        MessageEntity message = new MessageEntity(row.getString("key"), row.getString("value"), row.getLong("languageId"));
        message.setId(row.getLong("id"));
        return message;
    };

    private final RowMapper<LocaleEntity> localeMapper = (row, rowNumber) -> {
        LocaleEntity locale = new LocaleEntity(row.getString("language"), row.getString("country"));
        locale.setId(row.getLong("id"));
        return locale;
    };

}
