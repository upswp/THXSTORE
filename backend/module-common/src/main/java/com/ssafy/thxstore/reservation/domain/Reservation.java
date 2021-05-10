package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id; // pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDescription("주문 데이터 하나당 하나의 상품")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "prodcut_name")
    private String productName;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "store_id")
    private Long storeId;

    @ColumnDescription("기본,대기,승인,완료")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus reservationStatus;

    @Builder
    public Reservation(Member member, Product product, int count, int price, String productName,Long storeId,ReservationStatus reservationStatus) {
        this.member = member;
        this.product = product;
        this.count = count;
        this.price = price;
        this.productName = productName;
        this.storeId = storeId;
        this.reservationStatus = reservationStatus;
    }
}
