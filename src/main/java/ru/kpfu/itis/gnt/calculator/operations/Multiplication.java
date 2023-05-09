package ru.kpfu.itis.gnt.calculator.operations;

import org.springframework.stereotype.Component;

@Component
public class Multiplication extends CalculatorOperation {

    private static final String SIGN = "*";
    @Override
    public double execute(double valueFirst, double valueSecond) {
        return valueSecond * valueFirst;
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
        Multiplication operation = (Multiplication) obj;
        return getSign().equals(operation.getSign());
    }

    @Override
    public String toString() {
        return "This operation represents: " + getSign();
    }
}
