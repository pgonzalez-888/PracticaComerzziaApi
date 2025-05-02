package com.comerzzia.promocionesapi.model;

import java.math.BigDecimal;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Represents a sale line within a ticket")
public class SaleLine {

	@NotBlank(message = "Article code is required")
	@Schema(description = "Code of the sold article", example = "A12345")
	private String itemCode;

	@Min(value = 1, message = "Quantity must be at least 1")
	@Schema(description = "Quantity of articles sold", example = "2")
	private int quantity;

	@NotNull
	@Schema(description = "Original unit price", example = "10.99")
	private BigDecimal originalUnitPrice;

	@Schema(description = "Unit price after applying promotions", example = "8.99")
	private BigDecimal discountedUnitPrice;

	@Schema(description = "Total amount of the line (discounted price x quantity)", example = "17.98")
	private BigDecimal totalAmount;

	@Schema(description = "ID of the applied promotion (if any)", example = "PROMO2024")
	private String appliedPromotionId;

	public void applyPromotion(Promotion promo) {
		this.discountedUnitPrice = promo.calculateFinalPrice(this.originalUnitPrice);
		this.totalAmount = this.discountedUnitPrice.multiply(BigDecimal.valueOf(this.quantity));
		this.appliedPromotionId = promo.getId();
	}

	public BigDecimal simulatePromotionDiscount(Promotion promo) {
		BigDecimal simulatedPrice = promo.simulateFinalPrice(this.originalUnitPrice);
		return this.originalUnitPrice.subtract(simulatedPrice).multiply(BigDecimal.valueOf(this.quantity));
	}

	public void calculatePriceWithoutPromotion() {
	    this.discountedUnitPrice = this.originalUnitPrice;
	    this.totalAmount = this.originalUnitPrice.multiply(BigDecimal.valueOf(this.quantity));
	    this.appliedPromotionId = null;
	}

	
}
