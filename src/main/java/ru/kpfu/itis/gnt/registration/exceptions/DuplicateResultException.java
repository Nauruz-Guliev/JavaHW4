package ru.kpfu.itis.gnt.registration.exceptions;

public class DuplicateResultException  extends DBException{
    public DuplicateResultException(String message) {
        super(message);
    }
}
