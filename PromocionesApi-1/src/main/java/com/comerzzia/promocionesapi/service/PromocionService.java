package com.comerzzia.promocionesapi.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.promocionesapi.entity.LineaVenta;
import com.comerzzia.promocionesapi.entity.PromocionEntity;
import com.comerzzia.promocionesapi.entity.Ticket;
import com.comerzzia.promocionesapi.repository.PromocionRepository;

@Service
public class PromocionService {

	@Autowired
	private PromocionRepository promocionRepository;

	public Ticket aplicarPromociones(Ticket ticket) {
		LocalDate hoy = LocalDate.now();

		for (LineaVenta linea : ticket.getLineasVenta()) {
			// Obtener promociones candidatas
			List<PromocionEntity> candidatas = promocionRepository.findByCodigoArticuloAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(linea.getCodigoArticulo(), hoy, hoy);

			System.out.println("Promociones candidatas para el artículo " + linea.getCodigoArticulo() + ": " + candidatas.size());

			// Elegir la mejor promoción
			Optional<PromocionEntity> mejorPromocion = candidatas.stream().min(Comparator.comparing(promo -> promo.calcularPrecioAplicado(linea.getPrecioUnitarioOriginal())));

			if (mejorPromocion.isPresent()) {
				System.out.println("Aplicando promoción con ID: " + mejorPromocion.get().getId());
				linea.aplicarPromocion(mejorPromocion.get());
			}
			else {
				System.out.println("No se aplicó ninguna promoción a la línea: " + linea.getCodigoArticulo());
			}
		}

		return ticket;
	}

	// Crear una nueva promoción
	public PromocionEntity crearPromocion(PromocionEntity promocion) {
		return promocionRepository.save(promocion);
	}

	// Obtener todas las promociones
	public List<PromocionEntity> obtenerPromociones() {
		return promocionRepository.findAll();
	}

	// Obtener una promoción por ID
	public Optional<PromocionEntity> obtenerPromocionPorId(String id) {
		return promocionRepository.findById(id);
	}

	// Actualizar una promoción existente
	public PromocionEntity actualizarPromocion(String id, PromocionEntity promocion) {
		if (promocionRepository.existsById(id)) {
			promocion.setId(id);
			return promocionRepository.save(promocion);
		}
		else {
			throw new RuntimeException("Promoción no encontrada");
		}
	}

	// Eliminar una promoción
	public void eliminarPromocion(String id) {
		if (promocionRepository.existsById(id)) {
			promocionRepository.deleteById(id);
		}
		else {
			throw new RuntimeException("Promoción no encontrada");
		}
	}

}
