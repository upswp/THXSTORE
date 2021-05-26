package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReservationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id; // pk

    @ColumnDescription("하나의 그룹에 여러개의 프로젝트 리스트가 담긴다")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ColumnDescription("상품의 재고, 할인율 등의 정보를 위한 매핑,각각의 상품 1-1 매핑")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "user_id")
    private Long userId;

    @ColumnDescription("삭제 시 필요 연관관계 없음")
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @ColumnDescription("상품별 할인율")
    @Column(name = "rate")
    private int rate;

    @Column(name = "product_name")
    private String productName;

    @Builder
    public ReservationGroup(Product product,Reservation reservation, int count, int price, String productName,Long userId,Long storeId,int rate) {
        this.rate =rate;
        this.userId = userId;
        this.storeId = storeId;
        this.product =product;
        this.reservation = reservation;
        this.count = count;
        this.price = price;
        this.productName = productName;
    }

    public ReservationGroup(Reservation reservation,int count, int price, String productName,Long userId,ReservationStatus reservationStatus,int rate) {
        this.userId = userId;
        this.rate =rate;
        this.reservation = reservation;
        this.count = count;
        this.price = price;
        this.productName = productName;
    }
}
