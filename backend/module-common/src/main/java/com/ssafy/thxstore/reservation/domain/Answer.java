package com.ssafy.thxstore.reservation.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {

    @Id
    @ColumnDescription("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id; // pk

    @ColumnDescription("comment")
    @Column(name = "comment",length = 100)
    private String comment;

    @ColumnDescription("memberId")
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_id")
    private Long storeId;

    @ColumnDescription("스토어 아이디로 각각의 장바구니 구분")
    @Column(name = "write_time")
    private String dateTime;

    @ColumnDescription("양방향 맵핑 리뷰 삭제 시 답변도 삭제")
    @OneToOne(mappedBy = "answer", fetch = FetchType.LAZY)
    private Review review = new Review();

    @Builder
    public Answer(String comment, String dateTime,Long memberId,String storeName,Long storeId) {
        this.comment = comment;
        this.dateTime = dateTime;
        this.memberId = memberId;
        this.storeName =storeName;
        this.storeId = storeId;
    }
}
