package tutorial.core.services.exceptions;

import tutorial.core.entities.Account;

/**
 * Created by sletras on 21/01/2016.
 */
public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException(Throwable cause){
        super(cause);
    }

    public AccountDoesNotExistException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountDoesNotExistException(String message){
        super(message);
    }

    public AccountDoesNotExistException(){

    }
}
