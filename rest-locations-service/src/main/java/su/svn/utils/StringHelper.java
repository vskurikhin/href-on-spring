package su.svn.utils;

import java.util.ArrayList;

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
}
