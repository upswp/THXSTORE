package com.ssafy.thxstore.store.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TempStore {

    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // store id

    @ColumnDescription("스토어 상호명")
    @Column(name = "name")
    private String name;

    @ColumnDescription("스토어 메인 주소")
    @Column(name = "main_address")
    private String mainAddress;

    @ColumnDescription("스토어 서브 주소")
    @Column(name = "sub_address")
    private String subAddress;

    @ColumnDescription("스토어 전화번호")
    @Column(name = "phone_num",nullable = false)
    private String phoneNum;

    @ColumnDescription("사업자등록번호")
    @Column(name = "license",nullable = false)
    private String license;

    @Lob
    @ColumnDescription("사업자등록 이미지")
    @Column(name = "license_img")
    private String licenseImg;

    @Builder
    public TempStore(String name, String phoneNum, String mainAddress, String subAddress, String license, String licenseImg){
        this.name = name;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.phoneNum = phoneNum;
        this.license = license;
        this.licenseImg = licenseImg;
    }
}
