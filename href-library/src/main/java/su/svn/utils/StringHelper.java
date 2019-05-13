package su.svn.utils;

import java.util.Date;
import java.util.Map;

public class StringHelper
{
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

        return d.getClass().getName().equals(Date.class.getName()) ? (Date) d : null;
    }
}
