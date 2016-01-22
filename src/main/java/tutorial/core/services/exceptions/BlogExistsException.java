package tutorial.core.services.exceptions;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogExistsException extends RuntimeException {
    public BlogExistsException(Throwable cause){
        super(cause);
    }

    public BlogExistsException(String message, Throwable cause){
        super(message, cause);
    }

    public BlogExistsException(String message){
        super(message);
    }

    public BlogExistsException(){

    }
}
