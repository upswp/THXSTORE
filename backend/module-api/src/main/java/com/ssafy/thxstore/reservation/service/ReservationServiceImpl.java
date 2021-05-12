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
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.*;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReservationServiceImpl implements ReservationService{

    private final ReservationGroupRepository reservationGroupRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    /**
     * reservation을 생성하고
     * group을 리스트로 saveAll하자
     */
    @Override
    @Transactional
    public void addReservation(ReservationDto reservationList){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Member> member = memberRepository.findById(reservationList.getUserId());

        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){

            List<ReservationGroup> reservationAntityList = new ArrayList<>();
            ReservationGroup reservationGroup = ReservationGroup.builder().
                    storeId(reservationList.getStoreId()).
                    userId(reservationList.getUserId()).
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(Reservation.builder().
                            member(member.get()).
                            dateTime(dateFormat.format(DateTime.now().toDate()) + " " + time).
                            storeId(reservationList.getStoreId()).
                            reservationStatus(ReservationStatus.DEFAULT).
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
    public List<ReservationGroupDto> getReservation(Long Id,String type){
        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        List<ReservationGroup> list = new LinkedList<>();
        //dto 엔티티 매핑
        if(type == "member") {
            list = reservationGroupRepository.findReservationlistByMemberId(Id);
        }else{
            list = reservationGroupRepository.findReservationlistByStoreId(Id);
        }

        for(int i =0 ;i<list.size(); i++){
            ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                    orderTime(list.get(i).getReservation().getDateTime()).
                    userId(list.get(i).getUserId()).
                    count(list.get(i).getCount()).
                    price(list.get(i).getPrice()).
                    productName(list.get(i).getProductName()).
                    reservationStatus(list.get(i).getReservation().getReservationStatus()).
                    build();

            reservationGroupDtoList.add(reservationGroupDto);
        }
        return reservationGroupDtoList;
    }


    @Override
    @Transactional
    public void deleteReservation(Long memberId,Long storeId){
        List<ReservationGroup> order = reservationGroupRepository.findAllByUserIdAndStoreId(memberId,storeId);
        reservationGroupRepository.deleteAll(order);
    }

    @Override
    public void statusUpdate(StatusRequest status){
        reservationRepository.findReservation(status.getMemberId(),status.getStoreId(),status.getReservationStatus().name());
    }
}
