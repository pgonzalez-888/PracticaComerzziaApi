package com.comerzzia.promocionesapi.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO representing a sale line in a ticket")
public class SaleLineDTO {

    @NotBlank(message = "Article code is required")
    @Schema(description = "Code of the sold article", example = "A12345")
    private String itemCode;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of articles sold", example = "2")
    private int quantity;

    @NotNull(message = "Original unit price is required")
    @DecimalMin(value = "0.01", message = "Price must be positive")
    @Schema(description = "Original unit price", example = "10.99")
    private BigDecimal originalUnitPrice;

    @Schema(description = "Unit price after applying promotions", example = "8.99")
    private BigDecimal discountedUnitPrice;

    @Schema(description = "Total amount of the line (discounted price x quantity)", example = "17.98")
    private BigDecimal totalAmount;

    @Schema(description = "ID of the applied promotion (if any)", example = "PROMO2024")
    private String appliedPromotionId;
}

