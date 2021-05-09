package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.dto.StoreUnchangedDto;
import com.ssafy.thxstore.store.service.StoreService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// todo 주석처리, 예외처리 필요
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final ImageService imageService;

    // 스토어 생성
    @PostMapping
    public ResponseEntity createStore(@RequestHeader String authorization, @ModelAttribute CreateStoreFileDto createStoreFileDto){
        String email = jwtToEmail(authorization);
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(createStoreFileDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Store store = storeService.createStore(imgProfile, createStoreFileDto, email);
        //member 상태 변환

        WebMvcLinkBuilder selfLinkBuilder = linkTo(StoreController.class).slash(store.getId());
        URI createUri = selfLinkBuilder.toUri();
//        StoreResource storeResource = new StoreResource(store);
//        storeResource.add(linkTo(StoreController.class).withRel("create-store"));
//        storeResource.add(Link.of("/api/docs/index.html#resources-create-store").withRel("profile"));
//        return ResponseEntity.created(createUri).body(storeResource);
        return ResponseEntity.created(createUri).body(HttpStatus.CREATED);
    }

    /*판매자 스토어 페이지(스토어 정보 입력)*/
    // 스토어 상세 조회
    @GetMapping
    public ResponseEntity detailStore(@RequestHeader String authorization){
        System.out.println("zzzz");
        String email = jwtToEmail(authorization);
        System.out.println(email);
        Optional<Store> store = storeService.getStore(email);
        return ResponseEntity.created(null).body(store.get());
    }

    //스토어 정보 수정(개인)
    @PatchMapping
    public ResponseEntity patchStore(@RequestHeader String authorization, @ModelAttribute StoreChangedDto storeChangedDto){ // store_category, open_time, close_time, close_day, introduce, thumbnail_img, profile_img,
        String email = jwtToEmail(authorization);
        Store store = storeService.patchStore(email, storeChangedDto);
        return ResponseEntity.created(null).body(store);
    }

    // todo 에러로 주석 처리. -> 다음 주나 금요일에 수정 부분 시작
    // 스토어 정보 수정(불변) 불변 자료만 받아오자
    @PutMapping
    public ResponseEntity putStore(@RequestHeader String authorization, @ModelAttribute StoreUnchangedDto storeUnchangedDto){
        // todo : 가게가 존재하는지 확인하는 로직이 필요합니다.
        String email = jwtToEmail(authorization);
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(storeUnchangedDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Store store = storeService.getStore(email).get();
        store.setCheckStore(CheckStore.EDIT_WAITING);

        TempStore tempStore = TempStore.builder()
                .store(store)
                .name(storeUnchangedDto.getName())
                .mainAddress(storeUnchangedDto.getMainAddress())
                .subAddress(storeUnchangedDto.getSubAddress())
                .phoneNum(storeUnchangedDto.getPhoneNum())
                .license(storeUnchangedDto.getLicense())
                .licenseImg(imgProfile)
                .build();

        // 저장
        TempStore saveTempStore = storeService.tempStoreSave(tempStore);

        // TODO :
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }
    
    //스토어 상태 반환 신청 정보도?
//    @GetMapping("/status/")
//    public ResponseEntity statusStore(){
//        return ResponseEntity.created(null).body(null);
//    }

/* 스토어  관리(신청 목록)*/
    // 관리자 스토어 관리. 스토어 신청 리스트 반환---------------------------------------------------
    @GetMapping("/application/")
    public ResponseEntity storeApplicationList(@RequestHeader String authorization){
        List<Store> storeApplicationList = storeService.storeApplicationList();
        return ResponseEntity.created(null).body(storeApplicationList);
    }

    //스토어 신청 허가(관리자)
    @PostMapping("/application/success/")
    public ResponseEntity storeApplicationSuccess(@RequestHeader String authorization, @RequestParam(value="storeId") Long storeId){
        String email = jwtToEmail(authorization);
        // 스토어 변경
        Store store= storeService.storeApplicationSuccess(storeId, email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // 스토어 신청 실패(관리자) 매니저 -> 유저
    @PostMapping("/application/fail/")
    public ResponseEntity storeApplicationFail(@RequestHeader String authorization,  @RequestParam(value="storeId") Long storeId){
        // 스토어 삭제
        storeService.storeApplicationFail(storeId);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // 스토어 신청 실패 확인(판패자가 클릭)
    @PostMapping("/application/confirm/")
    public ResponseEntity storeApplicationConfirm(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        storeService.storeApplicationConfirm(email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    //스토어 수정 리스트--------------------------------------------------------
    @GetMapping("/modify/")
    public ResponseEntity storeModifyList(@RequestHeader String authorization){
        List<TempStore> storeModifyList = storeService.storeModifyList();
        return ResponseEntity.created(null).body(storeModifyList);
    }

    // 스토어 수정 허가(관리자)
    @PostMapping("/modify/success") // 수정이 필요
    public ResponseEntity storeModifySuccess(@RequestHeader String authorization, @RequestParam(value="tempStoreId") Long tempStoreId){

        storeService.tempStoreSucess(tempStoreId);


        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // 스토어 수정 실패(관리자)
    @PostMapping("/modify/fail") // 삭제가 필요
    public ResponseEntity storeModifyFail(@RequestHeader String authorization, @RequestParam(value="tempStoreId") Long tempStoreId){

        storeService.tempStoreFail(tempStoreId);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // 스토어 수정 실패 확인(판매자가 클릭)
    @PostMapping("/modify/confirm")
    public ResponseEntity storeModifyConfirm(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        storeService.editConfirm(email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
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

    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("aGVsbG90aHhzdG9yZWJhY2tlbmQK"))
                .parseClaimsJws(authorization).getBody().getSubject();
    }
}