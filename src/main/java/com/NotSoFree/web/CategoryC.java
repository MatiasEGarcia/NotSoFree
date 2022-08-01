package com.NotSoFree.web;

import com.NotSoFree.domain.Category;
import com.NotSoFree.service.CategoryService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String listAllCategories(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField",defaultValue = "idCategory") String sortField,
            @RequestParam(name = "sortDir",defaultValue = "asc") String sortDir, 
            @RequestParam(name = "pageSize",defaultValue = "10") String pageSize){
        log.info("listAllCategories handler");
        
        int pageNoInt= Integer.parseInt(pageNo);
        Page<Category> pageCateg=categoryService.findPaginated(pageNoInt,Integer.parseInt(pageSize),sortField, sortDir);
        
        
        model.addAttribute("categories", pageCateg.getContent());
        model.addAttribute("totalPages", pageCateg.getTotalPages());
        model.addAttribute("totalItems", pageCateg.getTotalElements());
        model.addAttribute("actualPage",pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        return "listAllCategories";
    }
    
    @PostMapping(value="/deleteCateg")
    public String deleteCategory(@RequestParam(name = "idCategory") String idCategory,RedirectAttributes redirectAttrs){
        log.info("delete handler");
        categoryService.delete(new Category(Long.parseLong(idCategory)));
        redirectAttrs
            .addFlashAttribute("message", "Category deleted successfully")
            .addFlashAttribute("class", "success");
        
        return "redirect:/categoryC/listAllPage";
    }
    
    @GetMapping(value="/editPage/{idCategory}")
    public String editPage(Category category,Model model){
        log.info("editPage handler");
        
        model.addAttribute("category", categoryService.findCategory(category));
        model.addAttribute("formAction", "/categoryC/editCateg");
        return "saveEditCategP";
    }    
    
    @GetMapping(value = "/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");

        Category category = new Category();

        model.addAttribute("category", category);
        model.addAttribute("formAction", "/categoryC/saveCateg");
        return "saveEditCategP";
    }

    @PostMapping(value="/editCateg")
    public String editCategory(Model model,Category category,
            @RequestParam(name = "flexRadio", required = true) byte state,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs){
        log.info("editCategory handler");
        
        try {
            category.setState(state);
            categoryService.save(category, image);
        } catch (IOException ex) {
            log.info("There was some error with the image: {}", ex);
        } catch (Exception ex) {
            log.info("There was some error: {}", ex);
        }
        
         redirectAttrs
                .addFlashAttribute("message", "Category edited successfully")
                .addFlashAttribute("class", "success")
                .addAttribute("idCategory", category.getIdCategory());
        
        return "redirect:/categoryC/editPage/{idCategory}";
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
