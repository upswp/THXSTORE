package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final ImageService imageService;

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
        StoreResource storeResource = new StoreResource(store);
        storeResource.add(linkTo(StoreController.class).withRel("create-store"));
        storeResource.add(Link.of("/api/docs/index.html#resources-create-store").withRel("profile"));

        return ResponseEntity.created(createUri).body(storeResource);
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

    // 스토어 상세 조회
    @GetMapping
    public ResponseEntity detailStore(){
        return ResponseEntity.created(null).body(null);
    }

    //스토어 정보 수정(개인)
    @PatchMapping
    public ResponseEntity patchStore(){
        return ResponseEntity.created(null).body(null);
    }

    // 스토어 정보 수정(불변) 불변 자료만 받아오자
    @PutMapping
    public ResponseEntity putStore(){
        return ResponseEntity.created(null).body(null);
    }

    //스토어 상태 반환
    @GetMapping("/status/")
    public ResponseEntity statusStore(){
        return ResponseEntity.created(null).body(null);
    }
}