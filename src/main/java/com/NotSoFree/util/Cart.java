
package com.NotSoFree.util;
import lombok.Data;

@Data
public class Cart {
    
    private Long idProduct;
    private int amount;
    
    public Cart(){   
    }
    public Cart(Long idProduct,int amount){
        this.amount= amount;
        this.idProduct = idProduct;
    }
}
