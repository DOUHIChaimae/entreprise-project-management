package ma.enset.projectmanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static Date from(String date) throws ParseException {
        return FORMATTER.parse(date);
    }
}
