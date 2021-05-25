package spring1.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring1.entities.User;
import spring1.services.UserService;

import javax.validation.Valid;

@Controller
@Slf4j
public class Registration {

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(Model model) {

        log.info("registration");

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @ModelAttribute("userForm") @Valid User userForm,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userForm.getUsername().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Password mismatch");
            return "registration";
        }
        if (!userService.createUser(userForm)) {
            model.addAttribute("userCreateError",
                    "Can't create user. Try another user name."
            );
            return "registration";
        }

        return "redirect:/";
    }
}
