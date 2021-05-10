package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
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

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    @Override
    public void addReservation(List<ReservationDto> reservationList){
        List<Reservation> reservationAntityList = new ArrayList<>();

        for(int i =0 ;i<reservationList.size();i++){
            Optional<Product> product = productRepository.findById(reservationList.get(i).getProductId());
            Optional<Member> member = memberRepository.findById(reservationList.get(i).getUserId());

            Reservation reservation = Reservation.builder().
                    count(reservationList.get(i).getCount()).
                    member(member.get()).
                    product(product.get()).
                    price(product.get().getPrice()).
                    productName(reservationList.get(i).getProductname()).
                    storeId(reservationList.get(i).getStoreId()).
                    reservationStatus(reservationList.get(i).getReservationStatus()).
                    build();
            reservationAntityList.add(reservation);
        }
        reservationRepository.saveAll(reservationAntityList);
    }

    @Override
    @Transactional
    public List<ReservationDto> getReservation(Long memberId){

        List<ReservationDto> list = reservationRepository.findgetReservationlist(memberId);

        return list;
    }


    @Override
    public void deleteReservation(Long memberId,Long storeId){
//        Optional<Member> member = memberRepository.findById(memberId);
        reservationRepository.deleteReservation(memberId,storeId);
    }

}
