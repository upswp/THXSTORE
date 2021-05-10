package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartid")
    private Long id; // pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(mappedBy = "cartId", fetch = FetchType.LAZY)
    private Reservation reservation;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "prodcut_name")
    private String productName;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "store_id")
    private Long storeId;

    @Builder
    public Cart(Member member, Product product, int count, int price, String productName,Long storeId) {
        this.member = member;
        this.product = product;
        this.count = count;
        this.price = price;
        this.productName = productName;
        this.storeId = storeId;
    }
}
