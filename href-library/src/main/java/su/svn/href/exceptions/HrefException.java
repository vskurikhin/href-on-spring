package su.svn.href.exceptions;

public class HrefException extends RuntimeException
{
    public HrefException(String message)
    {
        super(message);
    }

    public HrefException(String name, Class<?> clazz, String message)
    {
        super(name + " for class: " + clazz.getName() + ' ' + message);
    }
}
