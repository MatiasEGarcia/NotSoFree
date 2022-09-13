package com.NotSoFree.web;

import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import com.NotSoFree.service.FavoriteService;
import com.NotSoFree.util.CustomUserDetails;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/favoriteC")
public class FavoriteC {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @PostMapping(value="/add")
    public String addFavorite(@RequestParam(name = "idProduct") String idProduct,
            @AuthenticationPrincipal CustomUserDetails loggedUser,
            RedirectAttributes redirectAttrs,HttpServletRequest request) throws Exception{
        log.info("addFavorite handler");
        
        favoriteService.saveFavorite(new Favorite(new Product(Long.parseLong(idProduct)),loggedUser.getUser()));
        
         String url = request.getHeader("referer");
         redirectAttrs
                .addFlashAttribute("message", "Favorite added")
                .addFlashAttribute("class", "success");
        return "redirect:" + url;
    }
    
}
