package ru.kpfu.itis.gnt.exceptions;

public class InvalidValueException extends Exception{
    public InvalidValueException(String errorMessage) {
        super(errorMessage);
    }
}
