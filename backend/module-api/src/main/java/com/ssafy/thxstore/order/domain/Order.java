package com.ssafy.thxstore.order.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.dto.Member;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence_gen")
    @SequenceGenerator(
            name = "order_sequence_gen",
            sequenceName = "order_sequence"
    )
    @ColumnDescription("PK")
    private Long id;

    @ManyToOne
    @ColumnDescription("FK")
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDescription("FK")
    @JoinColumn(name = "store_id")
    private Store store;

    @Temporal(TemporalType.TIME)
    @CreationTimestamp
    @ColumnDescription("오더 들어온 시간")
    private Date createOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    @ColumnDescription("오더 상황")
    private OrderStatus orderStatus = OrderStatus.DEFAULT;

}
