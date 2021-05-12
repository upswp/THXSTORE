package com.ssafy.thxstore.product.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.dto.*;
import com.ssafy.thxstore.product.repository.ProductGroupRepository;
import com.ssafy.thxstore.product.repository.ProductRepository;
import com.ssafy.thxstore.product.repository.TimeDealRepository;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductService {

    private final ProductGroupRepository productGroupRepository;
    private final ProductRepository productRepository;
    private final TimeDealRepository timeDealRepository;
    private final StoreRepository storeRepository;
    private final ImageService imageService;

    public void createGroup(CreateGroupDto createGroupDto) {
        Store store = storeRepository.findById(createGroupDto.getStoreId()).get();
        ProductGroup productGroup = ProductGroup.builder()
                .name(createGroupDto.getName())
                .store(store)
                .build();
        productGroupRepository.save(productGroup);
    }

    public void editGroup(EditGroupDto editGroupDto) {
        ProductGroup productGroup = productGroupRepository.findById(editGroupDto.getGroupId()).get();
        productGroup.setName(editGroupDto.getName());
    }

    public Optional<List<ProductGroup>> findAllGroup(Long storeId) {
        Optional<List<ProductGroup>> productGroups = productGroupRepository.findAllByStoreId(storeId);
        return productGroups;
    }

    public void deleteGroup(Long groupId) {
        // 그룹 아이디 관련 매뉴 전체 삭제. 그룹 삭제 -> 연관매핑 확인
        productRepository.deleteByProductGroupId(groupId);
        productGroupRepository.deleteById(groupId);
    }

    public void deleteMenu(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<FindAllGroupMenuDto> findAllGroupMenu(Long groupId) {
        //
        List<Product> productList = productRepository.findAllByProductGroupId(groupId).get();

        List<FindAllGroupMenuDto> findAllGroupMenuDto = new ArrayList<>();

        for(int i = 0; i < productList.size(); i++){
            findAllGroupMenuDto.add(FindAllGroupMenuDto.builder()
                    .productId(productList.get(i).getId())
                    .name(productList.get(i).getName())
                    .price(productList.get(i).getPrice())
                    .productImg(productList.get(i).getProductImg())
                    .amount(productList.get(i).getAmount())
                    .rate(productList.get(i).getRate())
                    .stock(productList.get(i).getStock())
                    .introduce(productList.get(i).getIntroduce())
                    .build());
        }

        return findAllGroupMenuDto;
    }

    public void createMenu(String productImg, CreateMenuDto createMenuDto) {
        //
        ProductGroup productGroup = productGroupRepository.findById(createMenuDto.getGroupId()).get();
        Product product = Product.builder()
                .name(createMenuDto.getName())
                .price(createMenuDto.getPrice())
                .productImg(productImg)
                .amount(createMenuDto.getAmount())
                .productGroup(productGroup)
                .introduce(createMenuDto.getIntroduce())
                .build();

        productRepository.save(product);

    }

    public DetailProductDto findMenu(Long productId) {
        Product product = productRepository.findById(productId).get();

        DetailProductDto detailProductDto = DetailProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productImg(product.getProductImg())
                .amount(product.getAmount())
                .rate(product.getRate())
                .stock(product.getStock())
                .introduce(product.getIntroduce())
                .build();

        return detailProductDto;
    }

    public void editMenu(EditMenuDto editMenuDto) {
        Product product = productRepository.findById(editMenuDto.getProductId()).get();
        String productImg = null;
        if(editMenuDto.getName() != null){
            product.setName(editMenuDto.getName());
        }else if(editMenuDto.getAmount() != null){
            product.setAmount(editMenuDto.getAmount());
        }else if(editMenuDto.getPrice() != null){
            product.setPrice(editMenuDto.getPrice());
        }else if(editMenuDto.getIntroduce() != null){
            product.setIntroduce(editMenuDto.getIntroduce());
        }else if(editMenuDto.getProductImg() != null) {
            try {
                productImg = imageService.createImage(editMenuDto.getProductImg());
            } catch (IOException e) {
                return;
            }
            product.setProductImg(productImg);
        }
//        product.setName(editMenuDto.getName());
//        product.setPrice(editMenuDto.getPrice());
//        product.setAmount(editMenuDto.getAmount());
//        if(productImg != null){
//            product.setProductImg(productImg);
//        }

    }
}
