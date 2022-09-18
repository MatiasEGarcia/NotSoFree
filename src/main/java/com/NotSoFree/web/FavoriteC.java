package com.NotSoFree.web;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.service.CategoryService;
import com.NotSoFree.service.FavoriteService;
import com.NotSoFree.util.CustomUserDetails;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/auth/add")
    public String addFavorite(@RequestParam(name = "idProduct") String idProduct,
            @AuthenticationPrincipal CustomUserDetails loggedUser,
            RedirectAttributes redirectAttrs, HttpServletRequest request) throws Exception {
        log.info("addFavorite handler");

        favoriteService.saveFavorite(new Favorite(new Product(Long.parseLong(idProduct)), loggedUser.getUser()));

        String url = request.getHeader("referer");
        redirectAttrs
                .addFlashAttribute("message", "Favorite added")
                .addFlashAttribute("class", "success");
        return "redirect:" + url;
    }

    @GetMapping(value = "/auth/delete/{idProduct}")
    public String deleteFavorite(Product product, RedirectAttributes redirectAttrs) throws Exception {
        log.info("deleteFavorite handler");

        favoriteService.deleteFavoriteByProduct(product);

        redirectAttrs
                .addFlashAttribute("message", "Product deleted from favorites")
                .addFlashAttribute("class", "success")
                .addAttribute("idProduct", product.getIdProduct());

        return "redirect:/productC/detailPage/{idProduct}";

    }

    @GetMapping("/auth/listAll")
    public String list(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "10") String pageSize,
            @AuthenticationPrincipal CustomUserDetails loggedUser
    ) throws Exception {

        int pageNoInt = Integer.parseInt(pageNo);
        byte active = 1;
        PageDto<Product> pageProd = favoriteService.favoriteList(loggedUser.getUser(), pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);
        List<Category> activeCategories = categoryService.listByState(active);
        if (pageProd != null) {
            model.addAttribute("products", pageProd.getContent());
            model.addAttribute("totalPages", pageProd.getTotalPages());
            model.addAttribute("totalItems", pageProd.getTotalElements());
        }
        model.addAttribute("categories", activeCategories);
        model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "listFavorites";
    }

    @GetMapping("/auth/listByCategory")
    public String listByCategory(Model model,
            @RequestParam(name = "categorySelect") String categorySelected,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "10") String pageSize,
            @AuthenticationPrincipal CustomUserDetails loggedUser
    ) throws Exception {

        int pageNoInt = Integer.parseInt(pageNo);
        byte active = 1;
        PageDto<Product> pageProd = favoriteService.favoriteListByCategory(loggedUser.getUser(),
                new Category(Long.parseLong(categorySelected)), pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);
        List<Category> activeCategories = categoryService.listByState(active);

        if (pageProd != null) {
            model.addAttribute("products", pageProd.getContent());
            model.addAttribute("totalPages", pageProd.getTotalPages());
            model.addAttribute("totalItems", pageProd.getTotalElements());
            model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }
        model.addAttribute("categories", activeCategories);

        return "listFavorites";
    }

}
