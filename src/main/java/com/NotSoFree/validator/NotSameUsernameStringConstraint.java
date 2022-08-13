package com.NotSoFree.validator;

import com.NotSoFree.dto.UserDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotSameUsernameStringConstraint implements ConstraintValidator<NotSameUsername, String> {

    @Autowired
    private UserDService userDService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext cvc) {
        UserDto user = null;

        if (username != null && !username.contains("")) {
            try {
               user= userDService.findUserByUsername(username);
            } catch (UserDNotFoundByUsername ex) {
                ex.printStackTrace();
            }
        }

        return user == null;
    }

}
