package ru.kpfu.itis.gnt.registration.exceptions;

public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}
