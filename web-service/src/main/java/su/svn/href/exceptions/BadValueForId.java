package su.svn.href.exceptions;

import java.util.Objects;

public class BadValueForId extends RuntimeException
{
    private static <T> String messageForId(T id)
    {
       return Objects.isNull(id) ? "Bad null of id" : "Bad value for id: " + id.toString();
    }

    public BadValueForId(Long id, Class<?> c)
    {
        super("Ok");
    }
//    public BadValueForId(Long id, Class<?> c)
//    {
//        super(messageForId(id) + " for object of class: " + c.getName());
//    }
//
//    public BadValueForId(String id, Class<?> c)
//    {
//        super(messageForId(id) + " for object of class: " + c.getName());
//    }
}
