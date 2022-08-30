package com.NotSoFree.domain;

import com.NotSoFree.validator.NotZero;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "products", schema = "notanlibre")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    
    @NotBlank(message="Product name can't be blank")
    @Column(name="name")
    private String name;
    
    @NotNull(message="Product stock can't be null")
    @NotZero
    @Column(name="stock")
    private int stock;
    
    @NotBlank(message="Product mark can't be empty")
    @Column(name="mark")
    private String mark;
    
    @NotNull(message="Product price can't be null")
    @NotZero
    @Column(name="price")
    private float price;
   
    @Column(name = "image", columnDefinition = "BLOB")
    private String image;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "product") 
    private List<ProdCate> prodCate;

    public Product(){
        
    }
    
    public Product(Long idProduct){
        this.idProduct=idProduct;
    }
    
}
