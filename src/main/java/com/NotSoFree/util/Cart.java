
package com.NotSoFree.util;
import lombok.Data;

@Data
public class Cart {
    
    private boolean inCart = false; //To know if it is in the cart
    
    private int quantity;
    
}
