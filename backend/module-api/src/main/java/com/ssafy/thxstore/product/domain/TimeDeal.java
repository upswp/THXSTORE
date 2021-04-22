package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TimeDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timedeal_sequence_gen")
    @SequenceGenerator(
            name = "timedeal_sequence_gen",
            sequenceName = "timedeal_sequence"
    )
    @ColumnDescription("PK")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @ColumnDescription("FK")
    private Product product;

    @Column(name = "start_time")
    @ColumnDescription("타임딜 시작시간")
    private String startTime;

    @Column(name = "end_time")
    @ColumnDescription("타임딜 종료시간")
    private String endTime;

    @Column(name = "dc_rate")
    @ColumnDescription("할인가격")
    private Integer dcRate;
}
