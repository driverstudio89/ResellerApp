package com.resellerapp.controller;

import com.resellerapp.config.UserSession;
import com.resellerapp.model.dto.OfferInfoDto;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.service.OfferServiceImpl;
import com.resellerapp.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final UserServiceImpl userServiceImpl;
    private final OfferServiceImpl offerServiceImpl;

    public HomeController(UserSession userSession, UserServiceImpl userServiceImpl, OfferServiceImpl offerServiceImpl) {
        this.userSession = userSession;
        this.userServiceImpl = userServiceImpl;
        this.offerServiceImpl = offerServiceImpl;
    }

    @GetMapping("/")
    public String notLoggedIn() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String loggedIn(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        Set<OfferInfoDto> myOffers = userServiceImpl.getMyOffers();
        Set<OfferInfoDto> allOtherOffers = userServiceImpl.getAllOtherOffers();
        Set<OfferInfoDto> boughtItems = userServiceImpl.getBoughtItems();

        model.addAttribute("myOffers", myOffers);
        model.addAttribute("allOtherOffers", allOtherOffers);
        model.addAttribute("boughtItems", boughtItems);

        System.out.println();

        return "home";
    }

    @GetMapping("/remove-offer/{id}")
    public String removeOffer(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        offerServiceImpl.removeOffer(id);
        return "redirect:/home";
    }

    @GetMapping("/buy-now/{id}")
    public String buyNow(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userServiceImpl.buyItem(id);
        return "redirect:/home";
    }


}
