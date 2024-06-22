package com.resellerapp.model.dto;

import com.resellerapp.model.entity.Offer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class OfferInfoDto {

    @NotBlank
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String condition;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String description;

    public OfferInfoDto(Offer offer) {
        this.id = offer.getId();
        this.description = offer.getDescription();
        this.price = offer.getPrice();
        this.description = offer.getDescription();
    }

    @NotBlank
    public long getId() {
        return id;
    }

    public void setId(@NotBlank long id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getCondition() {
        return condition;
    }

    public void setCondition(@NotNull String condition) {
        this.condition = condition;
    }

    public @NotNull @Positive BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Positive BigDecimal price) {
        this.price = price;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }
}
