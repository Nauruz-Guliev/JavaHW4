package ru.kpfu.itis.gnt.calculator.operations;

import ru.kpfu.itis.gnt.exceptions.InvalidValueException;

public abstract class CalculatorOperation {
    public abstract double execute(double valueFirst, double valueSecond) throws InvalidValueException;

    private static final String SIGN = "";

    public abstract String getSign();
}
