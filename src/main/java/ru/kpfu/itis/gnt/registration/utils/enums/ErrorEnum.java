package ru.kpfu.itis.gnt.registration.utils.enums;

public enum ErrorEnum {

    USER_NOT_FOUND("Couldn't find the user."),

    LOCALE_NOT_FOUND("Locale is not found"),

    MESSAGE_NOT_FOUND("Message is not found"),

    UNKNOWN_DB_EXCEPTION("Unknown error occurred."),

    DUPLICATE_LOCALE_FIELDS_EXCEPTION("Such a locale already exists."),

    DUPLICATE_USER_FIELDS_EXCEPTION("Such a user already exists.");


    private final String message;

    ErrorEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return message;
    }
}
