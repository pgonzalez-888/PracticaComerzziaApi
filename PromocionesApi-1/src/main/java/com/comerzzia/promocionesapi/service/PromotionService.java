package com.comerzzia.promocionesapi.service;

import java.util.List;

import com.comerzzia.promocionesapi.model.Promotion;
import com.comerzzia.promocionesapi.model.Ticket;

public interface PromotionService {

    Ticket applyPromotions(Ticket ticket);

    Promotion createPromotion(Promotion promotion);

    List<Promotion> getAllPromotions();

    Promotion getPromotionById(String id);

    Promotion updatePromotion(String id, Promotion promotion);

    void deletePromotion(String id);
}

