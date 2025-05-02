package com.comerzzia.promocionesapi.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("FIXED_PRICE")
@Schema(description = "Fixed price promotion entity")
@Data
@ToString(callSuper = true) // Incluye campos de la clase padre
@EqualsAndHashCode(callSuper = true) // Incluye campos de la clase padre en equals y hashCode
@NoArgsConstructor
public class FixedPricePromotion extends Promotion {

    @NotNull
    @Schema(description = "Promotional fixed price", example = "5.99")
    private BigDecimal fixedPrice;

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal originalPrice) {
        if (fixedPrice == null) {
            throw new IllegalStateException("Fixed price must be set for fixed price promotions");
        }
        return fixedPrice;
    }
    
}