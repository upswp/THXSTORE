package com.ssafy.thxstore.reservation.service;

import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;
import com.pusher.rest.Pusher;
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
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.channel.PusherEvent;

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

        /**
         * 가게를 등록할 때 이벤트를 발생시키고
         * 이벤트가 발생되었다고 프론트에 전달하자
         * 이걸 해당 사장님한테만 전달해야함..
         *
         * 1. 회원이 스토어 등록한다 -> 사장님됨 storeId를 가지고 다녀야 됨
         * 2. 로그인 후 예약페이지 들어갔을 때 if (사장님이면) -> 우축 하단(미정) 채널 구독 상태로 channel-{storeId} 가지고 다니기 (디폴트 안보이게 -> 이벤트 오면 보이게)
         *
         * 로그인할 때 채널 구분       */


        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Member> member = memberRepository.findById(reservationList.getUserId());
        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        List<ReservationDto> reservationDtoList = new LinkedList<>();

        Reservation reservation = Reservation.builder().
                nickname(reservationList.getNickname()).
                reservationStatus(ReservationStatus.DEFAULT).
                storeId(reservationList.getStoreId()).
                member(member.get()).
                dateTime(DateTime.now() + " " + time).build();

        reservationRepository.save(reservation);
        List<ReservationGroup> reservationAntityList = new ArrayList<>();

        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){

            ReservationGroup reservationGroup = ReservationGroup.builder().
                    rate(reservationList.getReservationGroups().get(i).getRate()).
                    storeId(reservationList.getStoreId()).
                    userId(reservationList.getUserId()).
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(reservation).
                    price(reservationList.getReservationGroups().get(i).getPrice()).
                    productName(reservationList.getReservationGroups().get(i).getProductName()).
                    build();

            reservationAntityList.add(reservationGroup);
        }
        reservationGroupRepository.saveAll(reservationAntityList);

        Pusher pusher = new Pusher("1203876", "c961ac666cf7baaf084c", "43c7f358035c2a712f23");
        pusher.setCluster("ap3");
        reservationList.updateOrderTime(DateTime.now() + " " + time);

//            pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", Collections.singletonMap("message","회원번호: "+reservationList.getUserId()+ "님의 주문이 등록되었습니다."));
        pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", reservationList);
    }

    /**
     * 대서버 -> storeId
     * 중서버 -> memberId(사장님)
     */
    @Override
    @Transactional
    public List<ReservationDto> getReservation(Long Id,String type){

        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        List<ReservationDto> reservationDtoList = new LinkedList<>();
        List<ReservationGroup> list;
        List<Reservation> reservationlist;
        //dto 엔티티 매핑
        //memberid 검색 storeid 검색 각각 주문 size 구하자
        if(type == "member") {
            list = reservationGroupRepository.findReservationlistByMemberId(Id);
            reservationlist = reservationRepository.findReservationByMemberId(Id);
        }else{
            list = reservationGroupRepository.findReservationlistByStoreId(Id);
            reservationlist = reservationRepository.findReservationByStoreId(Id);
        }

        //주문 - 주문 내용
        for(int i =0 ;i<list.size(); i++){
            ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                    count(list.get(i).getCount()).
                    price(list.get(i).getPrice()).
                    rate(list.get(i).getRate()).
                    productName(list.get(i).getProductName()).
                    build();

            reservationGroupDtoList.add(reservationGroupDto);
        }

        for(int i =0 ;i<reservationlist.size(); i++) {
            ReservationDto reservationDto = ReservationDto.builder().
                    storeId(reservationlist.get(i).getStoreId()).
                    reservationStatus(reservationlist.get(i).getReservationStatus()).
                    nickname(reservationlist.get(i).getNickname()).
                    orderTime(reservationlist.get(i).getDateTime()).
                    userId(reservationlist.get(i).getMember().getId()).
                    reservationGroups(reservationGroupDtoList).
                    build();

            reservationDtoList.add(reservationDto);
        }

        return reservationDtoList;
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
