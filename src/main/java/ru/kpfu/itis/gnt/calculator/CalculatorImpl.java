package ru.kpfu.itis.gnt.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.calculator.operations.CalculatorOperation;
import ru.kpfu.itis.gnt.exceptions.InvalidValueException;
import ru.kpfu.itis.gnt.exceptions.UnsupportedOperationException;

import java.util.Map;

@Component
public class CalculatorImpl implements Calculator{
    private final Map<String, CalculatorOperation> operations;

    @Autowired
    public CalculatorImpl(Map<String, CalculatorOperation> operations) {
        this.operations = operations;
    }

    /**
     * @param valueFirst First number
     * @param valueSecond Second number
     * @param operationName Operation name is taken from {@link ru.kpfu.itis.gnt.storage.OperationStorage}
     * @return Calculation value
     * @throws UnsupportedOperationException It should never be thrown.
     * Operation will not be recognized by regex if it's not stored in {@link ru.kpfu.itis.gnt.storage.OperationStorage}
     */
    public double calculate(double valueFirst, double valueSecond, String operationName) throws UnsupportedOperationException, InvalidValueException {
        try {
            return operations.get(operationName).execute(valueFirst, valueSecond);
        } catch (NullPointerException ex) {
            throw new UnsupportedOperationException("This operation is not supported");
        }
    }

}
