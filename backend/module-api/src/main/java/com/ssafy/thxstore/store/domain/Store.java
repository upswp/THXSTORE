package com.ssafy.thxstore.store.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.dto.Favorite;
import com.ssafy.thxstore.member.dto.Member;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.product.domain.Product;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_sequence_get")
    @SequenceGenerator(
            name = "store_sequence_get",
            sequenceName = "store_sequence"
    )
    private Long id;

    @ManyToOne
    @ColumnDescription("FK")
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDescription("FK")
    @OneToMany
    private List<Product> productList = new ArrayList<>();

    @ColumnDescription("FK")
    @OneToMany
    private List<Reservation> reservationList = new ArrayList<>();

    @ColumnDescription("유저가 좋아하는 가게 리스트")
    @OneToMany
    private List<Favorite> storeList = new LinkedList<>();

    @ColumnDescription("스토어 상호명")
    @Column(name = "name")
    private String name;

    @ColumnDescription("사업자등록번호")
    @Column(name = "business_license_number",nullable = false)
    private Integer license;

    @ColumnDescription("사업자등록 이미지")
    @Column(name = "business_license_number_img")
    @Lob
    private String licenseImg;

    @ColumnDescription("스토어 프로필 이미지")
    @Column(name = "store_profile_img")
    @Lob
    private String profileImg;

    @ColumnDescription("스토어 카테고리")
    @Enumerated(EnumType.STRING)
    @Column(name = "store_type")
    private StoreCategory storeType = StoreCategory.DEFAULT;

    @ColumnDescription("스토어 전화번호")
    @Column(name = "phone_num",nullable = false)
    private String phoneNum;

    @ColumnDescription("스토어 주소")
    @Column(name = "address")
    private String address;

    @ColumnDescription("스토어 오픈 시간")
    @Column(name = "open_time")
    private String openTime;

    @ColumnDescription("스토어 마감 시간")
    @Column(name = "end_time")
    private String endTime;

    @ColumnDescription("스토어 휴무일")
    @Column(name = "closed_day")
    private String closedDay;

    @Lob
    @ColumnDescription("스토어 소개")
    @Column(name = "introduce")
    private String introduce;

    @ColumnDescription("timedeal 사용여부")
    @Column(name = "check_time_deal")
    private boolean checkTimeDeal;

    @ColumnDescription("스토어 관리자 승인 여부")
    @Enumerated(EnumType.STRING)
    @Column(name = "check_store")
    private CheckStore checkStore = CheckStore.WAIT_FOR_GENERATION;
}
