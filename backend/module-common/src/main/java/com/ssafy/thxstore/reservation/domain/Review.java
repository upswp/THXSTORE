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
public class Review {

    @Id
    @ColumnDescription("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id; // pk


    @ColumnDescription("comment")
    @Column(name = "comment",length = 100)
    private String comment;

    @Column(name = "star")
    private int star;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "order_time")
    private String dateTime;

    @Column(name = "prodcut_name")
    private String productName;

    @ColumnDescription("Reservation에 대한 양방향 맵핑")
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Review(String comment, int star, String dateTime, String productName) {
        this.comment = comment;
        this.star =star;
        this.dateTime = dateTime;
        this.productName = productName;
    }
}
