package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id; // pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "store_id")
    private Long storeId;

    @ColumnDescription("양방향 맵핑으로 해당 reservation 에 대한 product 정보 추출 가능")
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReservationGroup> reservationGroup = new ArrayList<>();

    @ColumnDescription("기본,대기,승인,완료")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus reservationStatus;

    @Builder
    public Reservation(Member member,Long storeId,ReservationStatus reservationStatus, List<ReservationGroup> reservationGroup) {
        this.member = member;
        this.storeId = storeId;
        this.reservationStatus = reservationStatus;
        this.reservationGroup = reservationGroup;
    }
}
