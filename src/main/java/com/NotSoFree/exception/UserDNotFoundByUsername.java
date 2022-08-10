
package com.NotSoFree.exception;

public class UserDNotFoundByUsername extends Exception{
    
    private static final long serialVersionUID = 1L;
     
     public UserDNotFoundByUsername (){
         super("An error occurred searching for the user");
     }
     
     public UserDNotFoundByUsername(String message){
         super("An error occurred looking for the user : " + message);
     }
}
