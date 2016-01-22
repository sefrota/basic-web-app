package tutorial.core.services.exceptions;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogDoesNotExistException extends RuntimeException {
    public BlogDoesNotExistException(Throwable cause){
        super(cause);
    }

    public BlogDoesNotExistException(String message, Throwable cause){
        super(message, cause);
    }

    public BlogDoesNotExistException(String message){
        super(message);
    }

    public BlogDoesNotExistException(){

    }
}
