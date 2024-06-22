package com.resellerapp.service;

import com.resellerapp.config.UserSession;
import com.resellerapp.model.dto.OfferInfoDto;
import com.resellerapp.model.dto.UserLoginDto;
import com.resellerapp.model.dto.UserRegisterDto;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl {


    private final UserSession userSession;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OfferRepository offerRepository;

    public UserServiceImpl(UserSession userSession, UserRepository userRepository, PasswordEncoder passwordEncoder, OfferRepository offerRepository) {
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.offerRepository = offerRepository;
    }

    public boolean register(UserRegisterDto userRegisterDto) {
        if (userSession.isLoggedIn()) {
            return false;
        }
        Optional<User> byUsernameOrEmail = userRepository.findByUsernameOrEmail(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        if (byUsernameOrEmail.isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDto userLoginDto) {
        if (userSession.isLoggedIn()) {
            return false;
        }
        Optional<User> byUsername = userRepository.findByUsername(userLoginDto.getUsername());
        if (byUsername.isEmpty()) {
            return false;
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), byUsername.get().getPassword())) {
            return false;
        }
        User user = byUsername.orElse(null);
        userSession.login(user.getId(), user.getUsername());
        userRepository.save(user);
        return true;
    }

    public void logout() {
        userSession.logout();
    }

    @Transactional
    public Set<OfferInfoDto> getAllOtherOffers() {
        Set<User> allByIdNot = userRepository.findAllByIdNot(userSession.getId());
        Set<OfferInfoDto> offerInfoDtoSet = new HashSet<>();

        for (User user : allByIdNot) {
            for (Offer offer : user.getOffers()) {
                OfferInfoDto offerInfoDto = new OfferInfoDto(offer);
                offerInfoDto.setCondition(offer.getCondition().getConditionName().toString());
                offerInfoDto.setUsername(user.getUsername());
                offerInfoDtoSet.add(offerInfoDto);
            }
        }
        return offerInfoDtoSet;

    }

    @Transactional
    public Set<OfferInfoDto> getMyOffers() {
        Set<OfferInfoDto> offerInfoDtoSet = new HashSet<>();
        User user = userRepository.findById(userSession.getId()).orElse(null);
        for (Offer offer : user.getOffers()) {
            OfferInfoDto offerInfoDto = new OfferInfoDto(offer);
            offerInfoDto.setUsername(user.getUsername());
            offerInfoDto.setCondition(offer.getCondition().getConditionName().toString());
            offerInfoDtoSet.add(offerInfoDto);
        }

        System.out.println();
        return offerInfoDtoSet;
    }


    @Transactional
    public Set<OfferInfoDto> getBoughtItems() {
        Set<OfferInfoDto> offerInfoDtoSet = new HashSet<>();
        User user = userRepository.findById(userSession.getId()).orElse(null);
        for (Offer boughtOffer : user.getBoughtOffers()) {
            OfferInfoDto offerInfoDto = new OfferInfoDto(boughtOffer);
            offerInfoDto.setUsername(user.getUsername());
            offerInfoDto.setCondition(boughtOffer.getCondition().getConditionName().toString());
            offerInfoDtoSet.add(offerInfoDto);
        }

        System.out.println();
        return offerInfoDtoSet;
    }

    @Transactional
    public void buyItem(long offerId) {
        User user = userRepository.findById(userSession.getId()).orElse(null);
        Offer offer = offerRepository.findById(offerId).orElse(null);
        User seller = userRepository.findByOffersId(offerId).orElse(null);
        seller.getOffers().remove(offer);
        user.getBoughtOffers().add(offer);
        userRepository.save(user);
    }
}
