package by.service;

import by.entity.UserListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidation implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserListForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserListForm userForm = (UserListForm) o;
        //проверка на пустой user
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userForProject", "validationErrorEmptyDeveloper");

    }
}