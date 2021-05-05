package com.ssafy.thxstore.product.repository;

import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.TimeDeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeDealRepository extends JpaRepository<TimeDeal, Long> {
}
