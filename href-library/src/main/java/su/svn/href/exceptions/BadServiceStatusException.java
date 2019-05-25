package su.svn.href.exceptions;

public class BadServiceStatusException extends HrefException
{
    public BadServiceStatusException(Class<?> clazz, String message)
    {
        super("Bad service status", clazz, message);
    }
}
