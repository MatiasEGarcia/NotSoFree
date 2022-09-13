package com.NotSoFree.web;

import com.NotSoFree.domain.Person;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PersonDto;
import com.NotSoFree.service.PersonService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/personC")
public class PersonC {

    @Autowired
    private PersonService personService;

    @Autowired
    private UserDService userDService;

    @GetMapping(value = "/editPage")
    public String edit(Model model, @AuthenticationPrincipal CustomUserDetails loggedUser) throws Exception {
        log.info("editPage handler");
        //Why not simply retrieve the user that is in the customDetails, because it will not be updated once the person is edited
        UserD userD = userDService.findUserByUsername(loggedUser.getUsername());
        model.addAttribute("personDto", new PersonDto(userD.getPerson()));
        model.addAttribute("userImage", userD.getImage());
        return "editPersonDetails";
    }

    @PostMapping(value = "/edit")
    public String editUser(Model model, @Valid PersonDto personDto,
            BindingResult result,
            RedirectAttributes redirectAttrs) throws Exception {
        log.info("edit handler");
        if (result.hasErrors()) {
            model.addAttribute("personDto", personDto);
            return "editPersonDetails";
        }
        personService.save(new Person(personDto));
        redirectAttrs
                .addFlashAttribute("message", "Person details edited successfully")
                .addFlashAttribute("class", "success");

        return "redirect:/personC/editPage";
    }

}
