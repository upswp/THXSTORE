package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.TimeDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeDealRepository extends JpaRepository<TimeDeal, Long> {
    Optional<TimeDeal> findByStoreId(Long storeId);

    Optional<List<TimeDeal>> findAllByStoreId(Long storeId);
}
