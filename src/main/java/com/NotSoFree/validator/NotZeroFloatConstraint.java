package com.NotSoFree.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotZeroFloatConstraint implements ConstraintValidator <NotZero,Float>{

    @Override
    public boolean isValid(Float input, ConstraintValidatorContext cvc) {
        
        return !(input==0.0);
    }
    
}
