package com.NotSoFree.util;
import com.NotSoFree.domain.Person;
import org.springframework.security.core.context.SecurityContextHolder;
//This class is to evaluate whether or not the user has all the correct data to make a purchase.

public class BuyValidation {
    
    public boolean addresValidation(){
        CustomUserDetails loggedUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person= loggedUser.getPerson();
        return !person.getAddress().isBlank();
    }

}
