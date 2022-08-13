package com.NotSoFree.web;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import java.security.Principal;
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
@RequestMapping("/userC")
public class UserC {

    @Autowired
    private UserDService userDService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/editPage")
    public String editPage(Model model, Principal principal) throws UserDNotFoundByUsername {
        log.info("editPage handler");

        UserD userD = userDService.findUserDByUsername(principal.getName());

        model.addAttribute("actualUser", userD);
        return "userProfile";
    }

    @GetMapping(value = "/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");

        UserD userD = new UserD();

        model.addAttribute("userD", userD);
        model.addAttribute("formAction", "/userC/saveUser");
        return "saveEditUser";
    }
    
    
    @PostMapping(value="/saveUser")
    public String saveUser(Model model,@Valid UserD userD,
             BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception{
        log.info("saveUser handler");
        
         if (result.hasErrors()) {
            model.addAttribute("user", userD);
            model.addAttribute("formAction", "/userC/saveUser");
            return "saveEditUser";
        }
        
        userDService.save(userD, image);
        
        redirectAttrs
                .addFlashAttribute("message", "User created successfully")
                .addFlashAttribute("class", "success");
        
        return "redirect:/userC/login";
    }
    
}
