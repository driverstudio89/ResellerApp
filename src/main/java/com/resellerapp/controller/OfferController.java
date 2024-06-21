package com.resellerapp.controller;

import com.resellerapp.config.UserSession;
import com.resellerapp.model.dto.AddOfferDto;
import com.resellerapp.service.OfferServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OfferController {

    private final UserSession userSession;
    private final OfferServiceImpl offerServiceImpl;

    public OfferController(UserSession userSession, OfferServiceImpl offerServiceImpl) {
        this.userSession = userSession;
        this.offerServiceImpl = offerServiceImpl;
    }

    @ModelAttribute("offerData")
    public AddOfferDto addOfferDto() {
        return new AddOfferDto();
    }

    @GetMapping("/offer-add")
    public String viewAddOffer() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        return "offer-add";
    }

    @PostMapping("/offer-add")
    public String doAddOffer(
            @Valid AddOfferDto addOfferDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerData", addOfferDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerData", bindingResult);
            return "redirect:/offer-add";
        }

        if (!offerServiceImpl.addOffer(addOfferDto)) {
            redirectAttributes.addFlashAttribute("offerData", addOfferDto);
            redirectAttributes.addFlashAttribute("somethingWentWrong", bindingResult);
            return "redirect:/offer-add";
        }

        return "redirect:home";
    }
}
