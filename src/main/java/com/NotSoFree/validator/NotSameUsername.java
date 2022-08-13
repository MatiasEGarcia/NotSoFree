package com.NotSoFree.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {NotSameUsernameStringConstraint.class})
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface NotSameUsername {

    String message() default "Username alredy in use";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
