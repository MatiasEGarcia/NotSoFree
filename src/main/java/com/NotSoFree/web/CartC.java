package com.NotSoFree.web;


import com.NotSoFree.dto.PersonDto;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/cartC")
public class CartC {

    @Autowired
    private ProductService productService;

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
        float totalAmount = 0;
        List<ProductDto> listProd = (List<ProductDto>) session.getAttribute("cartList");
        if (!listProd.isEmpty()) {
            for (ProductDto p : listProd) {
                totalAmount += p.getPrice() * p.getQuantity();
            }
            model.addAttribute("products", listProd);
            model.addAttribute("totalAmount", totalAmount);
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
    @GetMapping(value = "/auth/userPurchase")
    public String userPurchase(HttpSession session,
            RedirectAttributes redirectAttrs) throws Exception {
        List<ProductDto> listProd = new ArrayList<>();
        List<ProductDto> listNoStock = new ArrayList<>();
        listProd = (List<ProductDto>) session.getAttribute("cartList");
        listNoStock = this.noStock(listProd, productService.findAllProductsById(listProd.stream().map(prod -> prod.getIdProduct()).collect(Collectors.toList())));
        if (!listNoStock.isEmpty()) {
            session.setAttribute("cartList", this.setNotEnoughStock(listNoStock, listProd));
            redirectAttrs
                    .addFlashAttribute("message", "Some of the products has no stock")
                    .addFlashAttribute("class", "danger");

            return "redirect:/cartC/cartList";
        }
        productService.updateProductsStock(listProd);
        session.removeAttribute("cartList");

        redirectAttrs
                .addFlashAttribute("message", "Your products were purchased, your purchase id is : x")
                .addFlashAttribute("class", "success");

        return "redirect:/";
    }

    @GetMapping(value = "/noUserData")
    public String noUserData(Model model) {
        model.addAttribute("personDto", new PersonDto());
        return "buyNoUser";
    }

    @PostMapping(value = "/noUserPurchase")
    public String noUserPurchase(Model model,
            @Valid PersonDto personDto,
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttrs) throws Exception {
        log.info("noUserPurchase handler");
        List<ProductDto> listProd = new ArrayList<>();
        List<ProductDto> listNoStock = new ArrayList<>();

        if (result.hasErrors()) {
            model.addAttribute("personDto", personDto);
            return "buyNoUser";
        }
        listProd = (List<ProductDto>) session.getAttribute("cartList");
        listNoStock = this.noStock(listProd, productService.findAllProductsById(listProd.stream().map(prod -> prod.getIdProduct()).collect(Collectors.toList())));
        if (!listNoStock.isEmpty()) {
            session.setAttribute("cartList", this.setNotEnoughStock(listNoStock, listProd));
            redirectAttrs
                    .addFlashAttribute("message", "Some of the products has no stock")
                    .addFlashAttribute("class", "danger");

            return "redirect:/cartC/cartList";
        }
        productService.updateProductsStock(listProd);
        session.removeAttribute("cartList");

        redirectAttrs
                .addFlashAttribute("message", "Your products were purchased, your purchase id is : x")
                .addFlashAttribute("class", "success");

        return "redirect:/";
    }

    //This method is to find out if all products have stock
    public List<ProductDto> noStock(List<ProductDto> cartProds, List<ProductDto> prodsBdd) {
        List<ProductDto> listNoStock = new ArrayList<>();

        for (int i = 0; i < cartProds.size(); i++) {
            for (int j = 0; j < prodsBdd.size(); j++) {
                if (cartProds.get(i).getIdProduct().equals(prodsBdd.get(j).getIdProduct())) {
                    if (cartProds.get(i).getQuantity() > prodsBdd.get(j).getStock()) {
                        listNoStock.add(cartProds.get(i));
                    }
                    break;
                }
            }
        }
        return listNoStock;
    }

    private List<ProductDto> setNotEnoughStock(List<ProductDto> listNoStock, List<ProductDto> listProd) {
        for (int i = 0; i < listNoStock.size(); i++) {
            for (int j = 0; j < listProd.size(); j++) {
                if (listNoStock.get(i).getIdProduct().equals(listProd.get(j).getIdProduct())) {
                    listProd.get(j).setNotEnoughStock(true);
                    break;
                }
            }
        }
        return listProd;
    }
}
