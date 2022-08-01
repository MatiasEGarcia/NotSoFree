package com.NotSoFree.web;

import com.NotSoFree.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/categoryC")
public class CategoryC {
    
    @Autowired
    public CategoryService categoryService;
    
    
    
}
