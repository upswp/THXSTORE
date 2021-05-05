package com.ssafy.thxstore.store.repository;

import com.ssafy.thxstore.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query(value = "select * from store where check_store= ?1 ", nativeQuery = true)
    List<Store> findByCheckStore(Integer status);
}
