package com.ssafy.thxstore.member.domain;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.reservation.domain.Reservation;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Member implements UserDetails {
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

    @ColumnDescription("유저 위치정보")
    @Column(name = "address")
    private String address;

    @ColumnDescription("유저 닉네임")
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickName;

    @ColumnDescription("유저 프로필 이미지")
    @Column(name = "image")
    private String image;

    @ColumnDescription("유저 휴대폰 번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ColumnDescription("유저 Role")
    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private MemberRole roles;

    @ColumnDescription("소셜로그인 유저 정보")
    @Enumerated(EnumType.STRING)
    @Column(name = "social")
    private Social social;

    @Builder
    public Member(String userId, @Email String email, String password, String address, String nickName, String image, String phoneNumber, MemberRole roles, Social social) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.address = address;
        this.nickName = nickName;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.social = social;
    }

    @Override
    @Description("사용자의 id를 반환(unique 값)")
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> role = new HashSet<>();
        return role
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + roles))
                .collect(Collectors.toSet());
    }

    @Override
    @Description("사용자의 password 반환")
    public String getPassword() {
        return password;
    }

    @Override
    @Description("사용자의 계정 만료 여부 확인")
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    @Override
    @Description("사용자의 계정이 잠금 여부 확인")
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    @Override
    @Description("패스워드 만료여부 확인")
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    @Override
    @Description("계정 사용 가능 여부 확인")
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
