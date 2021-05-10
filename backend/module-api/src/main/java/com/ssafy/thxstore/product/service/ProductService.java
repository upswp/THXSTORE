package com.ssafy.thxstore.product.service;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<List<ProductGroup>> findAllGroup(FindAllGroupDto findAllGroupDto) {
        Optional<List<ProductGroup>> productGroups = productGroupRepository.findAllByStoreId(findAllGroupDto.getStoreId());
        return productGroups;
    }

    public void deleteGroup(DeleteGroupDto deleteGroupDto) {
        // 그룹 아이디 관련 매뉴 전체 삭제. 그룹 삭제 -> 연관매핑 확인
        productGroupRepository.deleteById(deleteGroupDto.getGroupId());
    }

    public void deleteMenu(DeleteMenuDto deleteMenuDto) {
        productRepository.deleteById(deleteMenuDto.getProductId());
    }

    public Optional<List<Product>> findAllGroupMenu(FindAllGroupMenuDto findAllGroupMenuDto) {
        //
        Optional<List<Product>> productList = productRepository.findAllByGroupId(findAllGroupMenuDto.getGroupId());

        return productList;
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
                .build();

        productRepository.save(product);

    }

    public Product findMenu(Long productId) {
        return productRepository.findById(productId).get();
    }
}
