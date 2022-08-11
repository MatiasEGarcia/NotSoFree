
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="users" , schema = "notanlibre")
public class UserD implements Serializable{
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long idUser;
    

    @OneToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name="person")
    private Person person;
    
    
   @NotBlank(message="username can't be blank")
    @Column(name="user_name")
    private String username;
    
    @NotBlank(message="password can't be blank")
    @Column(name="password")
    private String password;
    
   
    @Column(name="image")
    private String image;
    
    @NotNull
    @Column(name="state")
    private byte state;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Rol> roles;
    
    
}
