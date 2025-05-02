package com.comerzzia.promocionesapi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("DISCOUNT")
@Schema(description = "Discount promotion entity")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DiscountPromotion extends Promotion {

	@NotNull
	@Schema(description = "Discount percentage", example = "20")
	private BigDecimal discountPercentage;

	@Override
	public BigDecimal calculateFinalPrice(BigDecimal originalPrice) {
		if (discountPercentage == null) {
			throw new IllegalStateException("Discount percentage must be set for discount promotions");
		}
		BigDecimal discount = originalPrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		return originalPrice.subtract(discount);
	}

}