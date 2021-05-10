package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.*;
import com.ssafy.thxstore.store.service.StoreService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final ImageService imageService;
    private final AppProperties appProperties;

    @PostMapping // 스토어 생성
    public ResponseEntity createStore(@RequestHeader String authorization, @ModelAttribute CreateStoreFileDto createStoreFileDto){
        String email = jwtToEmail(authorization);
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(createStoreFileDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Store store = storeService.createStore(imgProfile, createStoreFileDto, email);
        return ResponseEntity.created(null).body(HttpStatus.CREATED);
    }

    @GetMapping // 스토어 상세 조회
    public ResponseEntity detailStore(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        Optional<Store> store = storeService.getStore(email);
        return ResponseEntity.created(null).body(store.get());
    }

    @PatchMapping//스토어 정보 수정(개인)
    public ResponseEntity patchStore(@RequestHeader String authorization, @ModelAttribute StoreChangedDto storeChangedDto){
        String email = jwtToEmail(authorization);
        Store store = storeService.patchStore(email, storeChangedDto);
        return ResponseEntity.created(null).body(store);
    }

    @PutMapping// 스토어 정보 수정(불변) 불변 자료만 받아오자
    public ResponseEntity putStore(@RequestHeader String authorization, @ModelAttribute StoreUnchangedDto storeUnchangedDto){
        // todo : 가게가 존재하는지 확인하는 로직이 필요합니다.
        String email = jwtToEmail(authorization);
        String imgProfile = null;
        try {
            imgProfile = imageService.createImage(storeUnchangedDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        storeService.putStore(email, storeUnchangedDto, imgProfile);

        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping("/application/") // 관리자 스토어 관리. 스토어 신청 리스트 반환
    public ResponseEntity storeApplicationList(@RequestHeader String authorization){
        List<Store> storeApplicationList = storeService.storeApplicationList();
        return ResponseEntity.created(null).body(storeApplicationList);
    }

    @PostMapping("/application/success/")//스토어 신청 허가(관리자)
    public ResponseEntity storeApplicationSuccess(@RequestHeader String authorization, @RequestBody StoreIdDto storeIdDto){
        String email = jwtToEmail(authorization);
        Store store= storeService.storeApplicationSuccess(storeIdDto.getStoreId(), email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/application/fail/") // 스토어 신청 실패(관리자) 매니저 -> 유저
    public ResponseEntity storeApplicationFail(@RequestHeader String authorization,  @RequestBody StoreIdDto storeIdDto){
        storeService.storeApplicationFail(storeIdDto.getStoreId());   // 스토어 삭제
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/application/confirm/") // 스토어 신청 실패 확인(판패자가 클릭)
    public ResponseEntity storeApplicationConfirm(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        storeService.storeApplicationConfirm(email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping("/modify/")//스토어 수정 리스트
    public ResponseEntity storeModifyList(@RequestHeader String authorization){
        List<TempStore> storeModifyList = storeService.storeModifyList();
        return ResponseEntity.created(null).body(storeModifyList);
    }

    @PostMapping("/modify/success") // 스토어 수정 허가(관리자)
    public ResponseEntity storeModifySuccess(@RequestHeader String authorization, @RequestBody TempStoreIdDto tempStoreId){
        storeService.tempStoreSucess(tempStoreId.getTempStoreId());
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/modify/fail") // 스토어 수정 실패(관리자)
    public ResponseEntity storeModifyFail(@RequestHeader String authorization, @RequestBody TempStoreIdDto tempStoreId){
        storeService.tempStoreFail(tempStoreId.getTempStoreId());
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/modify/confirm") // 스토어 수정 실패 확인(판매자가 클릭)
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
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }
}