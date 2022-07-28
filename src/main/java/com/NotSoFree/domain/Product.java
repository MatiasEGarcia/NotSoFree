package com.NotSoFree.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "products", schema = "notanlibre")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    
    @NotNull
    @Column(name="name")
    private String name;
    
    @NotNull
    @Column(name="stock")
    private int stock;
    
    @NotNull
    @Column(name="mark")
    private String mark;
    
    @NotNull
    @Column(name="price")
    private float price;
   
    @Column(name = "image", columnDefinition = "BLOB")
    private String image;
    
}
