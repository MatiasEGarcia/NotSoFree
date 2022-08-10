package com.NotSoFree.web;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/userC")
public class UserC {
    
    @Autowired
    private UserDService userDService;
    
    
    @GetMapping(value="/login")
    public String login(){
        return "login";
    }
    
    @GetMapping(value="/profilePage")
    public String profilePage(Model model, Principal principal) throws UserDNotFoundByUsername{
         log.info("profilePage handler");
        
        UserD userD=userDService.findUserDByUsername("error");
        
        model.addAttribute("actualUser", userD);
        return "userProfile";
    }
    
}
