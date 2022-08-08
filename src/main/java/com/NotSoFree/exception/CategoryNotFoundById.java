
package com.NotSoFree.exception;


public class CategoryNotFoundById extends Exception{
    
     private static final long serialVersionUID = 1L;
     
     public CategoryNotFoundById (){
         super("The category with that id was not found");
     }
     
     public CategoryNotFoundById(Long id){
         super("The category with id : " + id + " was not found");
     }
}
