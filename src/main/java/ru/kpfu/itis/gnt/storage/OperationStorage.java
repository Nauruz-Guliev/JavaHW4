package ru.kpfu.itis.gnt.storage;

import ru.kpfu.itis.gnt.calculator.operations.*;
import ru.kpfu.itis.gnt.exceptions.OperationImplementationException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Хранилище доступных знаков. Используется по большей части для вывода в консоли.
 */
public class OperationStorage {

    private final Set<String> set;


    public OperationStorage() throws OperationImplementationException {
        this.set = new HashSet<>();
        initOperations();
    }

    private void initOperations() throws OperationImplementationException {
        addOperation(Addition.class);
        addOperation(Subtraction.class);
        addOperation(Multiplication.class);
        addOperation(Division.class);
    }

    private void addOperation(Class<? extends CalculatorOperation> className) throws OperationImplementationException {
        try {
            Field field = className.getDeclaredField("SIGN");
            field.setAccessible(true);
            String sign = (String) field.get(className);
            set.add(sign);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OperationImplementationException("Wrong implementation of calculation operation!");
        }
    }

    public Set<String> getOperationsSet() {
        return set;
    }

    public String getOperationsString() {
        return String.join("", set);
    }
}
