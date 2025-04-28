package com.comerzzia.promocionesapi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.comerzzia.promocionesapi.entity.PromocionEntity;
import com.comerzzia.promocionesapi.entity.Ticket;
import com.comerzzia.promocionesapi.service.PromocionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/promociones")
@Tag(name = "Promociones", description = "Operaciones relacionadas con promociones")
public class PromocionController {

	@Autowired
	private PromocionService promocionService;

	@Operation(summary = "Aplicar promociones a un ticket", description = "Aplica las promociones disponibles a un ticket dado y devuelve el ticket actualizado")
	@PostMapping("/aplicar")
	public ResponseEntity<Ticket> aplicarPromociones(@Valid @RequestBody Ticket ticket) {
		Ticket ticketPromocionado = promocionService.aplicarPromociones(ticket);
		return ResponseEntity.ok(ticketPromocionado);
	}

	@Operation(summary = "Crear una nueva promoción", description = "Crea una nueva promoción si no existe una con el mismo ID")
    @PostMapping
    public ResponseEntity<PromocionEntity> crearPromocion(@Valid @RequestBody PromocionEntity promocion) {
        try {
        	promocionService.obtenerPromocionPorId(promocion.getId());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                PromocionEntity nuevaPromocion = promocionService.crearPromocion(promocion);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPromocion);
            }
            throw e;
        }
    }

	@Operation(summary = "Obtener todas las promociones", description = "Devuelve una lista con todas las promociones disponibles")
	@GetMapping
	public ResponseEntity<List<PromocionEntity>> obtenerPromociones() {
		List<PromocionEntity> promociones = promocionService.obtenerPromociones();
		return ResponseEntity.ok(promociones);
	}

	@Operation(summary = "Obtener una promoción por ID", description = "Devuelve los detalles de una promoción específica dado su ID")
	@GetMapping("/{id}")
	public ResponseEntity<PromocionEntity> obtenerPromocionPorId(@PathVariable String id) {
	    try {
	        PromocionEntity promocion = promocionService.obtenerPromocionPorId(id);
	        return ResponseEntity.ok(promocion);
	    } catch (ResponseStatusException e) {
	        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	            return ResponseEntity.notFound().build();
	        }
	        throw e;
	    }
	}


	@Operation(summary = "Actualizar una promoción existente", description = "Actualiza los datos de una promoción ya existente utilizando su ID")
	@PutMapping("/{id}")
	public ResponseEntity<PromocionEntity> actualizarPromocion(@PathVariable String id, @Valid @RequestBody PromocionEntity promocion) {
		PromocionEntity promocionActualizada = promocionService.actualizarPromocion(id, promocion);
		return ResponseEntity.ok(promocionActualizada);
	}

	@Operation(summary = "Eliminar una promoción", description = "Elimina una promoción específica dado su ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarPromocion(@PathVariable String id) {
		promocionService.eliminarPromocion(id);
		return ResponseEntity.noContent().build();
	}

}
