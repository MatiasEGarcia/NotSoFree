package com.NotSoFree.web;

import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.UserAEDto;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEPUDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.service.UserDService;
import com.NotSoFree.util.CustomUserDetails;
import com.NotSoFree.util.RolEnum;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/userC")
public class UserC {

    @Autowired
    private UserDService userDService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/savePage")
    public String savePage(Model model) {
        log.info("savePage handler");
        UserCDto userCDto = new UserCDto();
        model.addAttribute("userCDto", userCDto);
        return "saveUser";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(Model model, @Valid UserCDto userCDto,
            BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile image,
            RedirectAttributes redirectAttrs) throws Exception {
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

    @GetMapping(value = "/passUNamePage")
    public String passUNamePage(Model model, @AuthenticationPrincipal CustomUserDetails loggedUser) {
        log.info("passUNamePage handler");

        UserEPUDto userEPUDto = new UserEPUDto();
        userEPUDto.setIdUser(loggedUser.getId());
        userEPUDto.setUsername(loggedUser.getUsername());

        model.addAttribute("userEPUDto", userEPUDto);
        return "editPassUName";
    }

    @PostMapping(value = "editPassUName")
    public String editPassUName(Model model, @Valid UserEPUDto userEPUDto,
            BindingResult result,
            RedirectAttributes redirectAttrs) throws Exception {
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

    @GetMapping(value = "/listAllPage")
    public String listAllPage(Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
            @RequestParam(name = "sortField", defaultValue = "idUser") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "pageSize", defaultValue = "2") String pageSize) throws Exception {
        log.info("listAllPage handler");
        int pageNoInt = Integer.parseInt(pageNo);

        PageDto<UserAEDto> users = userDService.listUsers(pageNoInt, Integer.parseInt(pageSize), sortField, sortDir);

        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalItems", users.getTotalElements());
        model.addAttribute("actualPage", pageNoInt); //I need it to be integer for the pagination of the page to work
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "listAllUsers";
    }
    
    @GetMapping(value = "/editByAdminPage/{userName}")
    public String editByAdminPage(Model model,@PathVariable(name = "userName") String username) throws UserDNotFoundByUsername{
        log.info("editByAdminPage handler");
        
        UserAEDto userAEDto= new UserAEDto(userDService.findUserByUsername(username));
        model.addAttribute("userAEDto", userAEDto);
        
        return "editByAdmin";
    }
    
    @PostMapping(value = "editByAdmin")
    public String editByAdmin(Model model
            ,UserAEDto userAEDto
            ,@RequestParam(name = "rolCheckbox", required = false) List<RolEnum> listRolEnum
            ,@RequestParam(name = "stateRadio", required = false) String state
            ,RedirectAttributes redirectAttrs) throws Exception {
        log.info("saveUserByAdmin handler");
        
        userAEDto.setState(state);
        userDService.userEditByAdmin(userAEDto,listRolEnum);
        
        redirectAttrs
                .addFlashAttribute("message", "User edited successfully")
                .addFlashAttribute("class", "success")
                .addAttribute("userName", userAEDto.getUserName());

        return "redirect:/userC/editByAdminPage/{userName}";
    }
    
    @PostMapping(value = "/delete")
    public String deleteProduct(@RequestParam(name = "userName") String userName, RedirectAttributes redirectAttrs) throws Exception{
        log.info("deleteProduct handler");

        userDService.deleteByuserName(userName);

        redirectAttrs
                .addFlashAttribute("message", "User deleted successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/userC/listAllPage";
    }
    
     @GetMapping(value = "/editImagePage")
    public String editImagePage(Model model,@AuthenticationPrincipal CustomUserDetails loggedUser)throws Exception{
        log.info("editImagePage handler");
        model.addAttribute("userId", loggedUser.getId());
        model.addAttribute("oldImage", userDService.findUserByUsername(loggedUser.getUsername()).getImage());
        return "editImage";
    }
    @PostMapping(value = "/editImage")
    public String editImage(@RequestParam(name = "file", required = false) MultipartFile image, 
            @RequestParam(name = "userId") String userId, 
            RedirectAttributes redirectAttrs) throws Exception{
        log.info("editImage handler");

        if(image.isEmpty()){
            redirectAttrs
                .addFlashAttribute("message", "There is no image")
                .addFlashAttribute("class", "danger");
            return "redirect:/userC/editImagePage";
        }
        
        userDService.userImageEdit(image, userId);
        
        redirectAttrs
                .addFlashAttribute("message", "User image saved successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/personC/editPage";
    }
    
}
