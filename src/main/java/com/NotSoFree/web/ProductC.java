package com.NotSoFree.web;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.service.CategoryService;
import com.NotSoFree.service.ProductService;
import com.NotSoFree.service.UserDService;
import com.NotSoFree.util.CustomUserDetails;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserDService userDService;

    @GetMapping(value = "/admin/savePage")
    public String savePage(Model model) throws Exception {
        log.info("savePage handler");

        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("listCategories", categoryService.listByState(new Byte("1")));
        model.addAttribute("formAction", "/productC/admin/saveProd");
        return "saveEditProdP";
    }

    @GetMapping(value = "/admin/editPage/{idProduct}")
    public String editPage(Product product, Model model) throws Exception {
        log.info("editPage handler");

        model.addAttribute("productDto", productService.findProduct(product.getIdProduct()));
        model.addAttribute("listCategories", categoryService.listByState(new Byte("1")));
        model.addAttribute("formAction", "/productC/admin/editProd");

        return "saveEditProdP";
    }

    @PostMapping(value = "/admin/deleteProd")
    public String deleteProduct(@RequestParam(name = "idProduct") String idProduct, RedirectAttributes redirectAttrs) throws Exception {
        log.info("deleteProduct handler");

        productService.removeProduct(Long.parseLong(idProduct));

        redirectAttrs
                .addFlashAttribute("message", "Product deleted successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/";
    }

    @PostMapping(value = "/admin/editProd")
    public String editProd(Model model, @Valid ProductDto productDto,
            BindingResult result,
            @RequestParam(name = "boxCategory", required = false) List<String> newCategories,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception {
        log.info("editProd handler");

        if (result.hasErrors()) {
            model.addAttribute("productDto", productDto);
            model.addAttribute("formAction", "/productC/admin/editProd");
            return "saveEditProdP";
        } else if (newCategories.isEmpty()) {  //why this? th:checked doesn't work if th:field is present, so I can't check it on the product object, I have to get it separately and check it separately
            model.addAttribute("productDto", productDto);
            model.addAttribute("formAction", "/productC/admin/saveProd");

            redirectAttrs
                    .addFlashAttribute("message", "The product must have at least 1 category")
                    .addFlashAttribute("class", "danger");
            return "saveEditProdP";
        }
        productDto.setNewCategories(newCategories);
        productService.saveProduct(productDto, image);

        redirectAttrs
                .addFlashAttribute("message", "Product edited successfully")
                .addFlashAttribute("class", "success")
                .addAttribute("idProduct", productDto.getIdProduct());

        return "redirect:/productC/admin/editPage/{idProduct}";
    }

    @PostMapping(value = "/admin/saveProd")
    public String saveProduct(Model model, @Valid ProductDto productDto,
            BindingResult result,
            @RequestParam(name = "boxCategory", required = false) List<String> newCategories,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception {
        log.info("saveProd handler");

        if (result.hasErrors()) {
            model.addAttribute("productDto", productDto);
            model.addAttribute("listCategories", categoryService.listByState(new Byte("1")));
            model.addAttribute("formAction", "/productC/admin/saveProd");
            return "saveEditProdP";
        } else if (newCategories.isEmpty()) {  //why this? th:checked doesn't work if th:field is present, so I can't check it on the product object, I have to get it separately and check it separately
            model.addAttribute("productDto", productDto);
            model.addAttribute("listCategories", categoryService.listByState(new Byte("1")));
            model.addAttribute("formAction", "/productC/admin/saveProd");

            redirectAttrs
                    .addFlashAttribute("message", "The product must have at least 1 category")
                    .addFlashAttribute("class", "danger");
            return "saveEditProdP";
        }

        productService.saveProduct(productDto, image);

        redirectAttrs
                .addFlashAttribute("message", "Product created successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/productC/admin/savePage";
    }

    @GetMapping(value = "/listAllPage")
    public String listAllPage(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "20") String pageSize) throws Exception {

        log.info("listAllPage handler");

        int pageNoInt = Integer.parseInt(pageNo);

        Page<Product> pageProd = productService.findPaginated(pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);

        model.addAttribute("products", pageProd.getContent());
        model.addAttribute("totalPages", pageProd.getTotalPages());
        model.addAttribute("totalItems", pageProd.getTotalElements());
        model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "listAllProducts";
    }

    @GetMapping(value = "/detailPage/{idProduct}")
    public String detailPage(@PathVariable String idProduct,
            Model model,
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails loggedUser) throws Exception {
        log.info("detailPage handler");

        List<ProductDto> cartList;
        HttpSession session = request.getSession(false);

        if (loggedUser != null) {
            UserD user = userDService.findUserByUsername(loggedUser.getUsername());
            Optional<Product> productFav = user.getFavorites().stream()
                    .map(favorite -> favorite.getProduct())
                    .filter(product -> product.getIdProduct() == Long.parseLong(idProduct)).findFirst();
            if (productFav.isPresent()) {
                model.addAttribute("inFavorites", true);
            } else {
                model.addAttribute("inFavorites", false);
            }
        }

        if (session != null) {
            ProductDto productDto = productService.findProduct(Long.parseLong(idProduct));
            cartList = (List<ProductDto>) session.getAttribute("cartList");
            if (cartList != null) {
                for (int i = 0; i < cartList.size(); i++) {
                    if (cartList.get(i).getIdProduct().equals(productDto.getIdProduct())) {
                        productDto.setInCart(true);
                    }
                }
            }
            model.addAttribute("productDto", productDto);
        } else {
            model.addAttribute("productDto", productService.findProduct(Long.parseLong(idProduct)));
        }

        return "detailProduct";
    }

    @PostMapping(value = "/navSearch")
    public String search(Model model,
            @RequestParam(name = "search") String search,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "20") String pageSize) throws Exception {
        log.info("search handler");

        int pageNoInt = Integer.parseInt(pageNo);
        byte active = 1;

        PageDto pageProd = productService.findPaginatedLike(search, pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);
        List<Category> activeCategories = categoryService.listByState(active);

        model.addAttribute("search", search);
        model.addAttribute("categories", activeCategories);
        model.addAttribute("products", pageProd.getContent());
        model.addAttribute("totalPages", pageProd.getTotalPages());
        model.addAttribute("totalItems", pageProd.getTotalElements());
        model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "search";
    }

    @GetMapping(value = "/search")
    public String searchByCategory(Model model,
            @RequestParam(name = "categorySelect", defaultValue = "0") String category,
            @RequestParam(name = "search") String search,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idProduct") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "20") String pageSize) throws Exception {
        log.info("searchByCategory handler");

        int pageNoInt = Integer.parseInt(pageNo);
        byte active = 1;
        PageDto pageProd;

        if (category.equalsIgnoreCase("0")) {
            pageProd = productService.findPaginatedLike(search, pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);
        } else {
            pageProd = productService.findPaginatedLikeByCategory(search, pageNoInt, Integer.parseInt(pageSize), sortField, sortDir, new Category(Long.parseLong(category)));
            model.addAttribute("categorySelect", category);
        }
        List<Category> activeCategories = categoryService.listByState(active);

        model.addAttribute("search", search);
        model.addAttribute("categories", activeCategories);
        model.addAttribute("products", pageProd.getContent());
        model.addAttribute("totalPages", pageProd.getTotalPages());
        model.addAttribute("totalItems", pageProd.getTotalElements());
        model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "search";
    }

}
