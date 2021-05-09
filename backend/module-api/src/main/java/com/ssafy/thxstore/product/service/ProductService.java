package com.ssafy.thxstore.product.service;

import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.dto.CreateGroupDto;
import com.ssafy.thxstore.product.dto.EditGroupDto;
import com.ssafy.thxstore.product.dto.FindAllGroupDto;
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

    public List<ProductGroup> findAllGroup(FindAllGroupDto findAllGroupDto) {
        List<ProductGroup> productGroups = productGroupRepository.findAllByStoreId(findAllGroupDto.getStoreId());
        return productGroups;
    }
}
