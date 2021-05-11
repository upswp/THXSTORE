package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;
import com.ssafy.thxstore.reservation.repository.ReservationGroupRepository;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReservationServiceImpl implements ReservationService{

    private final ReservationGroupRepository reservationGroupRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    @Override
    public void addReservation(ReservationDto reservationList){
        Reservation reservation = new Reservation();
        List<ReservationGroup> reservationAntityList = new ArrayList<>();
        Optional<Member> member = memberRepository.findById(reservationList.getUserId());

        System.out.println("이거 뭐냐고: "+ reservationList.getReservationGroups().size());
        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){

            ReservationGroup reservationGroup = ReservationGroup.builder().
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(Reservation.builder().member(member.get()).
                            storeId(reservationList.getStoreId()).
                            reservationStatus(reservationList.getReservationStatus()).
                            reservationGroup(reservationAntityList).build()).
                    price(reservationList.getReservationGroups().get(i).getPrice()).
                    productName(reservationList.getReservationGroups().get(i).getProductName()).
                    build();
            reservationAntityList.add(reservationGroup);
        }
        reservationGroupRepository.saveAll(reservationAntityList);
    }

//    @Override
//    @Transactional
//    public List<ReservationDto> getReservation(Long memberId){
//
//        List<ReservationDto> list = reservationRepository.findgetReservationlist(memberId);
//
//        return list;
//    }


//    @Override
//    public void deleteReservation(Long memberId,Long storeId){
////        Optional<Member> member = memberRepository.findById(memberId);
//        reservationRepository.deleteReservation(memberId,storeId);
//    }
//
//    @Override
//    public void statusUpdate(Long memberId, StatusRequest status){
//        reservationRepository.findReservation(memberId,status.getStoreId(),status.getReservationStatus().name());
//    }
}
