
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
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="roles", schema = "notsofree")
public class Rol implements Serializable{
    
       private static final long serialVersionUID=1L;
     
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name="id_rol")
     private Long idRol;
     
     @NotBlank(message="Rol name can't be blank")
     @Column(name="name")
     private String name;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="user")
     private UserD user;
     
     public Rol(){
        
    }
    
    public Rol(Long idRol){
        this.idRol=idRol;
    }
    
    public Rol(String name){
        this.name=name;
    }
    
    public Rol(String name,UserD user){
        this.name=name;
        this.user=user;
    }
}
