package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    Optional<List<ProductGroup>> findAllByStoreId(Long storeId);
}
