
package com.NotSoFree.domain;

import com.NotSoFree.dto.UserCDto;
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
    
    @OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="person")
    private Person person;
    
    @Column(name="user_name")
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="image")
    private String image;
    
    @Column(name="state")
    private byte state;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<Rol> roles;
    
    public UserD(){
        
    }
    
    public UserD(UserCDto user){
        Person person= new Person();
        person.setNames(user.getNames());
        person.setSurnames(user.getSurnames());
        person.setEmail(user.getEmail());
        person.setPhone(user.getPhone());
        person.setEmail(user.getSurnames());
        setPerson(person);
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
