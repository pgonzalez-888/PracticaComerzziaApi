package com.comerzzia.promocionesapi.mappers;

import org.springframework.stereotype.Component;

import com.comerzzia.promocionesapi.dto.DiscountDetails;
import com.comerzzia.promocionesapi.dto.FixedPriceDetails;
import com.comerzzia.promocionesapi.dto.PromotionDTO;
import com.comerzzia.promocionesapi.model.DiscountPromotion;
import com.comerzzia.promocionesapi.model.FixedPricePromotion;
import com.comerzzia.promocionesapi.model.Promotion;

@Component
public class PromotionMapper {

    public Promotion fromDTO(PromotionDTO dto) {
        if (dto == null) {
            return null;
        }

        Promotion promotion;
        
        if ("DISCOUNT".equals(dto.getType())) {
            DiscountPromotion discountPromotion = new DiscountPromotion();
            discountPromotion.setDiscountPercentage(dto.getDiscountDetails().getPercentage());
            promotion = discountPromotion;
        } else if ("FIXED_PRICE".equals(dto.getType())) {
            FixedPricePromotion fixedPricePromotion = new FixedPricePromotion();
            fixedPricePromotion.setFixedPrice(dto.getFixedPriceDetails().getPrice());
            promotion = fixedPricePromotion;
        } else {
            throw new IllegalArgumentException("Tipo de promoción no válido: " + dto.getType());
        }

        // Campos comunes
        promotion.setId(dto.getId());
        promotion.setDescription(dto.getDescription());
        promotion.setCreationDate(dto.getCreationDate());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        promotion.setItemCode(dto.getItemCode());

        return promotion;
    }

    public PromotionDTO toDTO(Promotion entity) {
        if (entity == null) {
            return null;
        }

        PromotionDTO dto = new PromotionDTO();
        // Campos comunes
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setCreationDate(entity.getCreationDate());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setItemCode(entity.getItemCode());

        if (entity instanceof DiscountPromotion) {
            dto.setType("DISCOUNT");
            DiscountDetails details = new DiscountDetails();
            details.setPercentage(((DiscountPromotion) entity).getDiscountPercentage());
            dto.setDiscountDetails(details);
        } else if (entity instanceof FixedPricePromotion) {
            dto.setType("FIXED_PRICE");
            FixedPriceDetails details = new FixedPriceDetails();
            details.setPrice(((FixedPricePromotion) entity).getFixedPrice());
            dto.setFixedPriceDetails(details);
        }

        return dto;
    }
}
