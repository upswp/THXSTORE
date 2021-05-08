package com.ssafy.thxstore.member.repository;


import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);
    Boolean existsByUserIdAndSocial(String userId, Social social);
    Boolean existsByEmail(String email);
}
