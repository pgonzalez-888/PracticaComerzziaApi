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
		logger.info("Applying promotions to ticket with {} sale lines", ticket.getSaleLines().size());
		LocalDate today = LocalDate.now();

		for (SaleLine line : ticket.getSaleLines()) {
			logger.debug("Processing line with itemCode: {}", line.getItemCode());

			List<Promotion> applicablePromotions = promotionRepository.findByItemCodeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(line.getItemCode(), today, today);

			logger.debug("Found {} applicable promotions for itemCode {}", applicablePromotions.size(), line.getItemCode());
			applyBestPromotion(line, applicablePromotions);
		}

		logger.info("Finished applying promotions");
		return ticket;
	}

	private void applyBestPromotion(SaleLine line, List<Promotion> applicablePromotions) {
		if (applicablePromotions.isEmpty()) {
			logger.debug("No promotions applicable for itemCode {}, calculating price without promotion", line.getItemCode());
			line.calculatePriceWithoutPromotion();
			return;
		}

		if (applicablePromotions.size() == 1) {
			logger.debug("Applying single promotion to itemCode {}", line.getItemCode());
			line.applyPromotion(applicablePromotions.get(0));
			return;
		}

		logger.debug("Multiple promotions found for itemCode {}, selecting best", line.getItemCode());
		Promotion bestPromo = findBestPromotion(line, applicablePromotions);
		line.applyPromotion(bestPromo);
		logger.debug("Applied best promotion with ID {} to itemCode {}", bestPromo.getId(), line.getItemCode());
	}

	private Promotion findBestPromotion(SaleLine line, List<Promotion> promotions) {
		return promotions.stream().max(Comparator.comparing(promo -> line.simulatePromotionDiscount(promo))).orElseThrow(() -> {
			logger.error("Could not determine best promotion for itemCode {}", line.getItemCode());
			return new IllegalStateException("No se pudo determinar la mejor promoción");
		});
	}

	@Override
	@Transactional
	public Promotion createPromotion(Promotion promotion) {
		logger.info("Creating promotion with ID: {}", promotion.getId());
		validatePromotion(promotion);
		Promotion saved = promotionRepository.save(promotion);
		logger.info("Promotion created with ID: {}", saved.getId());
		return saved;
	}

	@Override
	public List<Promotion> getAllPromotions() {
		logger.info("Retrieving all promotions");
		List<Promotion> promotions = promotionRepository.findAll();
		logger.debug("Retrieved {} promotions", promotions.size());
		return promotions;
	}

	@Override
	public Promotion getPromotionById(String id) {
		logger.info("Retrieving promotion with ID: {}", id);
		return promotionRepository.findById(id).orElseThrow(() -> {
			logger.warn("Promotion not found with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id);
		});
	}

	@Override
	@Transactional
	public Promotion updatePromotion(String id, Promotion promotion) {
		logger.info("Updating promotion with ID: {}", id);
		validatePromotion(promotion);
		if (!id.equals(promotion.getId())) {
			logger.warn("Path ID ({}) does not match body ID ({})", id, promotion.getId());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path ID does not match body ID");
		}

		return promotionRepository.findById(id).map(existing -> {
			promotion.setId(id);
			Promotion updated = promotionRepository.save(promotion);
			logger.info("Promotion updated with ID: {}", updated.getId());
			return updated;
		}).orElseThrow(() -> {
			logger.warn("Promotion not found for update with ID: {}", id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id);
		});
	}

	@Override
	@Transactional
	public void deletePromotion(String id) {
		logger.info("Deleting promotion with ID: {}", id);
		if (!promotionRepository.existsById(id)) {
			logger.warn("Promotion not found for deletion with ID: {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found with ID: " + id);
		}
		promotionRepository.deleteById(id);
		logger.info("Promotion deleted with ID: {}", id);
	}

	private void validatePromotion(Promotion promotion) {
		logger.debug("Validating promotion with ID: {}", promotion.getId());
		if (promotion.getItemCode() == null || promotion.getItemCode().isBlank()) {
			logger.error("Item code is required for promotion");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item code is required");
		}
		if (promotion.getStartDate() == null || promotion.getEndDate() == null) {
			logger.error("Start and end dates are required for promotion");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end dates are required");
		}
		if (promotion.getEndDate().isBefore(promotion.getStartDate())) {
			logger.error("End date must be after start date for promotion");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date");
		}
	}
}
