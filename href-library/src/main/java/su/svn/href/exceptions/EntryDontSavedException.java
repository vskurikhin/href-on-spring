package su.svn.href.exceptions;

public class EntryDontSavedException extends HrefException {
    /* None */
    public EntryDontSavedException(Class<?> clazz, String message)
    {
        super("Entry don't saved", clazz, message);
    }
}
