package tutorial.core.services.exceptions;

/**
 * Created by sletras on 21/01/2016.
 */
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(Throwable cause){
        super(cause);
    }

    public AccountExistsException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountExistsException(String message){
        super(message);
    }

    public AccountExistsException(){
        
    }
}
