package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
