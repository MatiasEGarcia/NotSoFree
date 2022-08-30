
package com.NotSoFree.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "categories", schema = "notanlibre")
public class Category implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;
    
    @NotBlank(message="Category name can't be blank")
    @Column(name="name")
    private String name;
    
    @NotNull(message="Category state can't be null")
    @Column(name="state")
    private byte state;
    
    @Column(name="image")
    private String image;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "category") 
    private List<ProdCate> prodCate;
    
    public Category (){
        
    }
    
    public Category(Long idCategory){
        this.idCategory = idCategory;
                
    }
    
    
}
