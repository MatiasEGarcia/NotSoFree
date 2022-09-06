package com.NotSoFree.web;

import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.service.ProductService;
import com.NotSoFree.util.Cart;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/cartC")
public class CartC {

    @Autowired
    private ProductService productService;

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
        } else {
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

    @GetMapping(value = "/cartList")
    public String cartList(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "10") String pageSize,
            HttpSession session) throws Exception {
        log.info("cartList handler");
        List<Cart> cartList;
        PageDto<ProductDto> products;
        List<ProductDto> listProducts;

        if (!session.isNew()) {
            cartList = (List<Cart>) session.getAttribute("cartList");
            if (!cartList.isEmpty()) {
                List<Long> idProducts = cartList.stream().map(cart -> cart.getIdProduct()).collect(Collectors.toList());
                products = productService.findProductsPaginated(idProducts, Integer.parseInt(pageNo), Integer.parseInt(pageSize), sortField, sortDir);

                listProducts = setAmount(cartList, products.getContent());

                model.addAttribute("products", listProducts);
                model.addAttribute("totalPages", products.getTotalPages());
                model.addAttribute("totalItems", products.getTotalElements());
                model.addAttribute("actualPage", Integer.parseInt(pageNo)); //I need it to be integer for the pagination of the page to work
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDir);
                model.addAttribute("pageSize", pageSize);
                model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            }
        }

        return "cart";
    }

    //This handler is for Authenticated user
    @PostMapping(value = "UserPurchase")
    public String userPurchase() {
        //tengo que validar usando el buyValidation

        return null;

    }

    //method to set the quantity of the product in the cart
    public List<ProductDto> setAmount(List<Cart> carts, List<ProductDto> products) {
        for (int i = 0; i < carts.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                if (carts.get(i).getIdProduct().equals(products.get(i).getIdProduct())) {
                    products.get(i).setAmountInCart(carts.get(i).getAmount());
                    break;
                }
            }
        }

        return products;
    }

}
