
package com.NotSoFree.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

//To edit an user without the password
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEDto extends PersonDto{
    
    private Long id;
    private String username;
    private String image;
}
