package com.resellerapp.service;

import com.resellerapp.config.UserSession;
import com.resellerapp.model.dto.AddOfferDto;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.ConditionRepository;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
public class OfferServiceImpl {


    private final UserSession userSession;
    private final OfferRepository offerRepository;
    private final ConditionRepository conditionRepository;
    private final UserRepository userRepository;

    public OfferServiceImpl(UserSession userSession, OfferRepository offerRepository, ConditionRepository conditionRepository, UserRepository userRepository) {
        this.userSession = userSession;
        this.offerRepository = offerRepository;
        this.conditionRepository = conditionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean addOffer(AddOfferDto addOfferDto) {
        if (!userSession.isLoggedIn()) {
            return false;
        }
        Offer offer = new Offer();
        Optional<User> optionalUser = userRepository.findById(userSession.getId());
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        Condition condition = conditionRepository.findByConditionName(addOfferDto.getCondition());
        offer.setDescription(addOfferDto.getDescription());
        offer.setPrice(addOfferDto.getPrice());
        offer.setCondition(condition);


        user.getOffers().add(offer);
        offerRepository.save(offer);
        userRepository.save(user);
        return true;

    }
}
