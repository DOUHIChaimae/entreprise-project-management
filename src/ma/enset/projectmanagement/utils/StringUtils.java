package ma.enset.projectmanagement.utils;

import java.util.Objects;

public final class StringUtils {

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final CharSequence SEMI_COLON = ";";
    public static final String UNDERSCORE = "_";

    public static boolean isBlank(String value) {
        return Objects.isNull(value) || value.trim().length() == 0;
    }
}
