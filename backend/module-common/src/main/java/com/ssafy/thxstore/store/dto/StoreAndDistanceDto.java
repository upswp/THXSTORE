package com.ssafy.thxstore.store.dto;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.domain.TimeDeal;
import com.ssafy.thxstore.store.domain.CheckStore;
import com.ssafy.thxstore.store.domain.StoreCategory;
import com.ssafy.thxstore.store.domain.TempStore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreAndDistanceDto {
    private Long id;
    private String name;
    private StoreCategory storeCategory;
    private String phoneNum;
    private String mainAddress;
    private String subAddress;
    private String openTime;
    private String closeTime;
    private String closeDay;
    private String introduce;
    private String thumbImg;
    private String logo;
    private Double distance;
    private String timeDealStart;
    private List<Product> timeDealList;
}