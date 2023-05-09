package ru.kpfu.itis.gnt.utils.extractor;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.exceptions.UnsupportedNumberException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValuesExtractor implements Extractor<List<Pair<String, Double>>, String> {
    private static final String REGEX_NUMBER_PATTERN = "([0-9]*[.]?[0-9]*)";
    private static final String NUMBER_EXCEPTION_MESSAGE = "Wrong number format";

    private final String resultingRegexPattern;

    private final List<Pair<String, Double>> list;

    @Autowired
    public ValuesExtractor(String operations) {
        resultingRegexPattern = initPattern(operations);
        this.list = new ArrayList<>();
    }

    private String initPattern(String operations) {
        return "([" + operations + "])?" + REGEX_NUMBER_PATTERN;
    }

    /**
     * @param input user input. It should be a mathematical equation. Example: 10+10+2
     * @return list of pairs where
     * left = operation sign
     * value = number
     */

    public List<Pair<String, Double>> extract(String input) throws UnsupportedNumberException {
        list.clear();
        Matcher m = Pattern.compile(resultingRegexPattern)
                .matcher(input);
        while (m.find()) {
            double value = 0;
            String operation = m.group(1);
            //Небольшой костыль для первого значения.
            //Так как первым всегда идёт число, то пусть перед ним будет + вместно null
            //Это ничего не должно изменить.
            if (operation == null && list.size() == 0) {
                operation = "+";
            } else if (operation == null){
                // если не нашлось операции, то вернем все предыдущие пары (операция | значение)
                return list;
            }
            try {
                value = Double.parseDouble(m.group(2));
            } catch (NumberFormatException e) {
                throw new UnsupportedNumberException(NUMBER_EXCEPTION_MESSAGE);
            }
            list.add(new ImmutablePair<>(operation, value));
        }
        return list;
    }

}
