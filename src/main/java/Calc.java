import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Calc {

    private static final String DOUBLE_SLASH = "//";
    private static final String EMPTY_STRING = "";
    private static final String EOL = "\n";
    private static final String COMMA = ",";
    private static final String DEFAULT_SEPARATOR = COMMA;
    private static final String REGEX_BETWEEN_BRACKETS = "\\[(.*?)\\]";
    private static final String EXCEPTION_NEGATIVE_NUMBER_MESSAGE = "Numbers [%s] can not be negative";
    public static final int MOD_HUNDREAD = 1000;

    public Integer add(String numbers) {

        if (Util.isNullOrEmpty(numbers)) {
            return 0;
        }

        String separatorDeclaration = getSeparatorDeclaration(numbers);

        List<String> separator = getSeparator(separatorDeclaration);

        String myNewString = removeUnuselessStrings(numbers, separator, separatorDeclaration);

        String[] arrayOfStrings = myNewString.split(DEFAULT_SEPARATOR);

        List<String> myStringList = Arrays.asList(arrayOfStrings);

        List<Integer> integerList = convertArrayOfStringsToInt(myStringList);

        checkIfNegativeNumbers(integerList);

        Integer sum = sumArrayOfIntegers(integerList);

        return modHundread(sum);

    }

    private String removeUnuselessStrings(String numbers, List<String> separators, String separatorDeclaration) {
        String myNewString = numbers.replace(separatorDeclaration, EMPTY_STRING);
        return replaceSeparators(separators, myNewString);
    }


    private String getSeparatorDeclaration(String numbers) {
        if (hasCustomSeparator(numbers)) {
            return numbers.split("\n")[0] + "\n";
        }
        return EMPTY_STRING;
    }


    private String replaceSeparators(List<String> separators, String myNewString) {
        for (String separator : separators) {
            myNewString = myNewString.replace(separator, DEFAULT_SEPARATOR);
        }
        return myNewString;
    }

    private List<String> getSeparator(String separatorDeclaration) {
        List<String> separatorList = new ArrayList<>();
        separatorList.add(EOL);
        if (hasCustomSeparator(separatorDeclaration)) {
            List<String> customList = extractCustomSeparator(separatorDeclaration);
            separatorList.addAll(customList);
        } else {
            separatorList.add(COMMA);
        }
        return separatorList;
    }

    private List<String> extractCustomSeparator(String separatorDeclaration) {
        List<String> customSeparatorList = getDelimiterListBetweenBrackets(separatorDeclaration);
        if (customSeparatorList.size() > 0) {
            return customSeparatorList;
        }
        return Arrays.asList(separatorDeclaration.substring(2, 3));
    }

    private boolean hasCustomSeparator(String numbers) {
        return (numbers.length() > 3 && numbers.substring(0, 2).equals(DOUBLE_SLASH));
    }

    private List<Integer> convertArrayOfStringsToInt(List<String> myStringList) {
        return myStringList
                .stream()
                .map(stringNumber -> Integer.parseInt(stringNumber))
                .collect(Collectors.toList());
    }

    private Integer sumArrayOfIntegers(List<Integer> myIntegerList) {
        return myIntegerList
                .stream()
                .reduce(0, (current,accum) -> current + accum);
    }

    private Integer modHundread(Integer result) {
        return result % MOD_HUNDREAD;
    }

    private void checkIfNegativeNumbers(List<Integer> myIntegerList) {
        String negativeStringNumbers = myIntegerList
                .stream()
                .filter(value -> value < 0)
                .map(stringNumber -> String.valueOf(stringNumber))
                .collect(Collectors.joining(", ")); 
        if (!Util.isNullOrEmpty(negativeStringNumbers)) {
            String strError = format(EXCEPTION_NEGATIVE_NUMBER_MESSAGE, negativeStringNumbers);
            System.out.println(strError);
            throw new IllegalArgumentException(strError);
        }
    }


    //copy from  https://stackoverflow.com/questions/4006113/java-regular-expression-to-extract-content-within-square-brackets
    public List<String> getDelimiterListBetweenBrackets(String numbers) {
        List<String> betweenBrackets = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX_BETWEEN_BRACKETS);
        Matcher matcher = pattern.matcher(numbers);

        while (matcher.find()) {
            betweenBrackets.add(matcher.group(1));
        }

        return betweenBrackets;
    }
}
