package com.comerzzia.promocionesapi.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comerzzia.promocionesapi.entity.LineaVenta;
import com.comerzzia.promocionesapi.entity.PromocionEntity;
import com.comerzzia.promocionesapi.entity.Ticket;
import com.comerzzia.promocionesapi.repository.PromocionRepository;

import jakarta.transaction.Transactional;

@Service
public class PromocionService {

	private static final Logger logger = LoggerFactory.getLogger(PromocionService.class);

	@Autowired
	private PromocionRepository promocionRepository;

	/**
	 * Aplica la mejor promoción vigente a cada línea de un ticket.
	 * 
	 * @param ticket
	 *            Ticket con las líneas de venta a evaluar.
	 * @return Ticket con promociones aplicadas (si las hay).
	 */
	public Ticket aplicarPromociones(Ticket ticket) {
		LocalDate hoy = LocalDate.now();

		for (LineaVenta linea : ticket.getLineasVenta()) {
			List<PromocionEntity> candidatas = promocionRepository.findByCodigoArticuloAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(linea.getCodigoArticulo(), hoy, hoy);
			logger.debug("Promociones candidatas para el artículo " + linea.getCodigoArticulo() + ": " + candidatas.size());

			Optional<PromocionEntity> mejorPromocion = candidatas.stream().min(Comparator.comparing(promo -> promo.calcularPrecioAplicado(linea.getPrecioUnitarioOriginal())));

			if (mejorPromocion.isPresent()) {
				logger.debug("Aplicando promoción con ID: " + mejorPromocion.get().getId());
				linea.aplicarPromocion(mejorPromocion.get());
			}
			else {
				logger.debug("No se aplicó ninguna promoción a la línea: " + linea.getCodigoArticulo());
			}
		}

		return ticket;
	}

	/**
	 * Crea una promoción.
	 * 
	 * @param promocion
	 *            PromocionEntity con los datos validados por el controller.
	 * @return PromocionEntity.
	 */
	@Transactional
    public PromocionEntity crearPromocion(PromocionEntity promocion) {
        validarPromocion(promocion);
        return promocionRepository.save(promocion);
    }

	/**
	 * Devuelve una lista de todas las promociones existentes.
	 * 
	 * @return List<PromocionEntity> de las promociones existentes.
	 */
	public List<PromocionEntity> obtenerPromociones() {
		return promocionRepository.findAll();
	}

	/**
	 * Devuelve una promoción que se consulta a traves de un id.
	 * 
	 * @param id
	 *            String con el codigo de identificación de la promoción.
	 * @return PromocionEntity que tenga el mismo id, si es que la hay
	 */
	public PromocionEntity obtenerPromocionPorId(String id) {
		return promocionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promoción no encontrada con ID: " + id));
	}

	/**
	 * Actualiza una promoción ya existente.
	 * 
	 * @param id
	 *            String, promocion PromocionEntity con el id para confirmar que existe y los datos de la promoción
	 * @return PromociónEntity tras ser guardada en base de datos.
	 */
	@Transactional
    public PromocionEntity actualizarPromocion(String id, PromocionEntity promocion) {
        validarPromocion(promocion);
        if (!id.equals(promocion.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "ID del path no coincide con el body");
        }
        
        return promocionRepository.findById(id)
                .map(existente -> {
                    promocion.setId(id);
                    return promocionRepository.save(promocion);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Promoción no encontrada con ID: " + id));
    }

	/**
	 * Elimina una promoción
	 * 
	 * @param id
	 *            String con el que buscar la promoción que se va a eliminar.
	 */
	@Transactional
    public void eliminarPromocion(String id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Promoción no encontrada con ID: " + id);
        }
        promocionRepository.deleteById(id);
    }

	private void validarPromocion(PromocionEntity promocion) {
		if (promocion.getCodigoArticulo() == null || promocion.getCodigoArticulo().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código de artículo es obligatorio");
		}
		if (promocion.getFechaInicio() == null || promocion.getFechaFin() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las fechas de inicio y fin son obligatorias");
		}
		if (promocion.getFechaFin().isBefore(promocion.getFechaInicio())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin debe ser posterior a la fecha inicio");
		}
	}
}
