package com.NotSoFree.exception;

public class ProductNotFoundById extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ProductNotFoundById(){
        super("The product with that id was not found");
    }
    
    public ProductNotFoundById(Long id){
        super("The product with id : " + id + " was not found");
    }
}
