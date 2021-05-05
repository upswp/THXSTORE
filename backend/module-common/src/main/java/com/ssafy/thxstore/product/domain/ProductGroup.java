package com.ssafy.thxstore.product.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.store.domain.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductGroup {
    
    @Id
    @ColumnDescription("PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id") //
    private Long id;
    
    //store_id
    @ManyToOne // 멤버와 연결
    @ColumnDescription("FK member의 id 참조(필드_컬럼명)")
    @JoinColumn(name = "store_id") // id 맞낭
    private Store store;

    @Column(name = "name")
    @ColumnDescription("그룹 명")
    private String name;

    @Builder
    public ProductGroup(Store store, String name){
        this.store = store;
        this.name = name;
    }


}
