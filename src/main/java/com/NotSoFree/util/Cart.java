
package com.NotSoFree.util;
import lombok.Data;

@Data
public class Cart {
    
    private boolean inCart = false; //To know if it is in the cart
    
    private int quantity;
    
    private boolean notEnoughStock = false; // if this is true, it means that there is not enough stock in the database for your purchase
    
}
