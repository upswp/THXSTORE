package com.ssafy.thxstore.member.repository;


import com.ssafy.thxstore.member.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);
}
