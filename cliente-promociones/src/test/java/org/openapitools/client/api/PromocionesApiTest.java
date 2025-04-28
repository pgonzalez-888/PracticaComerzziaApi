package org.openapitools.client.api;

import static org.junit.jupiter.api.Assertions.*;

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
		promocionId = "PROMO_" + UUID.randomUUID().toString().substring(0, 8);

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
		PromocionEntity promocion = api.obtenerPromocionPorId(promocionId);

		assertNotNull(promocion);
		assertEquals(promocionId, promocion.getId());
		assertEquals(0, promocion.getPorcentajeDescuento().compareTo(BigDecimal.valueOf(20)));
	}

	@Test
	public void actualizarPromocionTest() throws ApiException {
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
		assertEquals(0, response.getPorcentajeDescuento().compareTo(BigDecimal.valueOf(25)));
	}

	@Test
	public void aplicarPromocionesTest() throws ApiException {
		Ticket ticket = new Ticket();
		ticket.setId("TICKET_" + UUID.randomUUID().toString().substring(0, 8));
		LineaVenta linea = new LineaVenta();
		linea.setCodigoArticulo("EO");
		linea.setCantidad(2);
		linea.setPrecioUnitarioOriginal(BigDecimal.valueOf(10.00));
		ticket.addLineasVentaItem(linea);

		Ticket response = api.aplicarPromociones(ticket);

		BigDecimal precioEsperado = BigDecimal.valueOf(7.50).setScale(3, RoundingMode.HALF_UP); // 25% de descuento
		assertEquals(0, response.getLineasVenta().get(0).getPrecioUnitarioPromocionado().compareTo(precioEsperado));

		BigDecimal importeEsperado = precioEsperado.multiply(BigDecimal.valueOf(2));
		assertEquals(0, response.getLineasVenta().get(0).getImporteTotal().compareTo(importeEsperado));
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
			fail("Se esperaba un ApiException porque la promoción fue eliminada");
		}
		catch (ApiException e) {
			assertTrue(e.getCode() == 404 || e.getCode() == 500, "Esperado error 404 o 500, pero fue: " + e.getCode());
		}
	}

	@Test
	public void eliminarPromocionInexistenteTest() {
		String idInexistente = "PROMO_NO_EXISTE";

		try {
			api.eliminarPromocion(idInexistente);
			fail("Se esperaba un ApiException al eliminar una promoción inexistente");
		}
		catch (ApiException e) {
			assertTrue(e.getCode() == 404 || e.getCode() == 500, "Esperado error 404 o 500, pero fue: " + e.getCode());
		}
	}
}
