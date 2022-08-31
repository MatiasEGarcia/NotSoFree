
package com.NotSoFree.dto;

import com.NotSoFree.validator.NotZero;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto implements Serializable {
    
    @NotNull(message="Product id can't be null")
    private Long idProduct;
    
    @NotBlank(message="Product name can't be blank")  
    private String name;
    
    @NotNull(message="Product stock can't be null")
    @NotZero
    private int stock;
    
    @NotBlank(message="Product mark can't be empty")
    private String mark;
    
    @NotNull(message="Product price can't be null")
    @NotZero
    private float price;
   
    private String image;
    
    @NotNull(message="The product must have at least 1 category")
    private List<String> newCategories;
}
