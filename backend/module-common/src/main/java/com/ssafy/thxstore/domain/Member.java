package com.ssafy.thxstore.domain;

import com.ssafy.thxstore.config.MemberRole;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "userId")
public class Member {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "address")
    private String address;

    @Description("")
    @Column(name = "nickname")
    private String nickName;

    @Column(name = "image")
    private String image;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private Set<MemberRole> roles;

//    @Column(name = "check_member_info")
//    private boolean checkMemberInfo;
}
