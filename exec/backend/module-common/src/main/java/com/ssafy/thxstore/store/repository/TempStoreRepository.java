package com.ssafy.thxstore.store.repository;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.TempStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempStoreRepository extends JpaRepository<TempStore,Long> {

}
