package com.NotSoFree.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class indexC {
    
    @GetMapping("/")
    public String index(Model model){
        log.info("index handler");
        
        model.addAttribute("Message", "Welcome to my app");
        return "index";
    }
}
