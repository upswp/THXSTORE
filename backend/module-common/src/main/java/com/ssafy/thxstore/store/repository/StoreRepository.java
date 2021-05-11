package com.ssafy.thxstore.store.repository;

import com.ssafy.thxstore.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {

    // todo jpa 메소드로 변경
    @Query(value = "select * from store where check_store= ?1 ", nativeQuery = true)
    List<Store> findByCheckStore(Integer status);

    Optional<Store> findByMemberId(Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update store set time_deal_check = 0 ",nativeQuery = true)
    void updateStoreTimeDealCHeck();
}
