package com.comerzzia.promocionesapi.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Fixed price promotion details")
public class FixedPriceDetails {

    @NotNull(message = "Fixed price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Promotional fixed price", example = "5.99", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private BigDecimal price;
}
