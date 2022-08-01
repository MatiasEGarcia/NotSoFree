package com.NotSoFree.web;

import com.NotSoFree.domain.Category;
import com.NotSoFree.service.CategoryService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/categoryC")
public class CategoryC {

    @Autowired
    public CategoryService categoryService;

    @GetMapping(value="/listAllPage")
    public String listAllCategories(Model model){
        log.info("listAllCategories handler");
        
        model.addAttribute("categories", categoryService.listCategories() );
        return "listAllCategories";
    }
    
    
    
    @GetMapping(value = "/savePage")
    public String savePageCategory(Model model) {
        log.info("savePageCategory handler");

        Category category = new Category();

        model.addAttribute("category", category);
        model.addAttribute("formAction", "/categoryC/saveCateg");
        return "saveEditCategP";
    }

    @PostMapping(value = "/saveCateg")
    public String saveCategory(Category category,
            @RequestParam(name = "flexRadio", required = true) byte state,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) {
        log.info("saveCategory handler");

        try {
            category.setState(state);
            categoryService.save(category, image);
        } catch (IOException ex) {
            log.info("There was some error with the image: {}", ex);
        } catch (Exception ex) {
            log.info("There was some error: {}", ex);
        }

        redirectAttrs
                .addFlashAttribute("message", "Category create successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/categoryC/savePage";
    }

}
