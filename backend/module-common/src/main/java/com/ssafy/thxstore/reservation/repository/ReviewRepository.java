package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Transactional
    @Query(value = "update review set comment = :comment , star = :star where review_id = :reviewId",nativeQuery = true)
    void updateReview(Long reviewId, String comment, int star);

    @Query(value = "select * from review where member_id = :id",nativeQuery = true)
    List<Review> findReviewByMemberId(Long id);

    @Query(value = "select * from review where store_id = :id",nativeQuery = true)
    List<Review> findReviewByStoreId(Long id);

    @Query(value = "select * from review where member_id = :memberId and store_id= :storeId",nativeQuery = true)
    Optional<Review> findByMemberIdAndStoreId(Long memberId,Long storeId);
}
