package com.NotSoFree.domain;

import com.NotSoFree.dto.ProductDto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;


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
      
    @Column(name="name")
    private String name;
    
    @Column(name="stock")
    private int stock;
    
    @Column(name="mark")
    private String mark;
    
    @Column(name="price")
    private float price;
   
    @Column(name = "image", columnDefinition = "BLOB")
    private String image;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE, mappedBy = "product") 
    private List<ProdCate> prodCate;

    public Product(){
        
    }
    
    public Product(Long idProduct){
        this.idProduct=idProduct;
    }
    
    public Product(ProductDto dto){
        this.idProduct= dto.getIdProduct();
        this.name= dto.getName();
        this.stock= dto.getStock();
        this.mark= dto.getMark();
        this.price=dto.getPrice();
        this.image=dto.getImage();
    }
    
}
