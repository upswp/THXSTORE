package com.ssafy.thxstore.member.domain;

import com.ssafy.thxstore.member.dto.Member;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    public void builder(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .nickName("helloThxStore")
                .build();
        assertThat(member).isNotNull();
    }

    @Test
    public void javaBean(){
        //Given
        String email = "test@gmail.com";
        String nickname = "helloThxStore";

        //When
        Member member = new Member();
        member.setEmail(email);
        member.setNickName(nickname);

        //Then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getNickName()).isEqualTo(nickname);
    }
}
