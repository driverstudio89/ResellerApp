package com.resellerapp.model.dto;

import com.resellerapp.model.enums.ConditionName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AddOfferDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private ConditionName condition;

    public @NotNull @Size(min = 2, max = 50) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(min = 2, max = 50) String description) {
        this.description = description;
    }

    public @NotNull @Positive BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Positive BigDecimal price) {
        this.price = price;
    }

    public @NotNull ConditionName getCondition() {
        return condition;
    }

    public void setCondition(@NotNull ConditionName condition) {
        this.condition = condition;
    }
}
