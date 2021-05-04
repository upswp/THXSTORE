package com.ssafy.thxstore.store.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Favorite;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import com.ssafy.thxstore.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    @JoinColumn(name = "id")
    private Member member;

    @ColumnDescription("스토어 상호명")
    @Column(name = "name")
    private String name;

    @ColumnDescription("스토어 카테고리")
    @Enumerated(EnumType.STRING)
    @Column(name = "store_category")
    private StoreCategory storeCategory = StoreCategory.DEFAULT;

    @ColumnDescription("스토어 전화번호")
    @Column(name = "phone_num",nullable = false)
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
    private String profileImg;

    @ColumnDescription("사업자등록번호")
    @Column(name = "license",nullable = false)
    private String license;

    @ColumnDescription("사업자등록 이미지")
    @Column(name = "license_img")
    @Lob
    private String licenseImg;

    @ColumnDescription("스토어 관리자 승인 여부 및 스토어 상태") // enum 수정
    @Enumerated(EnumType.STRING)
    @Column(name = "check_store")
    private CheckStore checkStore = CheckStore.APPLICATION_WAITING;

    @Builder
    public Store(Member member, String name, StoreCategory storeCategory, String phoneNum, String mainAddress, String subAddress,String openTime,String closeTime,String closedDay,String introduce, String thumbImg, String profileImg, String license, String licenseImg, CheckStore checkStore){
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
        this.profileImg = profileImg;
        this.license = license;
        this.licenseImg = licenseImg;
        this.checkStore = checkStore;
    }

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

//    @ColumnDescription("member")
//    @OneToOne
//    private Member member;

//    @ColumnDescription("product_group")
//    @OneToMany
//    private List<Product> productList = new ArrayList<>();
//
//    @ColumnDescription("time_deal")
//    @OneToMany
//    private List<Reservation> reservationList = new ArrayList<>();
//
//    @ColumnDescription("temp_store")
//    @OneToOne
//    private List<Favorite> storeList = new LinkedList<>();
}
