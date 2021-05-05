package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.dto.CartDto;
import com.ssafy.thxstore.reservation.repository.CartRepository;
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

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public void addCart(List<CartDto> cartlist){
        List<Cart> cartAntityList = new ArrayList<>();

        for(int i =0 ;i<cartlist.size();i++){
            Optional<Product> product = productRepository.findById(cartlist.get(i).getProductId());
            Optional<Member> member = memberRepository.findById(cartlist.get(i).getUserId());

            Cart cart = Cart.builder().
                    count(cartlist.get(i).getCount()).member(member.get()).
                    product(product.get()).price(product.get().getPrice()).productName(product.get().getName()).
                    build();
            cartAntityList.add(cart);
        }
        cartRepository.saveAll(cartAntityList);
    }
}
