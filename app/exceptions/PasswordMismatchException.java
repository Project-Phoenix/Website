package exceptions;

public class PasswordMismatchException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PasswordMismatchException()
    {
        super("Die Passwörter stimmen nicht überein!");       
    }
    
}
