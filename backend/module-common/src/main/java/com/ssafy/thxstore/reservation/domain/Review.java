package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.product.domain.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id; // pk

    @ColumnDescription("주문 데이터 여러개(하나의 주문) 당 연결되는 리뷰 한 개")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Reservation reservation;

    @ColumnDescription("주문 데이터 하나당 하나의 상품")
    @Column(name = "context")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "prodcut_name")
    private String productName;
}
