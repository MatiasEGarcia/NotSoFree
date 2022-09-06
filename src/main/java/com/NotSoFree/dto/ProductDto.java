
package com.NotSoFree.dto;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Product;
import com.NotSoFree.util.Cart;
import com.NotSoFree.validator.NotZero;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDto extends Cart implements Serializable {
    
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
    
    private List<String> newCategories;
    
    private List<Category> oldCategories;//To know if the product has categories
    
    public ProductDto(){
        
    }
    
    public ProductDto(Product product,List<Category> oldCategories){
        this.idProduct= product.getIdProduct();
        this.name=product.getName();
        this.stock= product.getStock();
        this.mark= product.getMark();
        this.price= product.getPrice();
        this.image= product.getImage();
        this.oldCategories=oldCategories;
    }
    
    public ProductDto(Product product){
        this.idProduct= product.getIdProduct();
        this.name=product.getName();
        this.stock= product.getStock();
        this.mark= product.getMark();
        this.price= product.getPrice();
        this.image= product.getImage();
    }
    
    
}
