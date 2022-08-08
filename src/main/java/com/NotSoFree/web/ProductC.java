package com.NotSoFree.web;

import com.NotSoFree.domain.Product;
import com.NotSoFree.exception.ProductNotFoundById;
import com.NotSoFree.service.ProductService;
import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/productC")
public class ProductC {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");

        Product product = new Product();

        model.addAttribute("product", product);
        model.addAttribute("formAction", "/productC/saveProd");
        return "saveEditProdP";
    }

    @GetMapping(value = "/editPage/{idProduct}")
    public String editPage(Product product, Model model) throws ProductNotFoundById {
        log.info("editPage handler");

        Product productFound = productService.findProduct(product.getIdProduct());
        model.addAttribute("product", productFound);
        model.addAttribute("formAction", "/productC/editProd");

        return "saveEditProdP";
    }

    @PostMapping(value = "/deleteProd")
    public String deleteProduct(@RequestParam(name = "idProduct") String idProduct, RedirectAttributes redirectAttrs) throws ProductNotFoundById {
        log.info("deleteProduct handler");

        productService.removeProduct(Long.parseLong(idProduct));

        redirectAttrs
                .addFlashAttribute("message", "Product deleted successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/";
    }

    @PostMapping(value = "/editProd")
    public String editProd(Model model, @Valid Product product,
            BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws IOException {
        log.info("editProd handler");

        if (result.hasErrors()) {
            model.addAttribute("product",product );
            model.addAttribute("formAction", "/productC/editProd");
            return "saveEditProdP";
        }

        productService.saveProduct(product, image);

        redirectAttrs
                .addFlashAttribute("message", "Product edited successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/productC/editPage/{idProduct}";
    }

    @PostMapping(value = "/saveProd")
    public String saveProduct(Model model, @Valid Product product, 
            BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws IOException {
        log.info("saveProd handler");

        if (result.hasErrors()) {
            model.addAttribute("product",product );
            model.addAttribute("formAction", "/productC/saveProd");
            return "saveEditProdP";
        }

        productService.saveProduct(product, image);

        redirectAttrs
                .addFlashAttribute("message", "Product create successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/productC/savePage";
    }

}
