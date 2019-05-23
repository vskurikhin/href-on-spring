package su.svn.href.exceptions;

public class BadValueForFieldException extends HrefException
{
    public BadValueForFieldException(Class<?> clazz, String message)
    {
        super("Bad value for field", clazz, message);
    }
}
