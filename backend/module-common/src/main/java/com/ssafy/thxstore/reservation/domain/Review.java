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

    @ColumnDescription("memberId")
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "star")
    private int star;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_id")
    private Long storeId;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "write_time")
    private String dateTime;

    @ColumnDescription("엔서 매핑")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer2;

    @Builder
    public Review(Long id,String comment, int star, String dateTime,Long memberId,String storeName,Long storeId) {
        this.comment = comment;
        this.id = id;
        this.star =star;
        this.dateTime = dateTime;
        this.memberId = memberId;
        this.storeName =storeName;
        this.storeId = storeId;
    }
}
