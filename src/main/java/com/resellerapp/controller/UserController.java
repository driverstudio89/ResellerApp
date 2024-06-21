package com.resellerapp.controller;

import com.resellerapp.model.dto.UserRegisterDto;
import com.resellerapp.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @GetMapping("/users/register")
    public String viewRegister() {
        return "register";
    }

    @PostMapping("/users/register")
    public String doRegister(
            @Valid UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/users/register";
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("passwordMismatch", userRegisterDto);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            return "redirect:/users/register";
        }

        if (!userServiceImpl.register(userRegisterDto)) {
            redirectAttributes.addFlashAttribute("alreadyInUse", userRegisterDto);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            return "redirect:/users/register";
        }

        return "redirect:/users/login";


    }


}