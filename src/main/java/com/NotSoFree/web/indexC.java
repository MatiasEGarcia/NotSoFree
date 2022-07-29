package com.NotSoFree.web;

import com.NotSoFree.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class indexC {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String index(Model model){
        log.info("index handler");
        
        model.addAttribute("products", productService.listProducts());
        return "index";
    }
}
