package com.comerzzia.promocionesapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comerzzia.promocionesapi.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

    List<Promotion> findByItemCodeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String itemCode, LocalDate startDate, LocalDate endDate);
}
