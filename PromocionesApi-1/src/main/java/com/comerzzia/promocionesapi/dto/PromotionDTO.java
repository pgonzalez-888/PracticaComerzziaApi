package com.comerzzia.promocionesapi.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO for promotions")
public class PromotionDTO {

    @NotBlank(message = "ID is required")
    @Schema(description = "Unique promotion ID", example = "PROMO2024", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "Promotion description", example = "20% discount on product A")
    private String description;

    @Schema(description = "Creation date", example = "2024-04-01")
    private LocalDate creationDate;

    @NotNull(message = "Start date is required")
    @Schema(description = "Start date of promotion validity", example = "2024-04-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Schema(description = "End date of promotion validity", example = "2024-05-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate endDate;

    @NotBlank(message = "Item code is required")
    @Schema(description = "Item code to which the promotion applies", example = "A12345", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemCode;

    @NotNull(message = "Promotion type is required")
    @Schema(description = "Type of promotion", example = "DISCOUNT", allowableValues = {"DISCOUNT", "FIXED_PRICE"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "Discount details, required when type is DISCOUNT")
    @Valid
    private DiscountDetails discountDetails;

    @Schema(description = "Fixed price details, required when type is FIXED_PRICE")
    @Valid
    private FixedPriceDetails fixedPriceDetails;
    
    @AssertTrue(message = "Invalid promotion configuration")
    public boolean isValidPromotion() {
        if ("DISCOUNT".equals(type)) {
            return discountDetails != null && fixedPriceDetails == null;
        } else if ("FIXED_PRICE".equals(type)) {
            return fixedPriceDetails != null && discountDetails == null;
        }
        return false;
    }

}