package com.NotSoFree.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "prodcate", schema = "notanlibre")
public class ProdCate implements Serializable {
    
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_prodcate")
    private Long idProdCate; 
    
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product")
    private Product product;
    
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category")
    private Category category;
    
    public ProdCate(){
        
    }
    
    public ProdCate(Product product,Category category){
        this.product = product;
        this.category = category;
    }
    
}
