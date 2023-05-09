package ru.kpfu.itis.gnt.calculator.operations;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.exceptions.InvalidValueException;

@Component
public class Division extends CalculatorOperation {
    private static final String SIGN = "/";

    public double execute(double valueFirst, double valueSecond) throws InvalidValueException {
        if (valueSecond == 0) {
            throw new InvalidValueException("Can not divide by zero!");
        }
        return valueFirst / valueSecond;
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
        Division operation = (Division) obj;
        return getSign().equals(operation.getSign());
    }

    @Override
    public String toString() {
        return "This operation represents: " + getSign();
    }
}
