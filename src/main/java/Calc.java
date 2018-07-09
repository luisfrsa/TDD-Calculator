import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {


    private static final String DOUBLE_SLASH = "//";
    private static final String EMPTY_STRING = "";
    private static final String EOL = "\n";
    private static final String COMMA = ",";
    private static final String DEFAULT_SEPARATOR = COMMA;
    public static final String REGEX_BETWEEN_BRACKETS = "\\[(.*?)\\]";

    public Integer add(String numbers) {

        if (Util.isNullOrEmpty(numbers)) {
            return 0;
        }

        List<String> separator = getSeparator(numbers);
        String separatorDeclaration = getSeparatorDeclaration(numbers);
        String myNewString = removeUnuselessStrings(numbers, separator, separatorDeclaration);
        String[] arrayOfStrings = myNewString.split(DEFAULT_SEPARATOR);
        List<String> myStringList = Arrays.asList(arrayOfStrings);
        return sumArrayOfStrings(myStringList);

    }

    private String removeUnuselessStrings(String numbers, List<String> separators, String separatorDeclaration) {
        String myNewString = numbers.replace(separatorDeclaration, EMPTY_STRING);
        return replaceSeparators(separators, myNewString);
    }


    private String getSeparatorDeclaration(String numbers) {
        if (hasCustomSeparator(numbers)) {
            return numbers.split("\n")[0]+"\n";
        }
        return EMPTY_STRING;
    }


    private String replaceSeparators(List<String> separators, String myNewString) {
        for (String separator : separators) {
            myNewString = myNewString.replace(separator, DEFAULT_SEPARATOR);
        }
        return myNewString;
    }

    private List<String> getSeparator(String numbers) {
        List<String> separatorList = new ArrayList<>();
        separatorList.add(EOL);
        if (hasCustomSeparator(numbers)) {
            separatorList.add(numbers.substring(2, 3));
        } else {
            separatorList.add(COMMA);
        }
        return separatorList;
    }

    private boolean hasCustomSeparator(String numbers) {
        return (numbers.length() > 3 && numbers.substring(0, 2).equals(DOUBLE_SLASH));
    }

    private Integer sumArrayOfStrings(List<String> myStringList) {
        Integer result = myStringList
                .stream()
                .map(stringNumber -> sumOnlyPositives(stringNumber))
                .reduce(0, (accum, current) -> current + accum);
        return result % 1000;
    }

    private int sumOnlyPositives(String stringNumber) {
        Integer number = Integer.parseInt(stringNumber);
        return number > 0 ? number : 0;
    }


    //copy from  https://stackoverflow.com/questions/4006113/java-regular-expression-to-extract-content-within-square-brackets
    public List<String> getDelimiterListBetweenBrackets(String numbers) {
        List<String> betweenBrackets = new ArrayList<>();
        numbers = "//[1][2][3]\n";

        Pattern pattern = Pattern.compile(REGEX_BETWEEN_BRACKETS);
        Matcher matcher = pattern.matcher(numbers);

        while (matcher.find()) {
            betweenBrackets.add(matcher.group(1));
        }
        return betweenBrackets;
    }

    public class CustomSeparator {
        protected String stringSeparator;
        protected List<String> separatorList;


    }
}
