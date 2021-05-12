package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReservationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id; // pk

    @ColumnDescription("하나의 그룹에 여러개의 프로젝트 리스트가 담긴다")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationGroup(Reservation reservation, int count, int price, String productName,Long userId,ReservationStatus reservationStatus) {
        this.userId = userId;
        this.reservation = reservation;
        this.count = count;
        this.price = price;
        this.productName = productName;
        this.reservationStatus = reservationStatus;
    }

    public ReservationGroup(int count, int price, String productName,Long userId,ReservationStatus reservationStatus) {
        this.userId = userId;
//        this.reservation = reservation;
        this.count = count;
        this.price = price;
        this.productName = productName;
        this.reservationStatus = reservationStatus;
    }
}
