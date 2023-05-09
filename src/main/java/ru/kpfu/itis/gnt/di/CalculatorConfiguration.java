package ru.kpfu.itis.gnt.di;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.gnt.calculator.*;
import ru.kpfu.itis.gnt.calculator.operations.*;
import ru.kpfu.itis.gnt.exceptions.OperationImplementationException;
import ru.kpfu.itis.gnt.exceptions.UnsupportedOperationException;
import ru.kpfu.itis.gnt.storage.OperationStorage;
import ru.kpfu.itis.gnt.utils.extractor.ValuesExtractor;
import ru.kpfu.itis.gnt.utils.mapper.OperationMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan( {"ru.kpfu.itis.gnt.calculator.operations", "ru.kpfu.itis.gnt.utils"})
public class CalculatorConfiguration {

    // сюда спринг "доставляет" карту с ключом = имя класса и значением = экземпляр операции
    // название класса следует замапить в знак, чтобы потом можно было обрашаться по ключу знака в карте.
    @Bean
    public CalculatorImpl calculator(OperationMapper mapper, Map<String, CalculatorOperation> calculatorOperations) throws UnsupportedOperationException {
        return new CalculatorImpl(mapper.map(calculatorOperations));
    }


    @Bean
    public String calculatorOperationSigns(@NotNull OperationStorage operationStorage) {
        return operationStorage.getOperationsString();
    }


    // бины создаются только для того, чтобы именно по этим названиям можно было доставать экземпляры из контекста
    // сами классы помечены аннотацией component и создание bean'ов можно было опустить
    @Bean
    public OperationStorage storage() throws OperationImplementationException {
        return new OperationStorage();
    }

    @Bean
    public ValuesExtractor extractor() throws OperationImplementationException {
        return new ValuesExtractor(calculatorOperationSigns(storage()));
    }

}
