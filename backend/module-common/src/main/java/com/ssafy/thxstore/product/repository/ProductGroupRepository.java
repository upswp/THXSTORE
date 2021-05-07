package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
}
