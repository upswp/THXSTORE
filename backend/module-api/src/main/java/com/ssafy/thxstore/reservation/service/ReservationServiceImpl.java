package com.ssafy.thxstore.reservation.service;

import com.pusher.rest.Pusher;
import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.dto.ProductDto;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.ReservationGroupDto;
import com.ssafy.thxstore.reservation.dto.request.StatusRequest;
import com.ssafy.thxstore.reservation.repository.ReservationGroupRepository;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<String> addReservation(String email,ReservationDto reservationList){

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
        Optional<Store> store =storeRepository.findById(reservationList.getStoreId());

        List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
        List<ReservationDto> reservationDtoList = new LinkedList<>();
        List<String> outOfStock = new LinkedList<>();

        Reservation reservation = Reservation.builder().
                email(store.get().getMember().getEmail()).  //스토어 사장님의 이메일이 들어가야해
                nickname(reservationList.getNickname()).
                reservationStatus(ReservationStatus.DEFAULT).
                storeId(reservationList.getStoreId()).
                member(member.get()).
                dateTime(DateTime.now().toString()).build();

        reservationRepository.save(reservation);
        List<ReservationGroup> reservationAntityList = new ArrayList<>();

        Boolean stockflag = false; //true로 바뀌면 재고 떨어짐
        List<Integer> productindex= new ArrayList<>();
        Optional<Product> product = Optional.of(new Product());
        for(int i =0 ;i<reservationList.getReservationGroups().size();i++){
            product = productRepository.findById(reservationList.getReservationGroups().get(i).getProductId());

            if(product.get().getStock() - reservationList.getReservationGroups().get(i).getCount() < 0){  //제고가 없는 경우-> 재고 없음 플러그 + outofstock에 제품이름추가
                stockflag =true;
                outOfStock.add(reservationList.getReservationGroups().get(i).getProductName());
            }

            ReservationGroup reservationGroup = ReservationGroup.builder().
                    product(product.get()).
                    rate(reservationList.getReservationGroups().get(i).getRate()).
                    storeId(reservationList.getStoreId()).
                    userId(reservationList.getUserId()).
                    count(reservationList.getReservationGroups().get(i).getCount()).
                    reservation(reservation).
                    price(reservationList.getReservationGroups().get(i).getPrice()).
                    productName(reservationList.getReservationGroups().get(i).getProductName()).
                    build();

            productindex.add(i);
            reservationAntityList.add(reservationGroup);
        }
        if(stockflag == true){   //재고 - 인거 있다면 저장안함  -> outofstock에 저장된 제품 이름 리턴
            return outOfStock;
        }

        //제고 -인게 없다면 (무사 통과)  -> 각 재고 마이너스 처리 해주고 (List<Integer> productindex)로 -> 재고가 마이너스인경우 넘어오지도 않음 빼도됨!
        // 인트로 바꿔서 연산 하고 stockUpdate 메소드에서 INTEGER로 바꿔서 저장
        for(int i =0 ;i< productindex.size(); i++) {
            product.get().stockUpdate(Integer.parseInt("" + product.get().getStock()) - reservationList.getReservationGroups().get(productindex.get(i)).getCount());
        }
        reservationGroupRepository.saveAll(reservationAntityList);

        Pusher pusher = new Pusher("1203876", "c961ac666cf7baaf084c", "43c7f358035c2a712f23");
        pusher.setCluster("ap3");
        reservationList.updateOrderTime(DateTime.now().toString());
        reservationList.addreservationId(reservation.getId());
//            pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", Collections.singletonMap("message","회원번호: "+reservationList.getUserId()+ "님의 주문이 등록되었습니다."));
        pusher.trigger(reservationList.getStoreId()+"-channel", "my-event", reservationList);

        outOfStock.add("주문가능");//outofstock의 0인덱스 -> 제고있음
        outOfStock.add(""+reservation.getId());
        return outOfStock;
    }

    /**
     * 대서버 -> storeId
     * 중서버 -> memberId(사장님)
     */
    @Override
    @Transactional
    public List<ReservationDto> getReservation(String email,String type) throws ParseException {

        List<ReservationDto> reservationDtoList = new LinkedList<>();
        List<ReservationGroup> list;
        List<Reservation> reservationlist;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");



        //dto 엔티티 매핑
        //memberid 검색 storeid 검색 각각 주문 size 구하자
        //기본키로 찾아야하는데 일단 쿼리문 적은 email로
        if(type == "member") {

            Optional<Member> member = memberRepository.findByEmail(email);
            reservationlist = reservationRepository.findReservationByMemberId(member.get().getId()); //reservation list 찾아와서 2개 각각의 프로덕트만 넣어줘야해 51 52

            for(int i = 0 ;i<reservationlist.size();i++){//2개
                List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
                list = reservationGroupRepository.findReservationlistByMemberId(reservationlist.get(i).getId());


                for(int j =0 ;j<list.size(); j++){  // 57 -2 ,58 -2
                    ReservationGroupDto reservationGroupDto = ReservationGroupDto.builder().
                            count(list.get(j).getCount()).
                            price(list.get(j).getPrice()).
                            rate(list.get(j).getRate()).
                            productName(list.get(j).getProductName()).
                            build();

                    reservationGroupDtoList.add(reservationGroupDto);

                }

                Optional<Store> store = storeRepository.findById(reservationlist.get(i).getStoreId());
//2개 57, 58에 각각 상품그룹 넣어줘d
                    ReservationDto reservationDto = ReservationDto.builder().
                            reservationId(reservationlist.get(i).getId()).
                            storeImg(store.get().getLogo()).
                            storeName(store.get().getName()).
                            email(reservationlist.get(i).getEmail()).
                            storeId(reservationlist.get(i).getStoreId()).
                            reservationStatus(reservationlist.get(i).getReservationStatus()).
                            nickname(reservationlist.get(i).getNickname()).
                            orderTime(reservationlist.get(i).getDateTime()).
                            userId(reservationlist.get(i).getMember().getId()).
                            reservationGroups(reservationGroupDtoList).
                            build();

                    reservationDtoList.add(reservationDto);
                    reservationDtoList.sort(Comparator.reverseOrder());
            }//for

            return reservationDtoList;
        }else{

            //store -member 간 onetoone 연관관계 주인 store 에게 있어서 쿼리 2 번
            //join 써서 한번으로
            Optional<Store> store= storeRepository.findByEmailJoin(email);
            // TODO: 2021-05-15 해당 맴버로 생성된 스토어가 없습니다 예외처리 리턴
            reservationlist = reservationRepository.findReservationByStoreId(store.get().getId());

            for(int i = 0 ;i<reservationlist.size();i++){//4개

                if(DateTime.now().toDate().getDate() != dateFormat.parse(reservationlist.get(i).getDateTime()).getDate()) {continue;}
                List<ReservationGroupDto> reservationGroupDtoList = new LinkedList<>();
                list = reservationGroupRepository.findReservationlistByMemberId(reservationlist.get(i).getId());

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
                        reservationId(reservationlist.get(i).getId()).
                        reservationStatus(reservationlist.get(i).getReservationStatus()).
                        nickname(reservationlist.get(i).getNickname()).
                        orderTime(reservationlist.get(i).getDateTime()).
                        userId(reservationlist.get(i).getMember().getId()).
                        reservationGroups(reservationGroupDtoList).
                        build();

                reservationDtoList.add(reservationDto);
                reservationDtoList.sort(Comparator.reverseOrder());
            }//for
        }//else

        return reservationDtoList;
    }


    // email -> store 일경우 사장님 Id ->memberId           member일 경우 email -> 맴버 꺼 Id -> storeId
    @Override
    @Transactional
    public String deleteReservation(String email,Long reservationId,String type){


        if(type == "store"){
            Optional<Store> store= storeRepository.findByEmailJoin(email);

            List<ReservationGroup> order = reservationGroupRepository.findAllByReservationId(reservationId);
            if(!order.get(0).getReservation().getReservationStatus().equals(ReservationStatus.DEFAULT)){
                return "해당 주문이 접수되어 취소할 수 없습니다.";
            }
            // 주문 생성하며 빠졌던 재고를 되돌리는 로직
            //reservation_group의 카운트 만큼 prodcut의 stock 에 더해준다

            for(int i = 0 ;i<order.size(); i++){
                Optional<Product> product = productRepository.findById(order.get(i).getProduct().getId());  //어떤 제품인지 확인

                product.get().updatestock(product.get().getStock() +order.get(i).getCount());
            }
            reservationGroupRepository.deleteAll(order);
            return "취소했습니다";
        }else{
            Optional<Member> member= memberRepository.findByEmail(email);

            List<ReservationGroup> order = reservationGroupRepository.findAllByReservationId(reservationId);

            if(!order.get(0).getReservation().getReservationStatus().equals(ReservationStatus.DEFAULT)){
                return "주문이 접수 상태로 넘어가 취소할 수 없습니다.";
            }

            for(int i = 0 ;i<order.size(); i++){
                Optional<Product> product = productRepository.findById(order.get(i).getProduct().getId());  //어떤 제품인지 확인

                product.get().updatestock(product.get().getStock() +order.get(i).getCount());
            }

            reservationGroupRepository.deleteAll(order);
            return "취소했습니다";
        }
    }

    @Override
    @Transactional
    public String statusUpdate(String email, StatusRequest status){
//        Optional<Reservation> nowReservation= reservationRepository.findByMember(status.getMemberId());

        //findby로 찾고 상태 업데이트 하자
        Optional<Reservation> reservation = reservationRepository.findById(status.getReservationId());

        if(reservation.isPresent()) {
            reservation.get().updateStatus(status.getReservationStatus());
            return "변경되었습니다";
        }
        else{
//            return "이미 취소된 주문입니다.";
            throw new AuthException(ErrorCode.STATUS_UPDATE);
        }
    }
}
