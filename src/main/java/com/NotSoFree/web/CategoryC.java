package com.NotSoFree.web;

import com.NotSoFree.domain.Category;
import com.NotSoFree.exception.CategoryNotFoundById;
import com.NotSoFree.service.CategoryService;
import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping(value="/admin/listAllPage")
    public String listAllCategories(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField",defaultValue = "idCategory") String sortField,
            @RequestParam(name = "sortDir",defaultValue = "asc") String sortDir, 
            @RequestParam(name = "pageSize",defaultValue = "10") String pageSize) throws Exception{
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
    
    @PostMapping(value="/admin/deleteCateg")
    public String deleteCategory(@RequestParam(name = "idCategory") String idCategory,
            RedirectAttributes redirectAttrs) throws Exception{
        log.info("delete handler");
        categoryService.delete(Long.parseLong(idCategory));
        redirectAttrs
            .addFlashAttribute("message", "Category deleted successfully")
            .addFlashAttribute("class", "success");
        
        return "redirect:/categoryC/admin/listAllPage";
    }
    
    @GetMapping(value="/admin/editPage/{idCategory}")
    public String editPage(Category category,Model model) throws CategoryNotFoundById{
        log.info("editPage handler");
        
        model.addAttribute("category", categoryService.findCategory(category.getIdCategory() ) );
        model.addAttribute("formAction", "/categoryC/admin/editCateg");
        return "saveEditCategP";
    }    
    
    @GetMapping(value = "/admin/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");

        Category category = new Category();

        model.addAttribute("category", category);
        model.addAttribute("formAction", "/categoryC/admin/saveCateg");
        return "saveEditCategP";
    }

    @PostMapping(value="/admin/editCateg")
    public String editCategory(Model model,@Valid Category category,
             BindingResult result,
            @RequestParam(name = "flexRadio", required = true) byte state,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws IOException, Exception{
        log.info("editCategory handler");
        
         if (result.hasErrors()) {
            model.addAttribute("category",category );
            model.addAttribute("formAction", "/categoryC/admin/editCateg");
            return "saveEditCategP";
        }
            category.setState(state);
            categoryService.save(category, image);
        
        
         redirectAttrs
                .addFlashAttribute("message", "Category edited successfully")
                .addFlashAttribute("class", "success")
                .addAttribute("idCategory", category.getIdCategory());
        
        return "redirect:/categoryC/admin/editPage/{idCategory}";
    }
    
    @PostMapping(value = "/admin/saveCateg")
    public String saveCategory(Model model,@Valid Category category,
            BindingResult result,
            @RequestParam(name = "flexRadio", required = true) byte state,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception {
        log.info("saveCategory handler");

         if (result.hasErrors()) {
            model.addAttribute("category",category );
            model.addAttribute("formAction", "/categoryC/admin/saveCateg");
            return "saveEditCategP";
        }
         
         
            category.setState(state);
            categoryService.save(category, image);
       

        redirectAttrs
                .addFlashAttribute("message", "Category create successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/categoryC/admin/savePage";
    }

}
