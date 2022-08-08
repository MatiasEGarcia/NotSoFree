
package com.NotSoFree.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="roles", schema = "notanlibre")
public class Rol {
    
       private static final long serialVersionUID=1L;
     
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name="id_rol")
     private Long idRol;
     
     @NotBlank(message="Rol name can't be blank")
     private String name;
}
