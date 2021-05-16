package com.ssafy.thxstore.store.repository;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.StoreAndDistanceDto;
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

    // 위도 lat = 37.xxx, 경도 lon = 126.xxx
    // 조심StoreAndDistanceDto? store? test
    @Query(value = "SELECT *, "+
            "(6371*acos(cos(radians(?1))*cos(radians(lat))*cos(radians(lon) "+
            "-radians(?2))+sin(radians(?1))*sin(radians(lat)))) AS distance "+
            " FROM store HAVING distance <= ?3 ORDER BY distance LIMIT 0,300 ", nativeQuery = true)
    Optional<List<Store>> findByLocation(Double lat, Double lon, Double distance);

    @Query("select s" +
            " from Store s join s.member m " +
            " where s.member.email = :email")
    Optional<Store> findByEmailJoin(String email);

}
