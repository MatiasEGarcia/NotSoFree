package com.NotSoFree.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotZeroIntConstraint implements ConstraintValidator <NotZero,Integer> {

    @Override
    public boolean isValid(Integer input, ConstraintValidatorContext cvc) {
        
        return !(input==0);
        
    }


    
}
