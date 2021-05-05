package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;

    // 스토어 생성
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

    /*판매자 스토어 페이지(스토어 정보 입력)*/
    // 스토어 정보 가져오기
    public Optional<Store> getStore(Long storeId) {
        Optional<Store> store =  storeRepository.findById(storeId);
        return store;
    }

    // 스토어 개인정보 변경 개인
    public Store patchStore(StoreChangedDto storeChangedDto){
        Store store = storeRepository.findById(storeChangedDto.getStoreId()).get();
         // 정보 꺼내고
        // 가공
        // 하나씩 비교
        if(storeChangedDto.getStoreCategory() != null){
            if(storeChangedDto.getStoreCategory() == "CAFE")
                store.setStoreCategory(StoreCategory.CAFE);

        }else if(storeChangedDto.getOpenTime() != null){

        }else if(storeChangedDto.getCloseTime() != null){

        }else if(storeChangedDto.getCloseDay() != null){

        }else if(storeChangedDto.getIntroduce() != null){

        }else if(storeChangedDto.getThumbnailImg() != null){

        }else if(storeChangedDto.getProfileImg() != null){

        }


        return store;
    }

    // 스토어 정보 변경 불변



    // etc. 기타 시간간 문여는 시간 체크
    public boolean storeOpen(String times, int checkTime){

        return false;
    }

    //tes
    public Store createStoreTest(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);

        storeRepository.save(store);

        return store;
    }
}