package com.comerzzia.promocionesapi.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Representa un ticket de compra que contiene varias líneas de venta")
public class Ticket {

	@NotEmpty
	@Schema(description = "ID único del ticket", example = "TCK123456")
	private String id;

	@Size(min = 1)
	@Valid
	@Schema(description = "Listado de líneas de venta incluidas en el ticket")
	private List<LineaVenta> lineasVenta = new ArrayList<>();

	public Ticket(String id) {
		this.id = id;
	}

	public void agregarLinea(LineaVenta linea) {
		this.lineasVenta.add(linea);
	}

	public BigDecimal calcularTotal() {
		return lineasVenta.stream().map(LineaVenta::getImporteTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
