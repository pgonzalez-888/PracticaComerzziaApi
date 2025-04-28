package org.openapitools.client.api;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    }

    @Test
    public void crearPromocionInvalidaTest() {
        PromocionEntity promocionInvalida = new PromocionEntity();
        
        try {
            api.crearPromocion(promocionInvalida);
            fail("Se esperaba ApiException");
        } catch (ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test
    public void actualizarPromocionTest() throws ApiException {
        PromocionEntity promocionActualizada = new PromocionEntity();
        promocionActualizada.setId(promocionId);
        promocionActualizada.setDescripcion("Actualizado");
        promocionActualizada.setPorcentajeDescuento(BigDecimal.valueOf(30));
        // Campos obligatorios añadidos:
        promocionActualizada.setCodigoArticulo("EO");
        promocionActualizada.setFechaInicio(LocalDate.now());
        promocionActualizada.setFechaFin(LocalDate.now().plusDays(1));
        promocionActualizada.setTipo(TipoEnum.DESCUENTO);

        PromocionEntity response = api.actualizarPromocion(promocionId, promocionActualizada);
        assertEquals("Actualizado", response.getDescripcion());
    }

    @Test
    public void actualizarPromocionInexistenteTest() {
        String idInexistente = "NO_EXISTE";
        PromocionEntity promocion = new PromocionEntity();
        promocion.setId(idInexistente);
        // Campos obligatorios añadidos:
        promocion.setCodigoArticulo("ART");
        promocion.setFechaInicio(LocalDate.now());
        promocion.setFechaFin(LocalDate.now().plusDays(1));
        promocion.setTipo(TipoEnum.DESCUENTO);

        try {
            api.actualizarPromocion(idInexistente, promocion);
            fail("Se esperaba ApiException");
        } catch (ApiException e) {
            assertEquals(404, e.getCode()); // Ahora debería dar 404, no 400
        }
    }

    @Test
    public void obtenerPromocionPorIdTest() throws ApiException {
        PromocionEntity response = api.obtenerPromocionPorId(promocionId);
        assertEquals(promocionId, response.getId());
    }

    @Test
    public void obtenerPromocionInexistenteTest() {
        String idInexistente = "NO_EXISTE";
        try {
            api.obtenerPromocionPorId(idInexistente);
            fail("Se esperaba ApiException");
        } catch (ApiException e) {
            assertEquals(404, e.getCode());
        }
    }

    @Test
    public void aplicarPromocionesTest() throws ApiException {
        Ticket ticket = new Ticket();
        ticket.setId("TICKET_TEST");
        LineaVenta linea = new LineaVenta();
        linea.setCodigoArticulo("EO");
        linea.setPrecioUnitarioOriginal(BigDecimal.valueOf(100));
        linea.setCantidad(1); // Campo importante añadido
        ticket.addLineasVentaItem(linea);

        Ticket resultado = api.aplicarPromociones(ticket);
        assertEquals(0, resultado.getLineasVenta().get(0).getPrecioUnitarioPromocionado()
                .compareTo(BigDecimal.valueOf(70)));
    }

    @Test
    public void eliminarPromocionTest() throws ApiException {
        api.eliminarPromocion(promocionId);
        try {
            api.obtenerPromocionPorId(promocionId);
            fail("Se esperaba ApiException");
        } catch (ApiException e) {
            assertEquals(404, e.getCode());
        }
    }

    @Test
    public void eliminarPromocionInexistenteTest() {
        try {
            api.eliminarPromocion("NO_EXISTE");
            fail("Se esperaba ApiException");
        } catch (ApiException e) {
            assertEquals(404, e.getCode());
        }
    }
}