package com.modsen.promocodeservice.repository;

import com.modsen.promocodeservice.model.PromoCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends MongoRepository<PromoCode, String> {
    boolean existsByValue(String value);

    Optional<PromoCode> findByValue(String value);
}
