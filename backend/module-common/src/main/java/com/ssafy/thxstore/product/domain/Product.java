package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name = "group_id")
    @ColumnDescription("FK")
    private ProductGroup productGroup;

//    @OneToOne
//    private TimeDeal timeDealList;

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





    @Builder
    public Product(ProductGroup productGroup, String name, Integer price, String productImg, String amount, Integer rate, Integer stock){
        this.productGroup = productGroup;
        this.name = name;
        this.price = price;
        this.productImg = productImg;
        this.amount = amount;
        this.rate = rate;
        this.stock = stock;
    }
}
