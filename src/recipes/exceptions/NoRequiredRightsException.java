package recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No required rights to action")
public class NoRequiredRightsException extends RuntimeException{

    public NoRequiredRightsException() {
        super();
    }
}
