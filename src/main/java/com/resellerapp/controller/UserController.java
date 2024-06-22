package com.resellerapp.controller;

import com.resellerapp.config.UserSession;
import com.resellerapp.model.dto.UserLoginDto;
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
    private final UserSession userSession;

    public UserController(UserServiceImpl userServiceImpl, UserSession userSession) {
        this.userServiceImpl = userServiceImpl;
        this.userSession = userSession;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("loginData")
    public UserLoginDto UserLoginDto() {
        return new UserLoginDto();
    }


    @GetMapping("/users/register")
    public String viewRegister() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/users/register")
    public String doRegister(
            @Valid UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }

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

    @GetMapping("/users/login")
    public String viewLogin() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/users/login")
    public String doLogin(
            @Valid UserLoginDto userLoginDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/users/login";
        }

        if (!userServiceImpl.login(userLoginDto)) {
            redirectAttributes.addFlashAttribute("wrongCredentials", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/users/logout")
    public String Logout() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userServiceImpl.logout();
        return "redirect:/";
    }




}
