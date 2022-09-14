package com.NotSoFree.exception;


public class FavoriteNotFoundById extends Exception {
    private static final long serialVersionUID = 1L;
    
    public FavoriteNotFoundById(){
        super("The Favorite with that id was not found");
    }
    
    public FavoriteNotFoundById(Long id){
        super("The Favorite with id : " + id + " was not found");
    }
}
