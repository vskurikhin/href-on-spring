package su.svn.utils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class StringHelper
{
    private static Pattern pattern = Pattern.compile("[-0-9a-zA-Z .,/!]+");

    public static String stringOrNULL(Object o)
    {
        return null == o ? "NULL" : o.toString();
    }

    public static String stringOrNULL(String s)
    {
        return null == s ? "NULL" : s;
    }

    public static String valueOrNULL(Map<String, Object> map, String key)
    {
        return stringOrNULL(map.getOrDefault(key, "NULL"));
    }

    public static Long longOrNULL(Map<String, Object> map, String key)
    {
        Object l = map.get(key);

        return null == l ? null : Long.parseLong(l.toString());
    }

    public static Double doubleOrNULL(Map<String, Object> map, String key)
    {
        Object d = map.get(key);

        return null == d ? null : Double.parseDouble(d.toString());
    }

    public static Date dateOrNULL(Map<String, Object> map, String key)
    {
        Object d = map.get(key);

        switch (d.getClass().getName()) {
            case "java.util.Date":
                return (Date) d;
            case "java.time.LocalDate":
                LocalDate localDate = (LocalDate) d;
                return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            default:
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return formatter.parse(d.toString());
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public static boolean isValidFieldName(String name, Class<?> clazz)
    {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().toUpperCase().equals(name))
                return true;
        }

        return false;
    }

    public static boolean isValidValue(String value)
    {
        return null != value && pattern.matcher(value).matches();
    }
}
