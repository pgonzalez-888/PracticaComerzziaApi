package com.comerzzia.promocionesapi.entity;

import java.math.BigDecimal;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Representa una línea de venta dentro de un ticket")
public class LineaVenta {

	@NotBlank(message = "Es necesario añadir el codigo del articulo")
	@Schema(description = "Código del artículo vendido", example = "A12345")
	private String codigoArticulo;

	@Min(1)
	@Schema(description = "Cantidad de artículos vendidos", example = "2")
	private int cantidad;

	@NotNull
	@Schema(description = "Precio original por unidad", example = "10.99")
	private BigDecimal precioUnitarioOriginal;

	@Schema(description = "Precio por unidad después de aplicar promociones", example = "8.99")
	private BigDecimal precioUnitarioPromocionado;

	@Schema(description = "Importe total de la línea (precio promocionado x cantidad)", example = "17.98")
	private BigDecimal importeTotal;

	@Schema(description = "ID de la promoción aplicada (si existe)", example = "PROMO2024")
	private String idPromocionAplicada;
	
	public void aplicarPromocion(PromocionEntity promo) {
		this.precioUnitarioPromocionado = promo.calcularPrecioAplicado(this.precioUnitarioOriginal);
		this.importeTotal = this.precioUnitarioPromocionado.multiply(BigDecimal.valueOf(this.cantidad));
		this.idPromocionAplicada = promo.getId();
	}
	
}
