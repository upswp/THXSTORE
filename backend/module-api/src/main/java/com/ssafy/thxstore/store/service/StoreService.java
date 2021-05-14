package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import com.ssafy.thxstore.product.dto.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.*;

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

    public TimeDealProductInfoResponse timeDealList(Long storeId){ // 타임 딜 반환.
        Store store = storeRepository.findById(storeId).get();
        List<TimeDeal> timeDeal = timeDealRepository.findAllByStoreId(storeId).get();
        List<TimeDealProductResponse> timeDealProductResponses = new ArrayList<>();

        for(int i = 0; i < timeDeal.size(); i++){
            timeDealProductResponses.add(
                    modelMapper.map(timeDeal.get(i).getProduct(), TimeDealProductResponse.class)
            );
            timeDealProductResponses.get(i).setProductId(timeDeal.get(i).getProduct().getId());
        }
        String status;

        // 이거 애러
        String startTime = null;

// NORMAL, RESERVATION, PROGRESS, COMPLETE
        if(timeDealProductResponses.size() == 0){ // 타임딜이 존재하지 않는 다면
            if(store.getTimeDealCheck() == true){ // 닫혀있다.  완료됌.
                status = "COMPLETE";
            }
            else{ // 열려있다.
                status = "NORMAL";
            }
        }
        else { // RESERVATION, PROGRESS
            // 시간 비교해서 현재 시간이 더 작으면 reservation, 크면 progress
            //timeDeal.get(0).getStartTime();
            //String localTime;
            startTime = timeDeal.get(0).getStartTime();
            SimpleDateFormat fourteen_format = new SimpleDateFormat("HH:mm");
            Date date_now = new Date(System.currentTimeMillis()); // 현재 시간
            String a = fourteen_format.format(date_now); // 현재 시간 포맷팅
            Date data1 = null; // 현재시간
            Date data2 = null; // data1에는 현재 시간 포맷팅, 2는 타임딜 시간 포맷팅
            try {
                data1 = fourteen_format.parse(a);
                data2 = fourteen_format.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Date localTime = fourteen_format.format(date_now);
            //String a = fourteen_format.format(date_now);

            if(data2.compareTo(data1) > 0){ //시간 비교해서 현재 시간이 더 작으면 reservation
                status = "RESERVATION";
            }
            else{ // 크거나 같으면 progress
                status = "PROGRESS";
            }
        }

        TimeDealProductInfoResponse timeDealProductInfoResponse = new TimeDealProductInfoResponse(status,startTime,timeDealProductResponses);

        return timeDealProductInfoResponse;
    }

    public void timeDealCreate(TimeDealCreateDto timeDealCreateDto) { // 타임 딜 생성
        // TODO 개선 방법을 생각해보자 rate와 stock를 timedeall 영역에 넣는 것이 더 효율적일까?

        List<TimeDealProductDto> timeDealProductDtos = timeDealCreateDto.getTimeDealList();

        Long storeId = timeDealCreateDto.getStoreId();
        Store store = storeRepository.findById(storeId).get();
        store.setTimeDealCheck(true); // 타임 딜 시작을 확인하는 셋팅
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

    // 멤버 정보
    public Optional<Member> getMemberInfo(String email) {
        return memberRepository.findByEmail(email);
    }

    // 위도경도
    public List<StoreAndDistanceDto> findLocation(Optional<Member> member) {
        // todo 여기 member 들어오면 변경
        //StoreAndDistanceDto
        // 해당 member와 위도 경도로 찾기 일단 거리 가까운 친구들 찾음.
        Optional<List<Store>> storeList = storeRepository.findByLocation(36.42583333272267, 127.38674024126392);

        List<StoreAndDistanceDto> storeAndDistanceDto = new ArrayList<>();

        // 타임딜이 진행한지 확인
        for(int i = 0; i < storeList.get().size(); i++){
            Optional<List<TimeDeal>> timeDealList = timeDealRepository.findAllByStoreId(storeList.get().get(i).getId());
            if(!timeDealList.isPresent()){
                continue;
            }

            //  타임딜이 존재한다면. todo 다른곳 remove 찾기

            // 해당 거리 값과 타임딜 시간 정보 넣기,.
            // 이것은 스토어 정보
            StoreAndDistanceDto storeInfo = modelMapper.map(storeList.get().get(i), new TypeToken<StoreAndDistanceDto>(){}.getType());


            storeInfo.setDistance( 6371*acos(cos(toRadians(36.42583333272267))*cos(toRadians(storeList.get().get(i).getLat()))*cos(toRadians(storeList.get().get(i).getLon())
                    -toRadians(127.38674024126392))+sin(toRadians(36.42583333272267))*sin(toRadians(storeList.get().get(i).getLat()))));

            storeInfo.setTimeDealStart(timeDealList.get().get(0).getStartTime());
            // 해당 상품들을 모아서 넣기
            //List<TimeDeal> timeDealList
            List<Product> products = new ArrayList<>();
            for(int j = 0; j < timeDealList.get().size(); j++){ //timeDealList.get().get(j).getProduct().
                products.add(Product.builder()
                        .id(timeDealList.get().get(j).getProduct().getId())
                        .name(timeDealList.get().get(j).getProduct().getName())
                        .introduce(timeDealList.get().get(j).getProduct().getIntroduce())
                        .amount(timeDealList.get().get(j).getProduct().getAmount())
                        .rate(timeDealList.get().get(j).getProduct().getRate())
                        .stock(timeDealList.get().get(j).getProduct().getStock())
                        .price(timeDealList.get().get(j).getProduct().getPrice())
                        .productImg(timeDealList.get().get(j).getProduct().getProductImg())
                        .build());
            }
            storeInfo.setTimeDealList(products);
            storeAndDistanceDto.add(storeInfo);
        }

        return storeAndDistanceDto;
    }

    public List<StoreAndDistanceDto> findtimeDealStore(Optional<List<StoreAndDistanceDto>> timeDealStoreList) {
        List<StoreAndDistanceDto> timeDeal = timeDealStoreList.get();

        // 타임딜 항목이 없으면 제거
        for(int i = 0; i < timeDeal.size();i++){
            if(timeDeal.get(i).getTimeDealList().size() == 0){
                timeDeal.remove(i);
                i--;
            }
        }
        return timeDeal; // 타임딜만 있는 상태
    }

    public Optional<Store> getStoreId(Long storeId) {
        return storeRepository.findById(storeId);
    }

    public void timeDealStatusInit() {
        SimpleDateFormat fourteen_format = new SimpleDateFormat("HH:mm");
        Date date_now = new Date(System.currentTimeMillis()); // 현재 시간
        String a = fourteen_format.format(date_now); // 현재 시간 포맷팅
        Date date1 = null; // 현재시간
        try {
            date1 = fourteen_format.parse(a);;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<TimeDeal> timeDealList = timeDealRepository.findAll();

        for(int i = 0; i < timeDealList.size(); i++){
            Date date2 = null;
            String startTime = timeDealList.get(i).getStartTime();
            try {
                date2 = fourteen_format.parse(startTime);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            // 2시간 전이란 것을 비교해야함 date1과 date2
            // 현재 시간과 date2시간이 2시간 넘게 차이남다면(+로)
            if(date1.getTime() - date2.getTime() > 7200000){
                Product product = productRepository.findById(timeDealList.get(i).getProduct().getId()).get();
                product.setStock(null);
                product.setRate(null);
                timeDealList.remove(i);
                i--;
            }
            else if(date1.getTime() - date2.getTime() < 0){ // 만약 date2의 시간이 22시를 넘어간다면
                if(date1.getTime()+86400000 - date2.getTime() > 7200000){
                    Product product = productRepository.findById(timeDealList.get(i).getProduct().getId()).get();
                    product.setStock(null);
                    product.setRate(null);
                    timeDealList.remove(i);
                    i--;
                }
            }
        }
    }
}