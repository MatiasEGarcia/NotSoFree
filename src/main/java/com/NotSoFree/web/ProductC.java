package com.NotSoFree.web;

import com.NotSoFree.domain.Product;
import com.NotSoFree.exception.ProductNotFoundById;
import com.NotSoFree.service.ProductService;
import java.io.IOException;
import javax.servlet.annotation.HandlesTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/productC")
public class ProductC {
    
    @Autowired
    private ProductService productService;
    
    
    @PostMapping(value="/deleteProd")
    public String deleteProduct(@RequestParam(name = "idProduct") String idProduct,RedirectAttributes redirectAttrs){
        log.info("deleteProduct handler");
        
        Product product= new Product(Long.parseLong(idProduct));
        
        productService.removeProduct(product);
        
        redirectAttrs
            .addFlashAttribute("message", "Product deleted successfully")
            .addFlashAttribute("class", "success");
        
        return "redirect:/";
    }
    
    @GetMapping(value="/savePage")
    public String savePage(Model model){
        log.info("savePage handler");
        
        Product product= new Product();
        
        model.addAttribute("product", product);
        model.addAttribute("formAction", "/productC/saveProd");
        return "saveEditProdP";
    }
    
    @GetMapping(value="/editPage/{idProduct}")
    public String editPage(Product product,Model model) throws ProductNotFoundById{
        log.info("editPage handler");
        
        Product productFound = productService.findProduct(product.getIdProduct());
        model.addAttribute("product", productFound);
        model.addAttribute("formAction", "/productC/editProd");
        
        return "saveEditProdP";
    }
    
    @PostMapping(value="/editProd")
    public String editProd(Model model,Product product,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs){
        log.info("editProd handler");
        
        try {
            productService.saveProduct(product, image);
        }catch (IOException ex) {
            log.info("There was some error with the image: {}",ex);
        }catch(Exception ex){
             log.info("There was some error: {}",ex);
        }
        
        redirectAttrs
            .addFlashAttribute("message", "Product edited successfully")
            .addFlashAttribute("class", "success")
            .addAttribute("idProduct", product.getIdProduct());
        
        return "redirect:/productC/editPage/{idProduct}";
    }
    
    
    @PostMapping(value="/saveProd")
    public String saveProduct(Product product,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs){
         log.info("saveProd handler");
         
        try {
            productService.saveProduct(product, image);
        }catch (IOException ex) {
            log.info("There was some error with the image: {}",ex);
        }catch(Exception ex){
             log.info("There was some error: {}",ex);
        }
         
        redirectAttrs
            .addFlashAttribute("message", "Product create successfully")
            .addFlashAttribute("class", "success");

        return "redirect:/productC/savePage";
}
    
}
