package com.ssafy.thxstore.member.dto;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_sequence_gen")
    @SequenceGenerator(
            name = "favorite_sequence_gen",
            sequenceName = "favorite_sequence"
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
}
