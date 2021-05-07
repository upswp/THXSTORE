package com.ssafy.thxstore.member.repository;


import com.ssafy.thxstore.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);
    @Query(value =
            "SELECT * FROM member WHERE member.user_id = :userId AND member.social= :social",
            nativeQuery = true)
            Optional<Member> findByUserIdAndSocial(String userId, String social);
    Boolean existsByEmail(String email);
}
