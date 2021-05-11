package com.ssafy.thxstore.store.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// todo setter -> build로 변경 필요 (다른 dto, domain 포함)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Store {

    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne // 멤버와 연결
    @ColumnDescription("FK member의 id 참조(필드_컬럼명)")
    @JoinColumn(name = "member_id") // id 맞낭
    private Member member;

    @ColumnDescription("스토어 상호명")
    @Column(name = "name")
    private String name;

    @ColumnDescription("스토어 카테고리")
    @Enumerated(EnumType.STRING)
    @Column(name = "store_category")
    private StoreCategory storeCategory = StoreCategory.DEFAULT;

    @ColumnDescription("스토어 전화번호")
    @Column(name = "phone_num")
    private String phoneNum;

    @ColumnDescription("스토어 메인 주소")
    @Column(name = "main_address")
    private String mainAddress;

    @ColumnDescription("스토어 서브 주소")
    @Column(name = "sub_address")
    private String subAddress;

    @ColumnDescription("스토어 오픈 시간") // 있다가 타입 확인
    @Column(name = "open_time")
    private String openTime;

    @ColumnDescription("스토어 마감 시간")
    @Column(name = "close_time")
    private String closeTime;

    @ColumnDescription("스토어 휴무일")
    @Column(name = "close_day")
    private String closedDay;

    @Lob
    @ColumnDescription("스토어 소개")
    @Column(name = "introduce")
    private String introduce;


    @ColumnDescription("스토어 썸네일 이미지")
    @Column(name = "thumbnail_img")
    @Lob
    private String thumbImg;

    @ColumnDescription("스토어 프로필 이미지")
    @Column(name = "profile_img")
    @Lob
    private String logo;

    @ColumnDescription("사업자등록번호")
    @Column(name = "license")
    private String license;

    @ColumnDescription("사업자등록 이미지")
    @Column(name = "license_img")
    @Lob
    private String licenseImg;

    @ColumnDescription("스토어 관리자 승인 여부 및 스토어 상태") // enum 수정
    @Column(name = "check_store")
    private CheckStore checkStore;

    @ColumnDescription("스토어 휴무 상태 closestore == 1 닫혀있다.") // enum 수정
    @Column(name = "close_store")
    private Boolean closeStore;

    @ColumnDescription("위도 37.xx") // enum 수정
    @Column(name = "lat")
    private Double lat;

    @ColumnDescription("경도 127.xx") // enum 수정
    @Column(name = "lon")
    private Double lon;

    @ColumnDescription("타임딜 사용 여부 1 = 사용. 0 노사용.") // enum 수정
    @Column(name = "time_deal_check")
    private Boolean timeDealCheck;

    @Builder
    public Store(Member member, String name, StoreCategory storeCategory, String phoneNum, String mainAddress, String subAddress,String openTime,String closeTime,String closedDay,String introduce, String thumbImg, String logo, String license, String licenseImg, CheckStore checkStore, Boolean closeStore,Double lat,Double lon, Boolean timeDealCheck){
        this.member = member;
        this.name = name;
        this.storeCategory = storeCategory;
        this.phoneNum = phoneNum;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.closedDay = closedDay;
        this.introduce = introduce;
        this.thumbImg = thumbImg;
        this.logo = logo;
        this.license = license;
        this.licenseImg = licenseImg;
        this.checkStore = checkStore;
        this.closeStore = closeStore;
        this.lat = lat;
        this.lon = lon;
        this.timeDealCheck = timeDealCheck;
    }

    // todo 정리 필요
    public Object getCheckStore(CheckStore checkStore){
        return CheckStore.APPLICATION_WAITING;
    }
    public Object getStoreCategory(StoreCategory storeCategory){
        return StoreCategory.DEFAULT;
    }


    @ColumnDescription("product_group")
    @OneToMany
    private List<ProductGroup> productGroupList = new ArrayList<>();

    @ColumnDescription("time_deal")
    @OneToMany
    private List<TimeDeal> timeDealList = new ArrayList<>();

    @ColumnDescription("temp_store")
    @OneToOne
    private TempStore tempStore;
}
