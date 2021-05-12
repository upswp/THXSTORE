package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import com.ssafy.thxstore.product.dto.AllProductListResponse;
import com.ssafy.thxstore.product.dto.TimeDealCreateDto;
import com.ssafy.thxstore.product.dto.TimeDealProductDto;
import com.ssafy.thxstore.product.dto.TimeDealProductResponse;
import com.ssafy.thxstore.product.repository.ProductGroupRepository;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.product.repository.TimeDealRepository;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.*;
import com.ssafy.thxstore.store.repository.StoreRepository;
import com.ssafy.thxstore.store.repository.TempStoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// todo 필요없는 주석 처리.
@Service
@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;
    private final TempStoreRepository tempStoreRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TimeDealRepository timeDealRepository;
    private final ProductGroupRepository productGroupRepository;

    private final ImageService imageService;

    // todo error 예외 처리 필요
    public Store createStore(String imgProfile, CreateStoreFileDto createStoreFileDto, String email) { //스토어 생성
        Member member = memberRepository.findByEmail(email).get();

        CreateStoreDto createStoreDto = CreateStoreDto.builder()
                .member(member)
                .memberId(member.getId())
                .name(createStoreFileDto.getName())
                .mainAddress(createStoreFileDto.getMainAddress())
                .subAddress(createStoreFileDto.getSubAddress())
                .phoneNum(createStoreFileDto.getPhoneNum())
                .license(createStoreFileDto.getLicense())
                .licenseImg(imgProfile)
                .checkStore(CheckStore.APPLICATION_WAITING)
                .lat(createStoreFileDto.getLat())
                .lon(createStoreFileDto.getLon())
                .build();

       Store store = modelMapper.map(createStoreDto, Store.class);
       store.setTimeDealCheck(false);
       storeRepository.save(store);
       return store;
    }

    public Optional<Store> getStore(String email) { // 스토어 정보 가져오기
        Member member = memberRepository.findByEmail(email).get();
        Optional<Store> store = storeRepository.findByMemberId(member.getId());
        return store;
    }

    public Store patchStore(String email, StoreChangedDto storeChangedDto){ // 스토어 개인정보 변경 개인
        Store store = storeRepository.findById(storeChangedDto.getStoreId()).get();

        if(storeChangedDto.getStoreCategory() != null){
            store.setStoreCategory(StoreCategory.valueOf(storeChangedDto.getStoreCategory()));
        }else if(storeChangedDto.getOpenTime() != null){
            store.setOpenTime(storeChangedDto.getOpenTime());
        }else if(storeChangedDto.getCloseTime() != null){
            store.setCloseTime(storeChangedDto.getCloseTime());
        }else if(storeChangedDto.getCloseDay() != null){
            store.setCloseDay(storeChangedDto.getCloseDay());
        }else if(storeChangedDto.getIntroduce() != null){
            store.setIntroduce(storeChangedDto.getIntroduce());
        }else if(storeChangedDto.getThumbImg() != null){
            String imgProfile = null;
            try {
                imgProfile = imageService.createImage(storeChangedDto.getThumbImg());
            }catch (IOException e) {
                return null;
            }
            store.setThumbImg(imgProfile);
        }else if(storeChangedDto.getLogo() != null){
            String imgProfile = null;
            try {
                imgProfile = imageService.createImage(storeChangedDto.getLogo());
            }catch (IOException e) {
                return null;
            }
            store.setLogo(imgProfile);
        }
        return store;
    }

    // 스토어 정보 변경 불변
    public void putStore(String email, StoreUnchangedDto storeUnchangedDto, String imgProfile) {
        Member member = memberRepository.findByEmail(email).get();
        Store store = storeRepository.findByMemberId(member.getId()).get();

        store.setCheckStore(CheckStore.EDIT_WAITING);

        TempStore tempStore = TempStore.builder()
                .store(store)
                .name(storeUnchangedDto.getName())
                .mainAddress(storeUnchangedDto.getMainAddress())
                .subAddress(storeUnchangedDto.getSubAddress())
                .phoneNum(storeUnchangedDto.getPhoneNum())
                .license(storeUnchangedDto.getLicense())
                .licenseImg(imgProfile)
                .lat(storeUnchangedDto.getLat())
                .lon(storeUnchangedDto.getLon())
                .build();
        TempStore saveTempStore = tempStoreSave(tempStore);
    }

    public List<Store> storeApplicationList() {// 스토어 신청 리스트
        List<Store> store = storeRepository.findByCheckStore(CheckStore.APPLICATION_WAITING.ordinal());
        return store;
    }

    public Store storeApplicationSuccess(Long storeId,String email){    // 스토어 신청 허가.
        Store store = storeRepository.findById(storeId).get();
        Member member = memberRepository.findByEmail(email).get();
        member.setRole(MemberRole.ROLE_MANAGER);
        store.setCheckStore(CheckStore.NORMAL);
        return store;
    }

    public void storeApplicationFail(Long storeId){ // 스토어 신청 불가 여기서는 store status만 변경
        Store store = storeRepository.findById(storeId).get();
        store.setCheckStore(CheckStore.APPLICATION_FAILED);
    }

    public void storeApplicationConfirm(String email){// 스토어 신청 불가 확인 -> 스토어 삭제
        Member member = memberRepository.findByEmail(email).get();
        Store store = storeRepository.findByMemberId(member.getId()).get();
        storeRepository.deleteById(store.getId());
    }

    public boolean storeOpen(String times, int checkTime){ // etc. 기타 시간간 문여는 시간 체크
        return false;
    }

    public Store createStoreTest(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);
        storeRepository.save(store);
        return store;
    }

    public List<StoreModifyListResponse> storeModifyList() {

        List<TempStore> tempStores = tempStoreRepository.findAll();
        List<StoreModifyListResponse> storeModifyListResponse = new ArrayList<>();

        for(int i = 0; i < tempStores.size(); i++){

            storeModifyListResponse.add(StoreModifyListResponse.builder()
                    .tempStoreId(tempStores.get(i).getId())
                    .name(tempStores.get(i).getName())
                    .mainAddress(tempStores.get(i).getMainAddress())
                    .phoneNum(tempStores.get(i).getPhoneNum())
                    .lat(tempStores.get(i).getLat())
                    .lon(tempStores.get(i).getLon())
                    .license(tempStores.get(i).getLicense())
                    .licenseImg(tempStores.get(i).getLicenseImg())
                    .build());

        }

        return storeModifyListResponse;
    }

    public TempStore tempStoreSave(TempStore tempStore) { return tempStoreRepository.save(tempStore); }

    public void tempStoreSucess(Long tempStoreId) {   // store에 반영하고, tempStore 제거, 그리고 normal 상태로 반환
        TempStore tempStore = tempStoreRepository.findById(tempStoreId).get();
        Long storeId = tempStore.getStore().getId();
        Store store = storeRepository.findById(storeId).get();
        store.setName(tempStore.getName());
        store.setMainAddress(tempStore.getMainAddress());
        store.setSubAddress(tempStore.getSubAddress());
        store.setPhoneNum(tempStore.getPhoneNum());
        store.setLicense(tempStore.getLicense());
        store.setLicenseImg(tempStore.getLicenseImg());
        store.setLat(tempStore.getLat());
        store.setLon(tempStore.getLon());

        tempStoreRepository.deleteById(tempStore.getId()); // 임시 저장소 삭제

        store.setCheckStore(CheckStore.NORMAL); // normal 변환
    }

    public void tempStoreFail(Long tempStoreId) { // 스토어 수정 실패
        TempStore tempStore = tempStoreRepository.findById(tempStoreId).get();
        Long storeId = tempStore.getStore().getId();
        Store store = storeRepository.findById(storeId).get();
        store.setCheckStore(CheckStore.EDIT_FAILED);
        tempStoreRepository.deleteById(tempStoreId);
    }

    public void editConfirm(String email) { // 스토어 수정 확인
        Member member = memberRepository.findByEmail(email).get();
        Long memberId = member.getId();
        Store store = storeRepository.findByMemberId(memberId).get();
        store.setCheckStore(CheckStore.NORMAL);
    }

    public DetailStoreResponse detailStoreResopnse(Store store) {
        SideInfo sideInfo = modelMapper.map(store, SideInfo.class);
        BaseInfo baseInfo = modelMapper.map(store, BaseInfo.class);
        baseInfo.setRole(store.getMember().getRole());
        baseInfo.setStoreId(store.getId());
        DetailStoreResponse detailStoreResponse = DetailStoreResponse.builder()
                .sideInfo(sideInfo)
                .baseInfo(baseInfo)
                .build();
        return detailStoreResponse;
    }

    // 자정 타임딜 초기화
    public void timeDealInit() {
        storeRepository.updateStoreTimeDealCHeck();
    }

    public List<TimeDealProductResponse> timeDealList(Long storeId) { // 타임 딜 반환.
        List<TimeDeal> timeDeal = timeDealRepository.findAllByStoreId(storeId).get();
        List<TimeDealProductResponse> timeDealProductResponses = new ArrayList<>();

        for(int i = 0; i < timeDeal.size(); i++){
            timeDealProductResponses.add(
                    modelMapper.map(timeDeal.get(i).getProduct(), TimeDealProductResponse.class)
            );
            timeDealProductResponses.get(i).setProductId(timeDeal.get(i).getProduct().getId());
        }
        return timeDealProductResponses;
    }

    public void timeDealCreate(TimeDealCreateDto timeDealCreateDto) { // 타임 딜 생성
        // TODO 개선 방법을 생각해보자 rate와 stock를 timedeall 영역에 넣는 것이 더 효율적일까?

        List<TimeDealProductDto> timeDealProductDtos = timeDealCreateDto.getTimeDealList();

        Long storeId = timeDealCreateDto.getStoreId();
        Store store = storeRepository.findById(storeId).get();
        String startTime = timeDealCreateDto.getStartTime();

        for(int i = 0 ; i < timeDealProductDtos.size(); i++){
            Product product = productRepository.findById(timeDealProductDtos.get(i).getProductId()).get();
            product.setRate(timeDealProductDtos.get(i).getRate());
            product.setStock(timeDealProductDtos.get(i).getStock());

            TimeDeal timeDeal = TimeDeal.builder()
                    .product(product)
                    .startTime(startTime)
                    .store(store)
                    .build();
            timeDealRepository.save(timeDeal);
        }
    }

    // TODO : group에서 prodcut포함하도록 리펙토링 필요
    public List<AllProductListResponse> productAll(Long storeId) {
        List<ProductGroup> productGroup = productGroupRepository.findAllByStoreId(storeId).get();

        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < productGroup.size(); i++){
            Long groupId = productGroup.get(i).getId();
            productList.addAll(productRepository.findAllByProductGroupId(groupId).get());
        }
        List<AllProductListResponse> allProductListResponses = modelMapper.map(productList, new TypeToken<List<AllProductListResponse>>(){}.getType());

        return allProductListResponses;
    }
}