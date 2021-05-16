package com.ssafy.thxstore.member.domain;

import com.ssafy.thxstore.common.BaseTimeEntity;
import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDescription("PK")
    private Long id;

    @ColumnDescription("유저가 좋아하는 가게 리스트")
    @OneToMany
    private List<Favorite> storeList = new LinkedList<>();

    @ColumnDescription("유저가 주문한 리스트[오더리스트]")
    @OneToMany
    private List<Reservation> reservationList = new ArrayList<>();

    @ColumnDescription("소셜로그인 유저 ID")
    @Column(name = "user_id", unique = true)
    private String userId;

    @Email
    @ColumnDescription("유저 이메일")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ColumnDescription("유저 비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    @ColumnDescription("유저 위치정보 위도")
    @Column(name = "lat")
    private Double lat;

    @ColumnDescription("유저 위치정보 경도")
    @Column(name = "lon")
    private Double lon;

    @ColumnDescription("유저 위치정보 주소")
    @Column(name = "address")
    private String address;

    @ColumnDescription("유저 닉네임")
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @ColumnDescription("유저 프로필 이미지")
    @Column(name = "profile_image")
    private String profileImage;

    @ColumnDescription("유저 휴대폰 번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ColumnDescription("유저 Role")
    private MemberRole role;

    @ColumnDescription("소셜로그인 유저 정보")
    @Enumerated(EnumType.STRING)
    @Column(name = "social")
    private Social social;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Member(Long id, String userId, @Email String email, String password, Double lat, String address, Double lon, String nickname, String profileImage, String phoneNumber, MemberRole role, Social social) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.social = social;
    }

    public String roleName(){
        return role.name();
    }
}
