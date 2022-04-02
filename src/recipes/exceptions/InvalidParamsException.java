package recipes.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Params are invalid")
public class InvalidParamsException extends RuntimeException {
    public InvalidParamsException() {
        super();
    }
}
