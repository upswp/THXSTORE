package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.ReservationGroupDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;
import com.ssafy.thxstore.reservation.repository.ReservationGroupRepository;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
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
        Optional<Member> member = memberRepository.findById(reservationList.getUserId());

        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){

            List<ReservationGroup> reservationAntityList = new ArrayList<>();
            ReservationGroup reservationGroup = ReservationGroup.builder().
                    reservationStatus(reservationList.getReservationStatus()).
                    userId(reservationList.getUserId()).
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(Reservation.builder().member(member.get()).
                            storeId(reservationList.getStoreId()).
                            reservationStatus(reservationList.getReservationStatus()).
                            reservationGroup(reservationAntityList).build()).
                    price(reservationList.getReservationGroups().get(i).getPrice()).
                    productName(reservationList.getReservationGroups().get(i).getProductName()).
                    build();

            reservationAntityList.add(reservationGroup);
            reservationGroupRepository.saveAll(reservationAntityList);
        }
    }

    @Override
    @Transactional
    public List<ReservationGroupDto> getReservation(Long memberId){
        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        //dto 엔티티 매핑
        List<ReservationGroup> list = reservationGroupRepository.findReservationlist(memberId);

        for(int i =0 ;i<list.size(); i++){
            ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                    userId(list.get(i).getUserId()).
                    count(list.get(i).getCount()).
                    price(list.get(i).getPrice()).
                    productName(list.get(i).getProductName()).
                    reservationStatus(list.get(i).getReservationStatus()).
                    build();

            reservationGroupDtoList.add(reservationGroupDto);
        }
        return reservationGroupDtoList;
    }


//    @Override
//    public void deleteReservation(Long memberId,Long storeId){
////        Optional<Member> member = memberRepository.findById(memberId);
//        reservationRepository.deleteReservation(memberId,storeId);
//    }

//    @Override
//    public void statusUpdate(Long memberId, StatusRequest status){
//        reservationRepository.findReservation(memberId,status.getStoreId(),status.getReservationStatus().name());
//    }
}
