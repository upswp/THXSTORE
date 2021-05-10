package com.ssafy.thxstore.store.dto;

import com.ssafy.thxstore.common.ColumnDescription;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreModifyListResponse {
    private Long tempStoreId;
    private String name;
    private String mainAddress;

    private String subAddress;

    private String phoneNum;

    private Double lat;

    private Double lon;

    private String license;

    private String licenseImg;
}
