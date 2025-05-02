package com.comerzzia.promocionesapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "x_promociones_tbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "promotion_type", discriminatorType = DiscriminatorType.STRING)
@Schema(description = "Abstract base class for promotions")
public abstract class Promotion {

	@Id
	@NotEmpty(message = "ID is required")
	@Schema(description = "Unique promotion ID", example = "PROMO2024")
	private String id;

	@Schema(description = "Promotion description", example = "20% discount on product A")
	private String description;

	@Schema(description = "Creation date", example = "2024-04-01")
	private LocalDate creationDate;

	@NotNull
	@Schema(description = "Start date", example = "2024-04-15")
	private LocalDate startDate;

	@NotNull
	@Schema(description = "End date", example = "2024-05-15")
	private LocalDate endDate;

	@NotNull
	@Schema(description = "Item code to which the promotion applies", example = "A12345")
	private String itemCode;

	public boolean isActive(LocalDate date) {
		return !date.isBefore(startDate) && !date.isAfter(endDate);
	}

	public abstract BigDecimal calculateFinalPrice(BigDecimal originalPrice);

	public BigDecimal simulateFinalPrice(BigDecimal originalPrice) {
		return calculateFinalPrice(originalPrice);
	}

}
