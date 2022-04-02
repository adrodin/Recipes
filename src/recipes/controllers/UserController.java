package recipes.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import recipes.exceptions.UserAlreadyExistsException;
import recipes.models.User;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@RequestBody @Valid User user) {
        if (userService.isUserAlreadyExists(user)) {
            throw new UserAlreadyExistsException();
        } else {
            userService.save(user);
        }
    }

}
