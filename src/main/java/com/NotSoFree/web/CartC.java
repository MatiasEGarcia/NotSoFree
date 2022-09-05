package com.NotSoFree.web;

import com.NotSoFree.domain.Product;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.util.Cart;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/cartC") 
public class CartC {

    @PostMapping(value = "/add")
    public String add(@RequestParam(name = "idProduct") String idProduct,
            @RequestParam(name = "amountSelect") String amount,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs) {
        log.info("add handler");

        List<Cart> cartList;
        HttpSession session = request.getSession(false);
        cartList = (List<Cart>) session.getAttribute("cartList");
        
        if (cartList != null) {
            cartList.add(new Cart(Long.parseLong(idProduct), Integer.parseInt(amount)));
        }else{
            cartList = new ArrayList<>();
            cartList.add(new Cart(Long.parseLong(idProduct), Integer.parseInt(amount)));
        }
        session.setAttribute("cartList", cartList);
        String url = request.getHeader("referer");

        redirectAttrs
                .addFlashAttribute("message", "Product added to Cart")
                .addFlashAttribute("class", "success");
        return "redirect:" + url;
    }
    //This handler is for Authenticated user
    @PostMapping(value = "UserPurchase")
    public String userPurchase() {
        //tengo que validar usando el buyValidation

        return null;

    }

}
