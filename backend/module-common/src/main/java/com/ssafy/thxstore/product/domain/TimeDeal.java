package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TimeDeal {

    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 여기 함 더
    @OneToOne
    @JoinColumn(name = "product_id")
    @ColumnDescription("FK")
    private Product product;

    @Column(name = "start_time")
    @ColumnDescription("타임딜 시작시간")
    private String startTime;

//    @Column(name = "end_time")
//    @ColumnDescription("타임딜 종료시간")
//    private String endTime;
//
//    @Column(name = "dc_rate")
//    @ColumnDescription("할인가격")
//    private Integer dcRate;

    @Builder
    public TimeDeal(String startTime){
        this.startTime = startTime;
    }
}
