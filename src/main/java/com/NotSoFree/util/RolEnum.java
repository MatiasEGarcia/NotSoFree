package com.NotSoFree.util;

public enum RolEnum {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");
    
    private String name;
    
    private RolEnum(String name){
        this.name= name;
    }
    
    public String getName(){
        return name;
    }
}
