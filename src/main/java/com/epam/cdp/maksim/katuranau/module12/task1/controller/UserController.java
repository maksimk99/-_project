package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.exception.security.UserAlreadyExistException;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserAuthorizationDto;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserRegistrationDto;
import com.epam.cdp.maksim.katuranau.module12.task1.service.RoleService;
import com.epam.cdp.maksim.katuranau.module12.task1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * The type User controller.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private RoleService roleService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     * @param roleService the role service
     */
    @Autowired
    public UserController(final UserService userService, final RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Gets registration.
     *
     * @param model the model
     * @return the registration
     */
    @GetMapping("/register")
    public String getRegistration(final Model model) {
        LOGGER.info("getRegistration method was invoked");
        model.addAttribute("selectableRoles", roleService.getRoles());
        model.addAttribute("user", new UserRegistrationDto());
        return "registerPage";
    }

    /**
     * Register user string.
     *
     * @param user              the user
     * @param validationResults the validation results
     * @param model             the model
     * @return the string
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid final UserRegistrationDto user,
                               final BindingResult validationResults, final Model model) {
        LOGGER.info("registerUser method [{}]", user);
        if (validationResults.hasErrors()) {
            LOGGER.warn("registerUser method has validation errors");
            setModelAttributeUserRegisterError(model, "Validation exception");
            return "registerPage";
        }
        try {
            String password = user.getPassword();
            userService.save(user);
            userService.autoLogin(user.getLogin(), password);
        } catch (UserAlreadyExistException ex) {
            LOGGER.warn("can't register user, user with username = \"{}\" already exist", user.getLogin());
            setModelAttributeUserRegisterError(model, ex.getMessage());
            return "registerPage";
        }
        return "redirect:/goods";
    }

    /**
     * Show authorization page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/login")
    public String showAuthorizationPage(final Model model) {
        LOGGER.info("showAuthorizationPage method was invoked");
        model.addAttribute("user", new UserAuthorizationDto());
        return "loginPage";
    }

    private void setModelAttributeUserRegisterError(final Model model, final String errorMessage) {
        model.addAttribute("user", new UserRegistrationDto());
        model.addAttribute("selectableRoles", roleService.getRoles());
        model.addAttribute("registrationErrorMessage", errorMessage);
    }
}
