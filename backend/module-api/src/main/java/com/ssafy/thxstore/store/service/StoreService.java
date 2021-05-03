package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;

    public Store createStore(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);

        storeRepository.save(store);

        return store;
    }
}
