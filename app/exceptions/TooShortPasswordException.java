package exceptions;

public class TooShortPasswordException extends Exception
{    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TooShortPasswordException()
    {
        super("Das eingegebene Passwort ist zu kurz!");
    }   
}
