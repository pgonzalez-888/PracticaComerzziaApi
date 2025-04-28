package com.comerzzia.promocionesapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comerzzia.promocionesapi.entity.PromocionEntity;

@Repository
public interface PromocionRepository extends JpaRepository<PromocionEntity, String> {

	List<PromocionEntity> findByCodigoArticuloAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(String codigoArticulo, LocalDate fechaInicio, LocalDate fechaFin);
}
