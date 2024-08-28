package org.example.hiringdriverservice.repository;

import org.example.hiringdriverservice.model.DriverApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverApplyRepository extends JpaRepository<DriverApply,Long> {

    Optional<DriverApply> findByRequesterId(Long requesterId);
}
