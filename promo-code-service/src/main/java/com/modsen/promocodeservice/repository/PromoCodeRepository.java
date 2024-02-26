package com.modsen.promocodeservice.repository;

import com.modsen.promocodeservice.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode,Long> {
    boolean existsByValue(String value);
    Optional<PromoCode> findByValue(String value);
}
