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
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final ProductService productService;

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
        DetailStoreResponse detailStoreResponse = storeService.detailStoreResopnse(store.get());

        return ResponseEntity.created(null).body(detailStoreResponse);
    }

    @GetMapping("/id") // 스토어 id 조회
    public ResponseEntity getStoreId(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        Optional<Store> store = storeService.getStore(email);
        return ResponseEntity.created(null).body(store.get().getId());
    }

    @PatchMapping//스토어 정보 수정(개인)
    public ResponseEntity patchStore(@RequestHeader String authorization, @ModelAttribute StoreChangedDto storeChangedDto){
        String email = jwtToEmail(authorization);
        Store store = storeService.patchStore(email, storeChangedDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
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
        List<StoreModifyListResponse> storeModifyListResponse = storeService.storeModifyList();
        return ResponseEntity.created(null).body(storeModifyListResponse);
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



    /* 판매자 스토어 페이지(타임 딜) */
    @GetMapping("/timedeal/{storeId}")  // 타임딜 조회
    public ResponseEntity timeDealList(@RequestHeader String authorization,@PathVariable Long storeId){
        TimeDealProductInfoResponse timeDeal = storeService.timeDealList(storeId);
        return ResponseEntity.created(null).body(timeDeal);
    }

    @PostMapping("/timedeal/")  // 타임딜 생성
    public ResponseEntity timeDealCreate(@RequestHeader String authorization, @RequestBody TimeDealCreateDto timeDealCreateDto){
        storeService.timeDealCreate(timeDealCreateDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    // 모든 매뉴 반환
    @GetMapping("/product/{storeId}")
    public ResponseEntity productAll(@RequestHeader String authorization, @PathVariable Long storeId){
        List<AllProductListResponse> product = storeService.productAll(storeId);
        return ResponseEntity.created(null).body(product);
    }

    // storeId로 스토어 정보 반환
    @GetMapping("/info/")
    public ResponseEntity storeInfo( @RequestParam("storeId") Long storeId){
        Optional<Store> store = storeService.getStoreId(storeId);
        DetailStoreResponse detailStoreResponse = storeService.detailStoreResopnse(store.get());
        return ResponseEntity.created(null).body(detailStoreResponse);
    }

    /* user 관점에서의 Store 작성 */
    @GetMapping("/user/") // 처음 접속했을 때, 타임딜 하고 있는 항목들을 보는 곳 거리에 따라
    public ResponseEntity getUserStoreList(@RequestHeader String authorization){

        // todo 0.초기화?? 타임딜 진행되는 거거
        // 현재 시간 -2시간 보다 작은 것이 있으면 다 제거 합시다.
        // 새벽 2시일 때, 10~12시것도 처리
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
// 년월일시분초 14자리 포멧
        SimpleDateFormat fourteen_format = new SimpleDateFormat("HH:mm");

       // 여기 헤더에서 사용되는 값은 위치정보를 꺼내야함. 가능.
        // 1. 처음에는 무조건 로그인 화면이고, 로그인하면 무조건 타임딜 진행중인 가게가 보이는 화면인지 체크 필요.
        // 시간을 먼저 체크할까 타임딜을 먼저 체크할까
        // 시간을 먼저 -> 시간에 따른 값을 꺼내오고, 타임딜 시간 계산하고, 
        // 타임딜 먼저 -> 
        // 타임딜 조건 -> 1이고, 아직 2시간 안지난것(timedeal에서 삭제가 없어야함.)


        // 1. member 정보 가져오기 -> 위도 경도 꺼내야합니다.
        String email = jwtToEmail(authorization);
        Optional<Member> member = storeService.getMemberInfo(email);
        if(!member.isPresent()){ // 멤버가 없을 경우
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // todo 타임딜 체크는 언제할 까요.여기서 같이 체크요~ + 휴무일 시간체크는 언제 체크요~
        // 2. 위도 경도 스토어들 찾기 -> 5km. 페이징 기법으로.. dist 값 확인
        // store는 되는데 이건 안된다? store로 받아서 추가 작업이 필요할 듯
        // 하면서 jpa 꺼내온 후 후작업이 필요한데 ->  페이징 기법은 어디서?
        List<StoreAndDistanceDto> timeDealStoreList = storeService.findLocation(member);
//        if(!timeDealStoreList.isPresent()){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        // 3여기서 타임딜 체크 timedeal에 있는 store 값들만 가져오기
        // 현재 timedeal 테이블에 storeid가 있는지 있는지 체크 -> timedeal 값ㅇ ㅣ있는지 체크


        //List<StoreAndDistanceDto> timeDealList = storeService.findtimeDealStore(timeDealStoreList);


        // close도 해야되네 close open day 등등
        return ResponseEntity.created(null).body(timeDealStoreList);
    }

    // 그룹별 매뉴로 상품정보 반환
    @GetMapping("/user/product/") 
    public ResponseEntity getStoreGroupProductList(@RequestHeader String authorization, @RequestParam("storeId") Long storeId){ // 쿼리 문으로 requestparams
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

    //자정 시간에 타임딜 초기화 1-> 0. 초 분 시간 일 월 요일. 매일 0시간에 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void timeDealInit(){
        storeService.timeDealInit();
    }
}