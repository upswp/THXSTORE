package com.ssafy.thxstore.store.repository;

import com.ssafy.thxstore.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
