package com.NotSoFree.web;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEDto;
import com.NotSoFree.dto.UserEPUDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import com.NotSoFree.util.CustomUserDetails;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String editPage(Model model, @AuthenticationPrincipal CustomUserDetails loggedUser) throws UserDNotFoundByUsername {
        log.info("editPage handler");
        
      
        UserEDto userEDto = userDService.findUserByUsername(loggedUser.getUsername());
        model.addAttribute("userEDto", userEDto);
        model.addAttribute("formAction", "/userC/editUser");
        model.addAttribute("action", "edit");
        return "editUser";
    }

    @GetMapping(value = "/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");
        UserCDto userCDto= new UserCDto();
        model.addAttribute("userCDto", userCDto);
        return "saveUser";
    }
    
    
    @PostMapping(value="/saveUser")
    public String saveUser(Model model,@Valid UserCDto userCDto,
             BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception{
        log.info("saveUser handler");
        
         if (result.hasErrors()) {
            model.addAttribute("userCDto", userCDto);
            return "saveUser";
        }
        
        userDService.userCreate(userCDto, image);
        
        redirectAttrs
                .addFlashAttribute("message", "User created successfully")
                .addFlashAttribute("class", "success");
        
        return "redirect:/userC/login";
    }
    
    @PostMapping(value="/editUser")
    public String editUser(Model model,@Valid UserEDto userEDto,
            BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception{
         log.info("editUser handler");
        
         if (result.hasErrors()) {
            model.addAttribute("userEDto", userEDto);
            return "editUser";
        }
         

         userDService.userEdit(userEDto, image);
         
          redirectAttrs
                .addFlashAttribute("message", "User edited successfully")
                .addFlashAttribute("class", "success");
         
        return "redirect:/userC/editPage";
    }
    

    @GetMapping(value="/passUNamePage")
    public String passUNamePage(Model model,@AuthenticationPrincipal CustomUserDetails loggedUser){
        log.info("passUNamePage handler");
        
        UserEPUDto userEPUDto = new UserEPUDto();
        userEPUDto.setIdUser(loggedUser.getId());
        userEPUDto.setUsername(loggedUser.getUsername());
       
        model.addAttribute("userEPUDto", userEPUDto);
        return "editPassUName";
    }
    
    @PostMapping(value="editPassUName")
    public String editPassUName(Model model, @Valid UserEPUDto userEPUDto,
            BindingResult result,
            RedirectAttributes redirectAttrs) throws Exception{
         log.info("editPassUName handler");
        if (result.hasErrors()) {
            model.addAttribute("userEPUDto", userEPUDto);
            return "editPassUName";
        }
        userDService.userEditPassAndUName(userEPUDto); 
        
         redirectAttrs
                .addFlashAttribute("message", "User edited successfully, you must re-login")
                .addFlashAttribute("class", "success");
         
        return "redirect:/userC/login";
    }
    
}
