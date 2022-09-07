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
import org.springframework.web.bind.annotation.PathVariable;
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
        } else {
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
    public String cartList(Model model, HttpSession session) {
        log.info("cartList handler");
        float totalAmount= 0;
         List<ProductDto> listProd = (List<ProductDto>) session.getAttribute("cartList");
         if(!listProd.isEmpty()){
             for(ProductDto p : listProd){
                totalAmount += p.getPrice()*p.getQuantity();
             }
             
             model.addAttribute("products",listProd );
             model.addAttribute("totalAmount",totalAmount);
         }
        return "cartList";
    }

    @GetMapping(value = "/delete/{idProduct}")
    public String delete(@PathVariable Long idProduct, HttpSession session, RedirectAttributes redirectAttrs) {
        log.info("cartList handler");
        List<ProductDto> listProd = (List<ProductDto>) session.getAttribute("cartList");
        for (int i = 0; i < listProd.size(); i++) {
            if (listProd.get(i).getIdProduct().equals(idProduct)) {
                listProd.remove(i);
                break;
            }
        }
        session.setAttribute("cartList", listProd);
        redirectAttrs
                .addFlashAttribute("message", "Product deleted successfully from cart")
                .addFlashAttribute("class", "success");
        return "redirect:/cartC/cartList";
    }

    //This handler is for Authenticated user
    @PostMapping(value = "UserPurchase")
    public String userPurchase() {
        //tengo que validar usando el buyValidation

        return null;

    }

}
