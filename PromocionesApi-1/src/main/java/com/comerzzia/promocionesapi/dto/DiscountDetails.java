package com.comerzzia.promocionesapi.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Discount promotion details")
public class DiscountDetails {

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount cannot exceed 100%")
    @Schema(description = "Discount percentage (0-100)", example = "20.5", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0", maximum = "100")
    private BigDecimal percentage;
}
