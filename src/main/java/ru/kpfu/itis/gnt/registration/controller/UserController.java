package ru.kpfu.itis.gnt.registration.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.gnt.registration.constants.PathConstants;
import ru.kpfu.itis.gnt.registration.constants.ViewNameConstants;
import ru.kpfu.itis.gnt.registration.dto.LoginDto;
import ru.kpfu.itis.gnt.registration.dto.UserDto;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.services.UserService;
import ru.kpfu.itis.gnt.registration.utils.validation.PasswordsEqual;

import java.util.Objects;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class UserController {
    private static final String USER_LOGIN_MODEL_KEY = "userLoginDto";
    private final UserService service;

    private static final String USER_REGISTER_MODEL_KEY = "userRegisterDto";

    private static final String ERROR_MESSAGE_KEY = "messageError";
    private static final String PASSWORD_REPEAT_ERROR_KEY = "passwordRepeatError";

    private static final String LOGIN_ACTION_KEY = "action";


    @GetMapping(PathConstants.User.REGISTER)
    public String newUser(ModelMap map, HttpServletRequest request) {
        request.getSession().setAttribute("locale", "ru_RU");
        map.put(USER_REGISTER_MODEL_KEY, new UserDto());
        return ViewNameConstants.User.REGISTER;
    }

    @PostMapping(PathConstants.User.REGISTER)
    public String newUserValidator(RedirectAttributes redirectAttributes,
                                   @Valid @ModelAttribute(USER_REGISTER_MODEL_KEY) UserDto userDto,
                                   BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
                // Не знаю, как иначе передать ошибку из кастомной аннотации всего класса представлению jsp.
                // Вместо всех сообщений об ошибках, достаю только ту, которая соответсвует моей аннотации.
                violations.stream().filter(
                                error -> PasswordsEqual.class.toString().contains(error.getConstraintDescriptor().getAnnotation().annotationType().getName()))
                        .findFirst()
                        .ifPresent(
                                userPasswordError -> map.put(PASSWORD_REPEAT_ERROR_KEY, userPasswordError.getMessage())
                        );
            }
            return ViewNameConstants.User.REGISTER;
        } else {
            try {
                service.registerUser(userDto);
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticles").build();
            } catch (DBException ex) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, ex.getMessage());
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("UC#newUser").build();
            }
        }
    }

    @GetMapping(PathConstants.User.LOGOUT)
    public void logout() {
    }


    @GetMapping(PathConstants.User.LOGIN)
    @PreAuthorize("isAnonymous()")
    public String login(@RequestParam(required = false) String error,
                        HttpServletRequest request, ModelMap map) {
        map.put(LOGIN_ACTION_KEY, request.getServletContext().getContextPath() + PathConstants.User.LOGIN);
        map.put("message", error);
        return ViewNameConstants.User.LOGIN;
    }

}
