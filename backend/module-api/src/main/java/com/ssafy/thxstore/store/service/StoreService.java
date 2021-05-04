package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
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

    public Store createStore(String imgProfile, CreateStoreFileDto createStoreFileDto) {
        // 파일 저장
        CreateStoreDto createStoreDto = CreateStoreDto.builder()
                .name(createStoreFileDto.getName())
                .mainAddress(createStoreFileDto.getMainAddress())
                .subAddress(createStoreFileDto.getSubAddress())
                .phoneNum(createStoreFileDto.getPhoneNum())
                .license(createStoreFileDto.getLicense())
                .licenseImg(imgProfile)
                .build();

       Store store = modelMapper.map(createStoreDto, Store.class);

        storeRepository.save(store);

        return store;
    }


    public Store createStoreTest(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);

        storeRepository.save(store);

        return store;
    }
}