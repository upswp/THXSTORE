package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    List<ProductGroup> findAllByStoreId(Long storeId);
}
