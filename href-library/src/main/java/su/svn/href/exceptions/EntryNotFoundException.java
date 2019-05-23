package su.svn.href.exceptions;

public class EntryNotFoundException extends HrefException
{
    public EntryNotFoundException(Class<?> clazz, String message)
    {
        super("Entry not found", clazz, message);
    }
}
