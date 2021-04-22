package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence_gen")
    @SequenceGenerator(
            name = "product_sequence_gen",
            sequenceName = "product_sequence"
    )
    @ColumnDescription("PK")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    @ColumnDescription("FK")
    private Store store;

    @OneToOne
    private TimeDeal timeDealList;

    @Column(name = "name")
    @ColumnDescription("상품명")
    private String name;

    @Column(name = "stock")
    @ColumnDescription("재고")
    private Integer stock;

    @Column(name = "price")
    @ColumnDescription("가격")
    private Integer price;

    @Column(name = "product_img")
    @ColumnDescription("상품 이미지")
    @Lob
    private String productImg;

}
