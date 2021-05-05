package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;

    private final ImageService imageService;

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
            // 1. switch(): 2. if
            //store.setStoreCategory();
            //빌더로 수정?
            store.setStoreCategory(StoreCategory.valueOf(storeChangedDto.getStoreCategory()));
        }else if(storeChangedDto.getOpenTime() != null){
            store.setOpenTime(storeChangedDto.getOpenTime());

        }else if(storeChangedDto.getCloseTime() != null){
            store.setCloseTime(storeChangedDto.getCloseTime());

        }else if(storeChangedDto.getCloseDay() != null){
            store.setClosedDay(storeChangedDto.getCloseDay());

        }else if(storeChangedDto.getIntroduce() != null){
            store.setIntroduce(storeChangedDto.getIntroduce());

        }else if(storeChangedDto.getThumbnailImg() != null){
            // 파일
            String imgProfile = null;
            try {
                imgProfile = imageService.createImage(storeChangedDto.getThumbnailImg());
            }catch (IOException e) {
                return null;
            }
            store.setThumbImg(imgProfile);

        }else if(storeChangedDto.getProfileImg() != null){
            // 파일
            String imgProfile = null;
            try {
                imgProfile = imageService.createImage(storeChangedDto.getProfileImg());
            }catch (IOException e) {
                return null;
            }
            store.setThumbImg(imgProfile);
        }

        return store;
    }

    // 스토어 정보 변경 불변
    public Store putStore(StoreChangedDto storeChangedDto) {
        return null;
    }


    /* 스토어  관리(신청 목록)*/
    // 스토어 신청 리스트
    public List<Store> storeApplicationList() {
        List<Store> store = storeRepository.findByStoreCategory("APPLICATION_WAITING");
        return store;
    }

    // 스토어 수정 리스트


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


    public List<Store> storeModifyList() {
        List<Store> store = storeRepository.findByStoreCategory("EDIT_WAITING");
        return store;
    }
}