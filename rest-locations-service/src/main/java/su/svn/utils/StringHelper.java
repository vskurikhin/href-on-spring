package su.svn.utils;

import java.math.BigInteger;
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

    public static BigInteger bigIntegerOrNULL(Map<String, Object> map, String key)
    {
        Object l = map.get(key);

        return null == l ? null : new BigInteger(l.toString());
    }
}
