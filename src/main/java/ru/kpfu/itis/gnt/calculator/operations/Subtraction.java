package ru.kpfu.itis.gnt.calculator.operations;

import org.springframework.stereotype.Component;

@Component
public class Subtraction extends CalculatorOperation {

    private static final String SIGN = "-";
    @Override
    public double execute(double valueFirst, double valueSecond) {
        return valueFirst - valueSecond;
    }

    @Override
    public String getSign() {
        return SIGN;
    }

    @Override
    public int hashCode() {
        return SIGN.hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subtraction operation = (Subtraction) obj;
        return getSign().equals(operation.getSign());
    }

    @Override
    public String toString() {
        return "This operation represents: " + getSign();
    }
}
