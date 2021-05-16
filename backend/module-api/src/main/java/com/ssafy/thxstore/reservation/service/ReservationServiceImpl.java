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
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.repository.StoreRepository;
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
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    /**
     * reservation을 생성하고
     * group을 리스트로 saveAll하자
     */
    @Override
    @Transactional
    public String addReservation(String email,ReservationDto reservationList){

        /**
         * 가게를 등록할 때 이벤트를 발생시키고
         * 이벤트가 발생되었다고 프론트에 전달하자
         * 이걸 해당 사장님한테만 전달해야함..
         *
         * 1. 회원이 스토어 등록한다 -> 사장님됨 storeId를 가지고 다녀야 됨
         * 2. 로그인 후 예약페이지 들어갔을 때 if (사장님이면) -> 우축 하단(미정) 채널 구독 상태로 channel-{storeId} 가지고 다니기 (디폴트 안보이게 -> 이벤트 오면 보이게)
         *
         * + 주문등록 주문이 들어왔을 때 ---->  재고 확인 후   stock 다떨어졌으면 품절된 상품이 있습니다 return
         * 로그인할 때 채널 구분      */


        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

//        Optional<Member> member = memberRepository.findById(reservationList.getUserId());
        Optional<Member> member = memberRepository.findByEmail(email);
        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        List<ReservationDto> reservationDtoList = new LinkedList<>();

        Reservation reservation = Reservation.builder().
                email(email).
                nickname(reservationList.getNickname()).
                reservationStatus(ReservationStatus.DEFAULT).
                storeId(reservationList.getStoreId()).
                member(member.get()).
                dateTime(DateTime.now().toString()).build();

        reservationRepository.save(reservation);
        List<ReservationGroup> reservationAntityList = new ArrayList<>();

        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){
            Optional<Product> product = productRepository.findById(reservationList.getReservationGroups().get(i).getProductId());

            if(product.get().getStock() - reservationList.getReservationGroups().get(i).getCount() < 0){

                return "선택하신 메뉴 중에 품절된 상품이 있습니다";
            }

            ReservationGroup reservationGroup = ReservationGroup.builder().
                    rate(reservationList.getReservationGroups().get(i).getRate()).
                    storeId(reservationList.getStoreId()).
                    userId(reservationList.getUserId()).
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(reservation).
                    price(reservationList.getReservationGroups().get(i).getPrice()).
                    productName(reservationList.getReservationGroups().get(i).getProductName()).
                    build();

            //저장하기전 -> 구매 수량 만큼 재고 마이너스
            // 인트로 바꿔서 연산 하고 stockUpdate 메소드에서 INTEGER로 바꿔서 저장
            product.get().stockUpdate(Integer.parseInt(""+product.get().getStock()) - reservationList.getReservationGroups().get(i).getCount());
            reservationAntityList.add(reservationGroup);
        }
        reservationGroupRepository.saveAll(reservationAntityList);

        Pusher pusher = new Pusher("1203876", "c961ac666cf7baaf084c", "43c7f358035c2a712f23");
        pusher.setCluster("ap3");
        reservationList.updateOrderTime(DateTime.now().toString());

