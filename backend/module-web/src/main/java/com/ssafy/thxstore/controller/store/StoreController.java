package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;

    // root
    @PostMapping
    public ResponseEntity createStore(@Valid @RequestBody CreateStoreDto createStoreDto){
//@RequestHeader String authorization,
        // 인증 검사

        //

        Store store = storeService.createStore(createStoreDto);
//        System.out.println(store.getId());
//        System.out.println(store.getLicense());
//        System.out.println(store.getLicenseImg());


        WebMvcLinkBuilder selfLinkBuilder = linkTo(StoreController.class).slash(store.getId());
        URI createUri = selfLinkBuilder.toUri();
        StoreResource storeResource = new StoreResource(store);
        storeResource.add(linkTo(StoreController.class).withRel("create-store"));
        storeResource.add(Link.of("/api/docs/index.html#resources-create-store").withRel("profile"));

        return ResponseEntity.created(createUri).body(storeResource);
    }
}

/*
       Member newMember = memberService.registerMember(signUpRequest);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(newMember.getId());
        URI createUri = selfLinkBuilder.toUri();
        MemberResource memberResource = new MemberResource(newMember);
        memberResource.add(linkTo(MemberController.class).withRel("signUp-member"));
        memberResource.add(Link.of("/api/docs/index.html#resources-signUp-member").withRel("profile"));
        return ResponseEntity.created(createUri).body(memberResource);
 */
/*
memberResource.add(Link.of("/api/docs/index.html#resources-signUp-member").withRel("profile"));
        return ResponseEntity.created(createUri).body(memberResource);
 */