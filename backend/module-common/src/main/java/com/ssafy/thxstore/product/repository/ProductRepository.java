package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByGroupId(Long groupId);
}
