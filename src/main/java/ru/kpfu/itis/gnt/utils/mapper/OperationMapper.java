package ru.kpfu.itis.gnt.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.calculator.operations.CalculatorOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OperationMapper implements Mapper<Map<String, CalculatorOperation>, Map<String, CalculatorOperation>> {
    private final static String MAPPING_EXCEPTION_MESSAGE = "This operation is not supported!";

    /**
     * @param values map where
     *               key = operation ( +, -, / etc.)
     *               value = class name that provides calculation functionality
     * @return map where
     *         key = operation ( +, -, / etc.)
     *         value = operation class instance
     */

    public Map<String, CalculatorOperation> map(Map<String, CalculatorOperation> values) throws UnsupportedOperationException {
        HashMap<String, CalculatorOperation> map = new HashMap<>();
        for (Map.Entry<String, CalculatorOperation> entry : values.entrySet()) {
            try {
                map.put(entry.getValue().getSign(), entry.getValue());
            } catch (Exception ex) {
                throw new UnsupportedOperationException(MAPPING_EXCEPTION_MESSAGE);
            }
        }
        return map;
    }
}
