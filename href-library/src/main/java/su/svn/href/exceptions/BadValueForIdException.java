package su.svn.href.exceptions;

public class BadValueForIdException extends HrefException
{
    public BadValueForIdException(Class<?> clazz, String message)
    {
        super("Bad value for id", clazz, message);
    }
}
