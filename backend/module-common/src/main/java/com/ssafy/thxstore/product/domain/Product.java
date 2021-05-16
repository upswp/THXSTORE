package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name = "product_group_id")
    @ColumnDescription("FK")
    private ProductGroup productGroup;

    @Column(name = "name")
    @ColumnDescription("상품명")
    private String name;

    @Column(name = "price")
    @ColumnDescription("가격")
    private Integer price;

    @Lob
    @Column(name = "product_img")
    @ColumnDescription("상품 이미지")
    private String productImg;

    @Column(name = "amount")
    @ColumnDescription("표기(그램, 인분)")
    private String amount;

    @Column(name = "rate")
    @ColumnDescription("할인률")
    private Integer rate;

    @Column(name = "stock")
    @ColumnDescription("재고")
    private Integer stock;

    @Column(name = "introduce")
    @ColumnDescription("상품 소개")
    private String introduce;

    @ColumnDescription("양방향 맵핑")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ReservationGroup> reservationGroup = new ArrayList<>();

    public void stockUpdate(int i) {
        this.stock = Integer.valueOf(""+i);
    }
}
