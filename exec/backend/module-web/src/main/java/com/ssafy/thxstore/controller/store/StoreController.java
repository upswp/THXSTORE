package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import com.ssafy.thxstore.product.dto.*;
import com.ssafy.thxstore.product.service.ProductService;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.*;
import com.ssafy.thxstore.store.service.StoreService;
import io.jsonwebtoken.Jwts;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final ImageService imageService;
    private final AppProperties appProperties;
    private final ProductService productService;

    @PostMapping // ????????? ??????
    public ResponseEntity createStore(@RequestHeader String authorization, @ModelAttribute CreateStoreFileDto createStoreFileDto){
        String email = jwtToEmail(authorization);
        String imgProfile = null;
        if(createStoreFileDto.getLicenseImg() == null){}
        try {
            imgProfile = imageService.createImage(createStoreFileDto.getLicenseImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Store store = storeService.createStore(imgProfile, createStoreFileDto, email);
        return ResponseEntity.created(null).body(HttpStatus.CREATED);
    }

    @GetMapping // ????????? ?????? ??????
    public ResponseEntity detailStore(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        Optional<Store> store = storeService.getStore(email);
        DetailStoreResponse detailStoreResponse = storeService.detailStoreResopnse(store.get());

        return ResponseEntity.created(null).body(detailStoreResponse);
    }

    @GetMapping("/id") // ????????? id ??????
    public ResponseEntity getStoreId(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        Optional<Store> store = storeService.getStore(email);
        return ResponseEntity.created(null).body(store.get().getId());
    }

    @PatchMapping//????????? ?????? ??????(??????)
    public ResponseEntity patchStore(@RequestHeader String authorization, @ModelAttribute StoreChangedDto storeChangedDto){
        String email = jwtToEmail(authorization);
        Store store = storeService.patchStore(email, storeChangedDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PutMapping// ????????? ?????? ??????(??????) ?????? ????????? ????????????
    public ResponseEntity putStore(@RequestHeader String authorization, @ModelAttribute StoreUnchangedDto storeUnchangedDto){
        // todo : ????????? ??????????????? ???????????? ????????? ???????????????.
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

    @GetMapping("/application/") // ????????? ????????? ??????. ????????? ?????? ????????? ??????
    public ResponseEntity storeApplicationList(@RequestHeader String authorization){
        List<Store> storeApplicationList = storeService.storeApplicationList();
        return ResponseEntity.created(null).body(storeApplicationList);
    }

    @PostMapping("/application/success/")//????????? ?????? ??????(?????????)
    public ResponseEntity storeApplicationSuccess(@RequestHeader String authorization, @RequestBody StoreIdDto storeIdDto){
        String email = jwtToEmail(authorization);
        Store store= storeService.storeApplicationSuccess(storeIdDto.getStoreId(), email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/application/fail/") // ????????? ?????? ??????(?????????) ????????? -> ??????
    public ResponseEntity storeApplicationFail(@RequestHeader String authorization,  @RequestBody StoreIdDto storeIdDto){
        storeService.storeApplicationFail(storeIdDto.getStoreId());   // ????????? ??????
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/application/confirm/") // ????????? ?????? ?????? ??????(???????????? ??????)
    public ResponseEntity storeApplicationConfirm(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        storeService.storeApplicationConfirm(email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping("/modify/")//????????? ?????? ?????????
    public ResponseEntity storeModifyList(@RequestHeader String authorization){
        List<StoreModifyListResponse> storeModifyListResponse = storeService.storeModifyList();
        return ResponseEntity.created(null).body(storeModifyListResponse);
    }

    @PostMapping("/modify/success") // ????????? ?????? ??????(?????????)
    public ResponseEntity storeModifySuccess(@RequestHeader String authorization, @RequestBody TempStoreIdDto tempStoreId){
        storeService.tempStoreSucess(tempStoreId.getTempStoreId());
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/modify/fail") // ????????? ?????? ??????(?????????)
    public ResponseEntity storeModifyFail(@RequestHeader String authorization, @RequestBody TempStoreIdDto tempStoreId){
        storeService.tempStoreFail(tempStoreId.getTempStoreId());
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/modify/confirm") // ????????? ?????? ?????? ??????(???????????? ??????)
    public ResponseEntity storeModifyConfirm(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        storeService.editConfirm(email);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }



    /* ????????? ????????? ?????????(?????? ???) */
    @GetMapping("/timedeal/{storeId}")  // ????????? ??????
    public ResponseEntity timeDealList(@RequestHeader String authorization,@PathVariable Long storeId) throws BadHttpRequest {
        // ????????? ????????? ?????????
        storeService.timeDealStatusInit(); // ?????? ??????
        TimeDealProductInfoResponse timeDeal = storeService.timeDealList(storeId);
        if(timeDeal.getStatus().equals("NORMAL")){
            return ResponseEntity.badRequest().body("400");
        }
        return ResponseEntity.created(null).body(timeDeal);
    }

    @PostMapping("/timedeal/")  // ????????? ??????
    public ResponseEntity timeDealCreate(@RequestHeader String authorization, @RequestBody TimeDealCreateDto timeDealCreateDto){
        storeService.timeDealCreate(timeDealCreateDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // ?????? ?????? ??????
    @GetMapping("/product/{storeId}")
    public ResponseEntity productAll(@RequestHeader String authorization, @PathVariable Long storeId){
        List<AllProductListResponse> product = storeService.productAll(storeId);
        return ResponseEntity.created(null).body(product);
    }

    // storeId??? ????????? ?????? ??????
    @GetMapping("/info/")
    public ResponseEntity storeInfo( @RequestParam("storeId") Long storeId){
        Optional<Store> store = storeService.getStoreId(storeId);
        DetailStoreResponse detailStoreResponse = storeService.detailStoreResopnse(store.get());
        return ResponseEntity.created(null).body(detailStoreResponse);
    }

    /* user ??????????????? Store ?????? */
    @GetMapping("/user/") // ?????? ???????????? ???, ????????? ?????? ?????? ???????????? ?????? ??? ????????? ??????
    public ResponseEntity getUserStoreList(@RequestHeader String authorization, @RequestParam("distance") Double distance){ // ????????? ?????? ?????? ??? ??????
        // todo 0.??????????? ????????? ???????????? ??????

        // 1. member ?????? ???????????? -> ?????? ?????? ??????????????????.
        String email = jwtToEmail(authorization);
        Optional<Member> member = storeService.getMemberInfo(email);
        if(!member.isPresent()){ // ????????? ?????? ??????
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // ????????? ????????? ?????????
        storeService.timeDealStatusInit();

        // todo ????????? ????????? ????????? ??????.????????? ?????? ?????????~ + ????????? ??????????????? ?????? ?????????~
        List<StoreAndDistanceDto> timeDealStoreList = storeService.findLocation(member, distance);

        return ResponseEntity.created(null).body(timeDealStoreList);
    }

    // ????????? ????????? ???????????? ??????
    @GetMapping("/user/product/") 
    public ResponseEntity getStoreGroupProductList(@RequestParam("storeId") Long storeId){ // ?????? ????????? requestparams
        List<GroupProductListResponse> groupProductListResponseList = productService.getStoreGroupProductList(storeId);
        return ResponseEntity.created(null).body(groupProductListResponseList);
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

    //?????? ????????? ????????? ????????? 1-> 0. ??? ??? ?????? ??? ??? ??????. ?????? 0????????? ?????????
    @Scheduled(cron = "0 0 0 * * *")
    public void timeDealInit(){
        storeService.timeDealStatusInit();
        storeService.timeDealInit();
    }




}