//            pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", Collections.singletonMap("message","회원번호: "+reservationList.getUserId()+ "님의 주문이 등록되었습니다."));
        pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", reservationList);
        return "주문이 완료되었습니다.";
    }

    /**
     * 대서버 -> storeId
     * 중서버 -> memberId(사장님)
     */
    @Override
    @Transactional
    public List<ReservationDto> getReservation(String email,String type){

        List<ReservationDto> reservationDtoList = new LinkedList<>();
        List<ReservationGroup> list;
        List<Reservation> reservationlist;


        //dto 엔티티 매핑
        //memberid 검색 storeid 검색 각각 주문 size 구하자
        //기본키로 찾아야하는데 일단 쿼리문 적은 email로
        if(type == "member") {
            reservationlist = reservationRepository.findReservationByMemberId(email); //reservation list 찾아와서 2개 각각의 프로덕트만 넣어줘야해 51 52
            System.out.println("reservationlist.size(): "+reservationlist.size());

            for(int i = 0 ;i<reservationlist.size();i++){//2개
                List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
                list = reservationGroupRepository.findReservationlistByMemberId(reservationlist.get(i).getId());
                System.out.println("reservationlist.get(i).getId(): " + reservationlist.get(i).getId());

                for(int j =0 ;j<list.size(); j++){  // 57 -2 ,58 -2
                    ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                            count(list.get(j).getCount()).
                            price(list.get(j).getPrice()).
                            rate(list.get(j).getRate()).
                            productName(list.get(j).getProductName()).
                            build();

                    reservationGroupDtoList.add(reservationGroupDto);

                }

//2개 57, 58에 각각 상품그룹 넣어줘
                    ReservationDto reservationDto = ReservationDto.builder().
                            email(reservationlist.get(i).getEmail()).
                            storeId(reservationlist.get(i).getStoreId()).
                            reservationStatus(reservationlist.get(i).getReservationStatus()).
                            nickname(reservationlist.get(i).getNickname()).
                            orderTime(reservationlist.get(i).getDateTime()).
                            userId(reservationlist.get(i).getMember().getId()).
                            reservationGroups(reservationGroupDtoList).
                            build();

                    reservationDtoList.add(reservationDto);

            }//for

            return reservationDtoList;
        }else{
            //store -member 간 onetoone 연관관계 주인 store 에게 있어서 쿼리 2 번
            //join 써서 한번으로
            Optional<Store> store= storeRepository.findByEmailJoin(email);
            // TODO: 2021-05-15 해당 맴버로 생성된 스토어가 없습니다 예외처리 리턴
            reservationlist = reservationRepository.findReservationByStoreId(store.get().getId());

            for(int i = 0 ;i<reservationlist.size();i++){//2개
                List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
                list = reservationGroupRepository.findReservationlistByMemberId(reservationlist.get(i).getId());
                System.out.println("reservationlist.get(i).getId(): " + reservationlist.get(i).getId());

                for(int j =0 ;j<list.size(); j++){  // 57 -2 ,58 -2
                    ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                            count(list.get(j).getCount()).
                            price(list.get(j).getPrice()).
                            rate(list.get(j).getRate()).
                            productName(list.get(j).getProductName()).
                            build();

                    reservationGroupDtoList.add(reservationGroupDto);

                }

//2개 57, 58에 각각 상품그룹 넣어줘
                ReservationDto reservationDto = ReservationDto.builder().
                        email(reservationlist.get(i).getEmail()).
                        storeId(reservationlist.get(i).getStoreId()).
                        reservationStatus(reservationlist.get(i).getReservationStatus()).
                        nickname(reservationlist.get(i).getNickname()).
                        orderTime(reservationlist.get(i).getDateTime()).
                        userId(reservationlist.get(i).getMember().getId()).
                        reservationGroups(reservationGroupDtoList).
                        build();

                reservationDtoList.add(reservationDto);

            }//for
        }//else

        return reservationDtoList;
    }


    // email -> store 일경우 사장님 Id ->memberId           member일 경우 email -> 맴버 꺼 Id -> storeId
    @Override
    @Transactional
    public String deleteReservation(String email,Long Id,String type){


        if(type == "store"){
            Optional<Store> store= storeRepository.findByEmailJoin(email);

            List<ReservationGroup> order = reservationGroupRepository.findAllByMemberIdAndStoreId(Id,store.get().getId());
            if(!order.get(0).getReservation().equals(ReservationStatus.DEFAULT)){
                return "주문이 접수 상태로 넘어가 취소할 수 없습니다.";
            }
            reservationGroupRepository.deleteAll(order);
            return "취소했습니다.";
        }else{
            Optional<Member> member= memberRepository.findByEmail(email);


            System.out.println("member.get().getId() : "+member.get().getId() );
            List<ReservationGroup> order = reservationGroupRepository.findAllByMemberIdAndStoreId(member.get().getId(),Id);
            if(!order.get(0).getReservation().equals(ReservationStatus.DEFAULT)){
                return "주문이 접수 상태로 넘어가 취소할 수 없습니다.";
            }
            reservationGroupRepository.deleteAll(order);
            return "취소했습니다.";
        }
    }

    @Override
    public String statusUpdate(String email, StatusRequest status){
//        Optional<Reservation> nowReservation= reservationRepository.findByMember(status.getMemberId());

        reservationRepository.findReservation(email,status.getStoreId(),status.getReservationStatus().name());
        return "변경되었습니다";
    }
}
