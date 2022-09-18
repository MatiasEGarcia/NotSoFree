package com.NotSoFree.exception;

public class PersonNotFoundById  extends Exception {
    private static final long serialVersionUID = 1L;
    
    public PersonNotFoundById(){
        super("The person with that id was not found");
    }
    
    public PersonNotFoundById(Long id){
        super("The person with id : " + id + " was not found");
    }
}
