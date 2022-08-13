package com.NotSoFree.validator;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotSameUsernameStringConstraint implements ConstraintValidator<NotSameUsername, String> {

    @Autowired
    private UserDService userDService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext cvc) {
        UserD user = null;

        if (username != null && !username.contains("")) {
            try {
               user= userDService.findUserDByUsername(username);
            } catch (UserDNotFoundByUsername ex) {
                ex.printStackTrace();
            }
        }

        return user == null;
    }

}
