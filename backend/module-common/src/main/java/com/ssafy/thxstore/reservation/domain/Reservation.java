package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.product.domain.Product;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
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

    @ColumnDescription("하나의 주문 당 연결되는 리뷰 한 개")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "store_id")
    private Long storeId;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "order_time")
    private String dateTime;

    @ColumnDescription("양방향 맵핑으로 해당 reservation 에 대한 product 정보 추출 가능")
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ReservationGroup> reservationGroup = new ArrayList<>();

    @ColumnDescription("기본,대기,승인,완료")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus reservationStatus;

    @Builder
    public Reservation(Member member,Long storeId,ReservationStatus reservationStatus, List<ReservationGroup> reservationGroup,String dateTime) {
        this.member = member;
        this.dateTime =dateTime;
        this.storeId = storeId;
        this.reservationStatus = reservationStatus;
        this.reservationGroup = reservationGroup;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void deleteReview() {
        this.review = null;
    }
}
