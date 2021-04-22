package com.ssafy.thxstore.reservation.domain;

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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence_gen")
    @SequenceGenerator(
            name = "reservation_sequence_gen",
            sequenceName = "reservation_sequence"
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
    @Column(name = "reservation_status")
    @ColumnDescription("오더 상황")
    private ReservationStatus reservationStatus = ReservationStatus.DEFAULT;

}
