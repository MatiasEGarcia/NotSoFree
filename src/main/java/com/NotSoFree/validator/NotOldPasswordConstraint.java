
package com.NotSoFree.validator;
import com.NotSoFree.util.BCPasswordEncoder;
import com.NotSoFree.util.CustomUserDetails;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class NotOldPasswordConstraint implements ConstraintValidator<NotOldPassword, String> {

    @Autowired
    private BCPasswordEncoder bcPasswordEncoder;
    
   
    @Override
    public boolean isValid(String oldPassword, ConstraintValidatorContext cvc) {
        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        CustomUserDetails loggedUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password= loggedUser.getPassword();
        return encoder.matches(oldPassword, password); //If they are not equal, it returns false.
    }
    
}
