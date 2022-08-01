
package com.NotSoFree.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "categories", schema = "notanlibre")
public class Category implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;
    
    @NotNull
    @Column(name="name")
    private String name;
    
    @NotNull
    @Column(name="state")
    private byte state;
    
    @Column(name="image")
    private String image;
    
    public Category (){
        
    }
    
    public Category(Long idCategory){
        this.idCategory = idCategory;
                
    }
    
    
}
