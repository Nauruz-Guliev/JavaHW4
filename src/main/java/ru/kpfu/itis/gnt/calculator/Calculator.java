package ru.kpfu.itis.gnt.calculator;

import ru.kpfu.itis.gnt.exceptions.InvalidValueException;
import ru.kpfu.itis.gnt.exceptions.UnsupportedOperationException;

public interface Calculator {
    double calculate(double valueFirst, double valueSecond, String operationName) throws UnsupportedOperationException, InvalidValueException;
}
