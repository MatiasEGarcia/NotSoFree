package com.NotSoFree.web;

import com.NotSoFree.domain.Product;
import com.NotSoFree.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class IndexC {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String index(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField",defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir",defaultValue = "asc") String sortDir, 
            @RequestParam(name = "pageSize",defaultValue = "10") String pageSize
        ){
        log.info("index handler");
        
        int pageNoInt= Integer.parseInt(pageNo);
        
        Page<Product> pageProd=productService.findPaginated(pageNoInt,Integer.parseInt(pageSize),sortField, sortDir);
        
        model.addAttribute("products", pageProd.getContent());
        model.addAttribute("totalPages", pageProd.getTotalPages());
        model.addAttribute("totalItems", pageProd.getTotalElements());
        model.addAttribute("actualPage",pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "index";
    }
}
