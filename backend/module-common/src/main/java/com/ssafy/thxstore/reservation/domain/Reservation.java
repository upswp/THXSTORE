package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id; // pk

    @ColumnDescription("주문 시간")
    @Column(name = "order_time")
    private LocalDateTime time;

    @ColumnDescription("주문 접수,결제 완료, 수령 대기, 수령완료")
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    @ColumnDescription("cart-product-store 접근 가능")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cartId;

    @ColumnDescription("member 매핑")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member reservMember;

    @Builder
    public Reservation(Member member, Cart cartId, LocalDateTime time, ReservationStatus reservationStatus) {
        this.reservMember = member;
        this.cartId = cartId;
        this.time = time;
        this.reservationStatus = reservationStatus;
    }
}
