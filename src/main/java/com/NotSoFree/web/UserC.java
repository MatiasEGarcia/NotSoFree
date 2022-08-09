package com.NotSoFree.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserC {
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
