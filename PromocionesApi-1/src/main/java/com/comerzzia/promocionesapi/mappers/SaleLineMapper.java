package com.comerzzia.promocionesapi.mappers;

import org.springframework.stereotype.Component;

import com.comerzzia.promocionesapi.dto.SaleLineDTO;
import com.comerzzia.promocionesapi.model.SaleLine;

@Component
public class SaleLineMapper {

    public SaleLineDTO toDTO(SaleLine line) {
        SaleLineDTO dto = new SaleLineDTO();
        dto.setItemCode(line.getItemCode());
        dto.setQuantity(line.getQuantity());
        dto.setOriginalUnitPrice(line.getOriginalUnitPrice());
        dto.setDiscountedUnitPrice(line.getDiscountedUnitPrice());
        dto.setTotalAmount(line.getTotalAmount());
        dto.setAppliedPromotionId(line.getAppliedPromotionId());
        return dto;
    }

    public SaleLine fromDTO(SaleLineDTO dto) {
        SaleLine line = new SaleLine();
        line.setItemCode(dto.getItemCode());
        line.setQuantity(dto.getQuantity());
        line.setOriginalUnitPrice(dto.getOriginalUnitPrice());
        return line;
    }
}

