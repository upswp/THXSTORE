package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;

    public Store createStore(CreateStoreDto createStoreDto) {

        // 파일 저장장

       Store store = modelMapper.map(createStoreDto, Store.class);

        System.out.println(store.getName());
        System.out.println(store.getSubAddress());
        System.out.println(store.getMainAddress());
        System.out.println(store.getLicenseImg());
        System.out.println(store.getLicense());
        System.out.println(store.getPhoneNum());
        System.out.println(store.getId());

        storeRepository.save(store);

        return store;
    }
}
