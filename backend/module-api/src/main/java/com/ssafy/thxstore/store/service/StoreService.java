package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// todo 필요없는 주석 처리.
@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreService {

    private final ModelMapper modelMapper;

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    private final ImageService imageService;

    // 스토어 생성
    // todo error 예외 처리 필요
    @Transactional
    public Store createStore(String imgProfile, CreateStoreFileDto createStoreFileDto, String email) {
//        Member member = memberRepository.findById(createStoreFileDto.getMemberId()).get();
        Member member = memberRepository.findByEmail(email).get();
//        member.setRole(MemberRole.MANAGER);

        // 파일 저장
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
                .build();

       Store store = modelMapper.map(createStoreDto, Store.class);
       // member 상태도 변환

        storeRepository.save(store);

        return store;
    }

    /*판매자 스토어 페이지(스토어 정보 입력)*/
    // 스토어 정보 가져오기
    public Optional<Store> getStore(String email) {
        Member member = memberRepository.findByEmail(email).get();

        Optional<Store> store = storeRepository.findByMemberId(member.getId());
       // Optional<Store> store =  storeRepository.findById(storeId);
        return store;
    }

    // todo 수정 부분 -> 금요일이나 다음 주 작업
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
        //System.out.println(CheckStore.APPLICATION_WAITING.ordinal());
        List<Store> store = storeRepository.findByCheckStore(CheckStore.APPLICATION_WAITING.ordinal());
        return store;
    }

    // 스토어 신청 허가.
    @Transactional
    public Store storeApplicationSuccess(Long storeId,String email){
        Store store = storeRepository.findById(storeId).get();
        Member member = memberRepository.findByEmail(email).get();
        member.setRole(MemberRole.MANAGER);
        //store = Store.builder()
       //         .checkStore(CheckStore.EDIT_WAITING)
       //         .build();
        store.setCheckStore(CheckStore.NORMAL);
        //System.out.println(store.getCheckStore());
        return store;
    }
    // 스토어 신청 불가 여기서는 store status만 변경
    @Transactional
    public void storeApplicationFail(Long storeId){
        Store store = storeRepository.findById(storeId).get();
        store.setCheckStore(CheckStore.APPLICATION_FAILED);
    }
    // 스토어 신청 불가 확인 -> 매니저 -> 유저,  스토어 삭제
    @Transactional
    public void storeApplicationConfirm(String email){

        Member member = memberRepository.findByEmail(email).get();
        Store store = storeRepository.findByMemberId(member.getId()).get();
        storeRepository.deleteById(store.getId());

//        Store store = storeRepository.findById(storeId).get();
//        Long memberId = store.getMember().getId();
//
//        Member member = memberRepository.findById(memberId).get();
//        member.setRole(MemberRole.USER);
//        storeRepository.deleteById(storeId);
    }
    // 스토어 수정 리스트


    // etc. 기타 시간간 문여는 시간 체크
    public boolean storeOpen(String times, int checkTime){

        return false;
    }

    public Store createStoreTest(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);

        storeRepository.save(store);

        return store;
    }

    public List<Store> storeModifyList() {
        List<Store> store = null;//storeRepository.findByStoreCategory("EDIT_WAITING");
        return store;
    }
}