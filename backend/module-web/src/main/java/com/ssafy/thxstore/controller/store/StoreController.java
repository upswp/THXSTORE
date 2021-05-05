package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.dto.StoreUnchangedDto;
import com.ssafy.thxstore.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final ImageService imageService;

    // 스토어 생성
    @PostMapping
    public ResponseEntity createStore(@ModelAttribute CreateStoreFileDto createStoreFileDto){
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(createStoreFileDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Store store = storeService.createStore(imgProfile, createStoreFileDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(StoreController.class).slash(store.getId());
        URI createUri = selfLinkBuilder.toUri();
//        StoreResource storeResource = new StoreResource(store);
//        storeResource.add(linkTo(StoreController.class).withRel("create-store"));
//        storeResource.add(Link.of("/api/docs/index.html#resources-create-store").withRel("profile"));
//
//        return ResponseEntity.created(createUri).body(storeResource);
        return ResponseEntity.created(createUri).body(store);
    }

    /*판매자 스토어 페이지(스토어 정보 입력)*/
    // 스토어 상세 조회
    @GetMapping
    public ResponseEntity detailStore(Long storeId){
        Optional<Store> store = storeService.getStore(storeId);
        return ResponseEntity.created(null).body(store.get());
    }

    //스토어 정보 수정(개인)
    @PatchMapping
    public ResponseEntity patchStore(@RequestBody StoreChangedDto storeChangedDto){ // store_category, open_time, close_time, close_day, introduce, thumbnail_img, profile_img,
        Store store = storeService.patchStore(storeChangedDto);
        return ResponseEntity.created(null).body(store);
    }

    // 스토어 정보 수정(불변) 불변 자료만 받아오자
    @PutMapping
    public ResponseEntity putStore(@RequestBody StoreUnchangedDto storeUnchangedDto){
        // store_id, name, main_address, sub_address, phone_num, license, licesne_img
        // temp_store 하나 만들어 주고,
        // store 상태 변환
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(storeUnchangedDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Store> store = storeService.getStore(storeUnchangedDto.getStoreId());

        TempStore tempStore = TempStore.builder()
                .store(store.get())
                .name(storeUnchangedDto.getName())
                .mainAddress(storeUnchangedDto.getMainAddress())
                .subAddress(storeUnchangedDto.getSubAddress())
                .phoneNum(storeUnchangedDto.getPhoneNum())
                .license(storeUnchangedDto.getLicense())
                .licenseImg(imgProfile)
                .build();

        // 저장, 저장 repository 생성

        //store 상태 변환

        return ResponseEntity.created(null).body(null);
    }

    //스토어 상태 반환 신청 정보도?
//    @GetMapping("/status/")
//    public ResponseEntity statusStore(){
//        return ResponseEntity.created(null).body(null);
//    }




/* 스토어  관리(신청 목록)*/
    // 관리자 스토어 관리. 신청리스트 반환---------------------------------------------------
    @GetMapping("/application/")
    public ResponseEntity storeApplicationList(){
        List<Store> storeApplicationList = storeService.storeApplicationList();
        return ResponseEntity.created(null).body(storeApplicationList);
    }

    //스토어 신청 허가(관리자)
    @PostMapping("/application/success")
    public ResponseEntity storeApplicationSuccess(){
        return ResponseEntity.created(null).body(null);
    }

    // 스토어 신청 실패(관리자)
    @PostMapping("/application/fail")
    public ResponseEntity storeApplicationFail(){
        return ResponseEntity.created(null).body(null);
    }

    // 스토어 신청 실패 확인(판패자가 클릭)
    @PostMapping("/application/confirm")
    public ResponseEntity storeApplicationConfirm(){
        return ResponseEntity.created(null).body(null);
    }

    //스토어 수정 리스트--------------------------------------------------------
    @GetMapping("/modify/")
    public ResponseEntity storeModifyList(){
        List<Store> storeModifyList = storeService.storeModifyList();
        return ResponseEntity.created(null).body(storeModifyList);
    }

    // 스토어 수정 허가(관리자)
    @PostMapping("/modify/success")
    public ResponseEntity storeModifySuccess(){
        return ResponseEntity.created(null).body(null);
    }

    // 스토어 수정 실패(관리자)
    @PostMapping("/modify/fail")
    public ResponseEntity storeModifyFail(){
        return ResponseEntity.created(null).body(null);
    }

    // 스토어 수정 실패 확인(판매자가 클릭)
    @PostMapping("/modify/confirm")
    public ResponseEntity storeModifyConfirm(){
        return ResponseEntity.created(null).body(null);
    }



    @PostMapping("/test/")
    public ResponseEntity createStoreTest(@RequestBody CreateStoreDto createStoreDto){


        Store store = storeService.createStoreTest(createStoreDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(StoreController.class).slash(store.getId());
        URI createUri = selfLinkBuilder.toUri();
        StoreResource storeResource = new StoreResource(store);
        storeResource.add(linkTo(StoreController.class).withRel("create-store"));
        storeResource.add(Link.of("/api/docs/index.html#resources-create-store").withRel("profile"));

        return ResponseEntity.created(createUri).body(storeResource);
    }
}