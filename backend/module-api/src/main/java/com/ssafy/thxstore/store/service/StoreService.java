package com.ssafy.thxstore.store.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.domain.TempStore;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.dto.StoreChangedDto;
import com.ssafy.thxstore.store.dto.StoreUnchangedDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import com.ssafy.thxstore.store.repository.TempStoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
            store.setClosedDay(storeChangedDto.getCloseDay());
        }else if(storeChangedDto.getIntroduce() != null){
            store.setIntroduce(storeChangedDto.getIntroduce());
        }else if(storeChangedDto.getThumbnailImg() != null){
            String imgProfile = null;
            try {
                imgProfile = imageService.createImage(storeChangedDto.getThumbnailImg());
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

    public List<TempStore> storeModifyList() { return tempStoreRepository.findAll(); }

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

        tempStoreRepository.deleteById(tempStore.getId()); // 임시 저장소 삭제

        store.setCheckStore(CheckStore.NORMAL); // normal 변환
    }

    public void tempStoreFail(Long tempStoreId) { // 스토어 수정 실패
        TempStore tempStore = tempStoreRepository.findById(tempStoreId).get();
        Long storeId = tempStore.getStore().getId();
        Store store = storeRepository.findById(storeId).get();
        store.setCheckStore(CheckStore.APPLICATION_FAILED);
        tempStoreRepository.deleteById(tempStoreId);
    }

    public void editConfirm(String email) { // 스토어 수정 확인
        Member member = memberRepository.findByEmail(email).get();
        Long memberId = member.getId();
        Store store = storeRepository.findByMemberId(memberId).get();
        store.setCheckStore(CheckStore.NORMAL);
    }
}