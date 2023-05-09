package ru.kpfu.itis.gnt;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kpfu.itis.gnt.calculator.CalculatorImpl;
import ru.kpfu.itis.gnt.calculator.operations.Addition;
import ru.kpfu.itis.gnt.di.CalculatorConfiguration;
import ru.kpfu.itis.gnt.exceptions.InvalidValueException;
import ru.kpfu.itis.gnt.exceptions.UnsupportedNumberException;
import ru.kpfu.itis.gnt.exceptions.UnsupportedOperationException;
import ru.kpfu.itis.gnt.storage.OperationStorage;
import ru.kpfu.itis.gnt.utils.extractor.ValuesExtractor;

import java.util.*;

public class CalculatorApplication {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        new CalculatorApplication();
    }

    public CalculatorApplication() {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(CalculatorConfiguration.class);

            CalculatorImpl calculator = (CalculatorImpl) context.getBean("calculator");
            OperationStorage storage = (OperationStorage) context.getBean("storage");
            ValuesExtractor extractor = (ValuesExtractor) context.getBean("extractor");

            System.out.println(ANSI_YELLOW + "Supported operations: " + storage.getOperationsSet() + ANSI_RESET);

            Scanner scanner = new Scanner(System.in);

            String equation = "";
            while (true) {
                System.out.print("Enter your equation or \"exit\" to close: ");
                equation = scanner.nextLine();
                if (equation.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Result: " + ANSI_BLUE + calculateResult(calculator, extractor.extract(equation)) + ANSI_RESET);
            }
        } catch (UnsupportedNumberException | UnsupportedOperationException | InvalidValueException e) {
            System.out.println(ANSI_RED + "ERROR!!! " + e.getMessage() + ANSI_RESET);
        }
    }


    private double calculateResult(CalculatorImpl calculator, List<Pair<String, Double>> userOperationPairs) throws UnsupportedOperationException, InvalidValueException {
        double result = 0;
        for (Pair<String, Double> pair : userOperationPairs) {
            result = calculator.calculate(result, pair.getRight(), pair.getLeft());
        }
        return result;
    }
}
