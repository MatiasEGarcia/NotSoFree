package com.NotSoFree.validator;
//To validate that the old password is correct

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = {NotOldPasswordConstraint.class})
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface NotOldPassword {
    
    String message() default "Password incorrect";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
