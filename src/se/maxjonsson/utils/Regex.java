package se.maxjonsson.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Max on 2016-12-12.
 */
public class Regex {

    public static Matcher parse(String regex, String input) {
        final Matcher matcher = Pattern.compile(regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        else {
            throw new RuntimeException("'" + regex + "' didn't match given input '" + input + "'" );
        }
    }
}
