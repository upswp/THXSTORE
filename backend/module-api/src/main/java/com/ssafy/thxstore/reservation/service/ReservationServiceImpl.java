package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.dto.CartDto;
import com.ssafy.thxstore.reservation.dto.OrderRequest;
import com.ssafy.thxstore.reservation.repository.CartRepository;
import com.ssafy.thxstore.reservation.repository.OrderRepository;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReservationServiceImpl implements ReservationService{

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    @Override
    public void addCart(List<CartDto> cartlist){
        List<Cart> cartAntityList = new ArrayList<>();

        for(int i =0 ;i<cartlist.size();i++){
            Optional<Product> product = productRepository.findById(cartlist.get(i).getProductId());
            Optional<Member> member = memberRepository.findById(cartlist.get(i).getUserId());

            Cart cart = Cart.builder().
                    count(cartlist.get(i).getCount()).
                    member(member.get()).
                    product(product.get()).
                    price(product.get().getPrice()).
                    productName(cartlist.get(i).getProductname()).
                    storeId(cartlist.get(i).getStoreId()).
                    build();
            cartAntityList.add(cart);
        }
        cartRepository.saveAll(cartAntityList);
    }

    @Override
    @Transactional
    public List<CartDto> getCart(Long memberId){

        List<CartDto> list = cartRepository.findCartlist(memberId);

        return list;
    }

    /**
     * cart에서 memberId를 통해 한번에 조회
     * 그 후 order 테이블에 넣고 cart 테이블에서 삭제
     */
    @Override
    public void addOrder(Long memberId){

//        Optional<Cart> cart = cartRepository.findById(orderRequest.getCartId());
//        Optional<Member> member = memberRepository.findById(orderRequest.getUserId());
//
//        Reservation reservation = Reservation.builder().
//                member(member.get()).
//                cart(cart.get()).
//                time(LocalDateTime.now()).
//                reservationStatus(orderRequest.getReservationStatus()).
//                build();
//
//        orderRepository.save(reservation);

        List<Reservation> reservationList = reservationRepository.findReservationlist(memberId);
        reservationRepository.saveAll(reservationList);
    }


    /**
     *
     */
//    @Override
//    public List<ReservationDto> getOrder(Long memberId){
//        List<ReservationDto> list = cartRepository.findOrderById(memberId);
//
//
//        return list;
//    }

}
