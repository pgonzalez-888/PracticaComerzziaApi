package org.openapitools.client.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LineaVenta;
import org.openapitools.client.model.PromocionEntity;
import org.openapitools.client.model.PromocionEntity.TipoEnum;
import org.openapitools.client.model.Ticket;

public class PromocionesApiTest {

	private final PromocionesApi api = new PromocionesApi();
	private String promocionId;

	@BeforeEach
	public void prepararPromocion() throws ApiException {
		// Crear una nueva promoción con ID único para cada test
		promocionId = "PROMO_" + UUID.randomUUID().toString().substring(0, 8); // Ej: PROMO_1a2b3c4d

		PromocionEntity promocionEntity = new PromocionEntity();
		promocionEntity.setId(promocionId);
		promocionEntity.setDescripcion("Descuento de prueba");
		promocionEntity.setFechaInicio(LocalDate.now());
		promocionEntity.setFechaFin(LocalDate.now().plusYears(1));
		promocionEntity.setPorcentajeDescuento(BigDecimal.valueOf(20));
		promocionEntity.setCodigoArticulo("EO");
		promocionEntity.setTipo(TipoEnum.DESCUENTO);

		api.crearPromocion(promocionEntity);
	}

	@Test
	public void crearPromocionTest() throws ApiException {
		// Ya se creó en @BeforeEach, simplemente lo verificamos
		PromocionEntity promocion = api.obtenerPromocionPorId(promocionId);

		assertNotNull(promocion);
		assertEquals(promocionId, promocion.getId());
		assertEquals(BigDecimal.valueOf(20).setScale(2), promocion.getPorcentajeDescuento().setScale(2));
	}

	@Test
	public void actualizarPromocionTest() throws ApiException {
		// Actualizamos la promoción creada en @BeforeEach
		PromocionEntity promocionActualizada = new PromocionEntity();
		promocionActualizada.setId(promocionId);
		promocionActualizada.setDescripcion("Descuento actualizado");
		promocionActualizada.setFechaInicio(LocalDate.now());
		promocionActualizada.setFechaFin(LocalDate.now().plusYears(2));
		promocionActualizada.setPorcentajeDescuento(BigDecimal.valueOf(25));
		promocionActualizada.setCodigoArticulo("EO");
		promocionActualizada.setTipo(TipoEnum.DESCUENTO);

		PromocionEntity response = api.actualizarPromocion(promocionId, promocionActualizada);

		assertEquals(promocionId, response.getId());
		assertEquals("Descuento actualizado", response.getDescripcion());
		assertEquals(BigDecimal.valueOf(25), response.getPorcentajeDescuento());
	}

	@Test
	public void aplicarPromocionesTest() throws ApiException {
		// Crear un ticket con una línea de venta
		Ticket ticket = new Ticket();
		ticket.setId("TICKET");
		LineaVenta linea = new LineaVenta();
		linea.setCodigoArticulo("EO");
		linea.setCantidad(2);
		linea.setPrecioUnitarioOriginal(BigDecimal.valueOf(10.00));
		ticket.addLineasVentaItem(linea);

		// Aplicar la promoción al ticket
		Ticket response = api.aplicarPromociones(ticket);

		// Verificamos que las líneas de venta del ticket tengan la promoción aplicada
		BigDecimal precioEsperado = BigDecimal.valueOf(7.50).setScale(3, RoundingMode.HALF_UP);; // 20% de descuento
		                                                                                         // sobre 10.00
		assertEquals(precioEsperado, response.getLineasVenta().get(0).getPrecioUnitarioPromocionado());

		// Verificamos que el importe total se haya actualizado
		BigDecimal importeEsperado = precioEsperado.multiply(BigDecimal.valueOf(2)); // 2 artículos
		assertEquals(importeEsperado, response.getLineasVenta().get(0).getImporteTotal());
	}

	@Test
	public void obtenerPromocionPorIdTest() throws ApiException {
		PromocionEntity response = api.obtenerPromocionPorId(promocionId);
		assertNotNull(response);
		assertEquals(promocionId, response.getId());
	}

	@Test
	public void obtenerPromocionesTest() throws ApiException {
		List<PromocionEntity> promociones = api.obtenerPromociones();
		assertNotNull(promociones);
		assertFalse(promociones.isEmpty());
	}

	@Test
	public void eliminarPromocionTest() throws ApiException {
		api.eliminarPromocion(promocionId);

		try {
			api.obtenerPromocionPorId(promocionId);
			throw new AssertionError("La promoción debería haber sido eliminada");
		}
		catch (ApiException e) {
			// Esperamos un error porque la promoción ya no debería existir
			assertEquals(404, e.getCode()); // Ajusta si tu API devuelve otro código
		}
	}
}
