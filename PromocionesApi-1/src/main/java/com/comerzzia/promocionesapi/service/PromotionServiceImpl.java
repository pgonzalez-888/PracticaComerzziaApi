package com.comerzzia.promocionesapi.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comerzzia.promocionesapi.model.Promotion;
import com.comerzzia.promocionesapi.model.SaleLine;
import com.comerzzia.promocionesapi.model.Ticket;
import com.comerzzia.promocionesapi.repository.PromotionRepository;

import jakarta.transaction.Transactional;

@Service
public class PromotionServiceImpl implements PromotionService {

	private static final Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);

	@Autowired
	private PromotionRepository promotionRepository;

	@Override
	public Ticket applyPromotions(Ticket ticket) {
	    LocalDate today = LocalDate.now();
	    
	    for (SaleLine line : ticket.getSaleLines()) {
	        // Usar el método real del repositorio:
	        List<Promotion> applicablePromotions = promotionRepository
	            .findByItemCodeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
	                line.getItemCode(), 
	                today, 
	                today
	            );
	        
	        applyBestPromotion(line, applicablePromotions);
	    }
	    
	    return ticket;
	}

    private void applyBestPromotion(SaleLine line, List<Promotion> applicablePromotions) {
        if (applicablePromotions.isEmpty()) {
            line.calculatePriceWithoutPromotion(); // Método existente en SaleLine
            return;
        }

        // Si solo hay una promoción, aplicarla directamente
        if (applicablePromotions.size() == 1) {
            line.applyPromotion(applicablePromotions.get(0)); // Método existente
            return;
        }

        // Si hay múltiples, comparar y aplicar la mejor
        Promotion bestPromo = findBestPromotion(line, applicablePromotions);
        line.applyPromotion(bestPromo); // Método existente
    }

    private Promotion findBestPromotion(SaleLine line, List<Promotion> promotions) {
        return promotions.stream()
            .max(Comparator.comparing(promo -> 
                line.simulatePromotionDiscount(promo) // Método existente
            ))
            .orElseThrow(() -> new IllegalStateException("No se pudo determinar la mejor promoción"));
    }

	@Override
	@Transactional
	public Promotion createPromotion(Promotion promotion) {
		validatePromotion(promotion);
		return promotionRepository.save(promotion);
	}

	@Override
	public List<Promotion> getAllPromotions() {
		return promotionRepository.findAll();
	}

	@Override
	public Promotion getPromotionById(String id) {
		return promotionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id));
	}

	@Override
	@Transactional
	public Promotion updatePromotion(String id, Promotion promotion) {
		validatePromotion(promotion);
		if (!id.equals(promotion.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path ID does not match body ID");
		}

		return promotionRepository.findById(id).map(existing -> {
			promotion.setId(id);
			return promotionRepository.save(promotion);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id));
	}

	@Override
	@Transactional
	public void deletePromotion(String id) {
		if (!promotionRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id);
		}
		promotionRepository.deleteById(id);
	}

	private void validatePromotion(Promotion promotion) {
		if (promotion.getItemCode() == null || promotion.getItemCode().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item code is required");
		}
		if (promotion.getStartDate() == null || promotion.getEndDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end dates are required");
		}
		if (promotion.getEndDate().isBefore(promotion.getStartDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date");
		}
	}
}