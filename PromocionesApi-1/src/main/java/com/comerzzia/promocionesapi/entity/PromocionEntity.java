package com.comerzzia.promocionesapi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

@Schema(description = "Entidad que representa una promoción aplicable a un artículo")
public class PromocionEntity {

	@Id
	@NotEmpty(message = "Es necesario incluir un id")
	@Schema(description = "ID único de la promoción", example = "PROMO2024")
	private String id;

	@Schema(description = "Descripción de la promoción", example = "20% de descuento en el producto A")
	private String descripcion;

	@Schema(description = "Fecha de alta de la promoción", example = "2024-04-01")
	private LocalDate fechaAlta;

	@NotNull
	@Schema(description = "Fecha de inicio de la promoción", example = "2024-04-15")
	private LocalDate fechaInicio;

	@NotNull
	@Schema(description = "Fecha de finalización de la promoción", example = "2024-05-15")
	private LocalDate fechaFin;

	@NotNull
	@Schema(description = "Código del artículo al que se aplica la promoción", example = "A12345")
	private String codigoArticulo;

	@NotNull
	@Schema(description = "Tipo de promoción (PRECIO o DESCUENTO)", example = "DESCUENTO")
	private TipoPromocion tipo;

	@Schema(description = "Nuevo precio promocional (solo si el tipo es PRECIO)", example = "5.99")
	private BigDecimal precioPromocion;

	@Schema(description = "Porcentaje de descuento (solo si el tipo es DESCUENTO)", example = "20")
	private BigDecimal porcentajeDescuento;

	public enum TipoPromocion {
		PRECIO, DESCUENTO
	}

	public boolean estaVigente(LocalDate hoy) {
		return (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) && (hoy.isEqual(fechaFin) || hoy.isBefore(fechaFin));
	}

	public BigDecimal calcularPrecioAplicado(BigDecimal precioOriginal) {
		if (tipo == TipoPromocion.PRECIO) {
			return precioPromocion;
		}
		else if (tipo == TipoPromocion.DESCUENTO) {
			BigDecimal descuento = precioOriginal.multiply(porcentajeDescuento).divide(BigDecimal.valueOf(100));
			return precioOriginal.subtract(descuento);
		}
		return precioOriginal;
	}

}
