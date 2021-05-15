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
         */


        Pusher pusher = new Pusher("1203876", "c961ac666cf7baaf084c", "43c7f358035c2a712f23");
        pusher.setCluster("ap3");

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "~~님의 주문이 등록되었습니다."));


        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Member> member = memberRepository.findById(reservationList.getUserId());

        Reservation reservation = Reservation.builder().
                nickname(reservationList.getNickname()).
                reservationStatus(ReservationStatus.DEFAULT).
                storeId(reservationList.getStoreId()).
                member(member.get()).
                dateTime(dateFormat.format(DateTime.now().toDate()) + " " + time).build();

        reservationRepository.save(reservation);

        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){

            List<ReservationGroup> reservationAntityList = new ArrayList<>();

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
            reservationGroupRepository.saveAll(reservationAntityList);
        }
    }

    /**
     * 대서버 -> storeId
     * 중서버 -> memberId(사장님)
     */
    @Override
    @Transactional
    public List<ReservationDto> getReservation(Long Id,String type){


        //        if(type =="store") {  //사장님이 본인 가게에 들어온 주문 확인할때 알림 기능 온
//
////            HttpAuthorizer authorizer = new HttpAuthorizer("http://localhost:8080/api/order/reservation/store/"+Id);
////            PusherOptions options = new PusherOptions().setCluster("ap3").setAuthorizer(authorizer);
////            Pusher pusher = new Pusher(YOUR_APP_KEY, options);
//
//            PusherOptions options = new PusherOptions().setCluster("ap3");
//            Pusher pusher = new Pusher("c961ac666cf7baaf084c", options);  //푸셔 인스턴스를 만들고
//            pusher.connect(); //연결하고 disconnect 언제하지?
//
//
//            pusher.connect(new ConnectionEventListener() {
//                @Override
//                public void onConnectionStateChange(ConnectionStateChange change) {
//                    System.out.println("State changed to " + change.getCurrentState() +
//                            " from " + change.getPreviousState());
//                }
//
//                @Override
//                public void onError(String message, String code, Exception e) {
//                    System.out.println("There was a problem connecting!");
//                }
//            }, ConnectionState.ALL);
//
//
//            //프론트엔드에서 예약주문했을때 서버에 이벤트 보내주고 사장님 채널에 메세지
//            Channel channel = pusher.subscribe("store"+Id+"-channel", new ChannelEventListener() {
//                @Override
//                public void onEvent(PusherEvent event) {
//                    System.out.println("Received event with data: " + event.toString());
//                }
//
//                @Override
//                public void onSubscriptionSucceeded(String channelName) {
//                    System.out.println("Subscribed to channel: " + channelName);
//                }
//                // Other ChannelEventListener methods
//            });
//
//
//        }

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
