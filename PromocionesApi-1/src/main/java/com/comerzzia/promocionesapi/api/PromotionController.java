package com.comerzzia.promocionesapi.api;

import java.util.List;
import java.util.stream.Collectors;

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

import com.comerzzia.promocionesapi.dto.PromotionDTO;
import com.comerzzia.promocionesapi.dto.TicketDTO;
import com.comerzzia.promocionesapi.mappers.PromotionMapper;
import com.comerzzia.promocionesapi.mappers.TicketMapper;
import com.comerzzia.promocionesapi.model.Promotion;
import com.comerzzia.promocionesapi.model.Ticket;
import com.comerzzia.promocionesapi.service.PromotionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/promotions")
@Tag(name = "Promotions", description = "Operations related to promotions")
public class PromotionController {

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private PromotionMapper promotionMapper;

	@Autowired
	private TicketMapper ticketMapper;

	@Operation(summary = "Apply promotions to a ticket", description = "Applies available promotions to the given ticket and returns the updated ticket.")
	@PostMapping("/apply")
	public ResponseEntity<TicketDTO> applyPromotions(@Valid @RequestBody TicketDTO ticketDTO) {
		Ticket ticket = ticketMapper.fromDTO(ticketDTO);
		Ticket discountedTicket = promotionService.applyPromotions(ticket);
		TicketDTO updatedTicketDTO = ticketMapper.toDTO(discountedTicket);
		return ResponseEntity.ok(updatedTicketDTO);
	}

	@Operation(summary = "Create a new promotion", description = "Creates a new promotion if it does not already exist.")
	@PostMapping
	public ResponseEntity<PromotionDTO> createPromotion(@Valid @RequestBody PromotionDTO promotionDTO) {
		try {
			Promotion promotion = promotionMapper.fromDTO(promotionDTO);
			promotionService.getPromotionById(promotion.getId());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		catch (Exception e) {
			Promotion newPromotion = promotionService.createPromotion(promotionMapper.fromDTO(promotionDTO));
			PromotionDTO newPromotionDTO = promotionMapper.toDTO(newPromotion);
			return ResponseEntity.ok(newPromotionDTO);
		}
	}

	@Operation(summary = "Get all promotions", description = "Retrieves a list of all configured promotions.")
	@GetMapping
	public ResponseEntity<List<PromotionDTO>> getAllPromotions() {
		List<Promotion> promotions = promotionService.getAllPromotions();
		List<PromotionDTO> promotionsDTO = promotions.stream().map(promotionMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(promotionsDTO);
	}

	@Operation(summary = "Get promotion by ID", description = "Retrieves the promotion with the specified ID.")
	@GetMapping("/{id}")
	public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable String id) {
		try {
			Promotion promotion = promotionService.getPromotionById(id);
			PromotionDTO promotionDTO = promotionMapper.toDTO(promotion);
			return ResponseEntity.ok(promotionDTO);
		}
		catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Update an existing promotion", description = "Updates the promotion with the given ID.")
	@PutMapping("/{id}")
	public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable String id, @Valid @RequestBody PromotionDTO promotionDTO) {
		Promotion promotion = promotionMapper.fromDTO(promotionDTO);
		Promotion updatedPromotion = promotionService.updatePromotion(id, promotion);
		PromotionDTO updatedPromotionDTO = promotionMapper.toDTO(updatedPromotion);
		return ResponseEntity.ok(updatedPromotionDTO);
	}

	@Operation(summary = "Delete a promotion", description = "Deletes the promotion with the specified ID.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePromotion(@PathVariable String id) {
		promotionService.deletePromotion(id);
		return ResponseEntity.noContent().build();
	}
}
