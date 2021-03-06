package utils;

import java.time.LocalDate;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static String changeFormat(String dateString, String oldFormat, String newFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date d = sdf.parse(dateString);
        sdf.applyPattern(newFormat);
        return sdf.format(d);
    }

    public static String changeFormat(Date date, String newFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
        return formatter.format(date);
    }

    public static Date stringToDate(String format, String dateString) throws ParseException {
        Date date =new SimpleDateFormat(format).parse(dateString);
        return date;
    }
}
