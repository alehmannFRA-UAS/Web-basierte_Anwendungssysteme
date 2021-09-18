package edu.fra.uas.security.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.fra.uas.security.model.UserCreateForm;
import edu.fra.uas.security.service.user.UserService;

@Component
public class UserCreateFormValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(UserCreateFormValidator.class);
    
    private UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.debug("Validating {}", target);
        UserCreateForm form = (UserCreateForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password", "different passwords entered!");
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.existsByNickname(form.getNickname())) {
            errors.reject("nickname", "user with this nickname already exists!!!");
        }
        else
        if (userService.existsByEmail(form.getEmail())) {
            errors.reject("email", "user with this email already exists!!!");
        }
    }
    
}
