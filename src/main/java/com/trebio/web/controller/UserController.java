package com.trebio.web.controller;

import com.trebio.context.account.exception.AccountAlreadyExistException;
import com.trebio.context.account.model.Account;
import com.trebio.context.account.service.UserService;
import com.trebio.context.account.service.dto.RegisterUser;
import com.trebio.web.form.user.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("user/register", "registration", new Registration());
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute @Valid Registration registration, BindingResult bindingResult) throws AccountAlreadyExistException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/register");
        }
        Account account = userService.registerNewUserAccount(
                new RegisterUser(
                        registration.email,
                        registration.password
                )
        );

        return new ModelAndView("user/registration-success", "account", account);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("user/login");


        return model;
    }
}
