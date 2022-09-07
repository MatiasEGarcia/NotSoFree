package com.NotSoFree.web;

import com.NotSoFree.dto.ProductDto;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/cartC") 
public class CartC {

    @PostMapping(value = "/add")
    public String add(ProductDto productDto,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs) {
        log.info("add handler");

        List<ProductDto> cartList;
        HttpSession session = request.getSession(false);
        cartList = (List<ProductDto>) session.getAttribute("cartList");
        
        if (cartList != null) {
            cartList.add(productDto);
        }else{
            cartList = new ArrayList<>();
            cartList.add(productDto);
        }
        session.setAttribute("cartList", cartList);
        String url = request.getHeader("referer");

        redirectAttrs
                .addFlashAttribute("message", "Product added to Cart")
                .addFlashAttribute("class", "success");
        return "redirect:" + url;
    }
    
    
    @GetMapping(value = "/cartList")
    public String cartList(Model model,HttpSession session){
        model.addAttribute("products", session.getAttribute("cartList"));
        return "cartList";
    }
    
    
    
    //This handler is for Authenticated user
    @PostMapping(value = "UserPurchase")
    public String userPurchase() {
        //tengo que validar usando el buyValidation

        return null;

    }

}
