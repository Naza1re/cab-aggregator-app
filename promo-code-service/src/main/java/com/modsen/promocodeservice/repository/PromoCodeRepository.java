package com.modsen.promocodeservice.repository;

import com.modsen.promocodeservice.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode,Long> {
    boolean existsByValue(String value);
}
