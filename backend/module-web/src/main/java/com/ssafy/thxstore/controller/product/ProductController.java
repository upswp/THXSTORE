package com.ssafy.thxstore.controller.product;

import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.product.domain.Product;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.dto.*;
import com.ssafy.thxstore.product.service.ProductService;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store/group", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final AppProperties appProperties;
    private final ImageService imageService;

/* 판매자 스토어 페이지(메뉴 관리(1, 그룹)) */

    @PatchMapping // 그룹 수정
    public ResponseEntity editGroup(@RequestHeader String authorization, @RequestBody EditGroupDto editGroupDto) {
        String email = jwtToEmail(authorization);
        productService.editGroup(editGroupDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping // 그룹 등록
    public ResponseEntity createGroup(@RequestHeader String authorization, @RequestBody CreateGroupDto createGroupDto) {
        String email = jwtToEmail(authorization);
        productService.createGroup(createGroupDto);
        return ResponseEntity.created(null).body(HttpStatus.CREATED);
    }

    @DeleteMapping // todo 그룹 삭제(매뉴 전체) -> 연관 매핑 확인
    public ResponseEntity deleteGroup(@RequestHeader String authorization, @RequestBody DeleteGroupDto deleteGroupDto) {
        String email = jwtToEmail(authorization);
        productService.deleteGroup(deleteGroupDto);

       return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping("{storeId}") // 그룹 전체 조회@RequestBody FindAllGroupDto findAllGroupDto
    public ResponseEntity findAllGroup(@RequestHeader String authorization, @PathVariable("storeId") Long storeId) {
        String email = jwtToEmail(authorization);
        Optional<List<ProductGroup>> productGroups = productService.findAllGroup(storeId);

        if(productGroups.isPresent()) {
            return ResponseEntity.created(null).body(productGroups);
        }
        else {
            return ResponseEntity.created(null).body(null);
        }
    }


    /* 판매자 스토어 페이지(매뉴관리(2, 그룹 메뉴 리스트)) */
    @GetMapping("/product/")// 그룹 매뉴 반환(조회)
    public ResponseEntity findAllGroupMenu(@RequestHeader String authorization, @RequestBody FindAllGroupMenuDto findAllGroupMenuDto) {
        String email = jwtToEmail(authorization);
        Optional<List<Product>> productList = productService.findAllGroupMenu(findAllGroupMenuDto);

        //fdf todo optional 생각하자 -> 다른 곳에 해당하는 것이 있는지 product 완료.
        if(productList.isPresent()){
            return ResponseEntity.created(null).body(productList.get());
        }else {
            return ResponseEntity.created(null).body(null);
        }
    }

    @DeleteMapping("/product/") // 매뉴 삭제
    public ResponseEntity deleteMenu(@RequestHeader String authorization, @RequestBody DeleteMenuDto deleteMenuDto) {
        String email = jwtToEmail(authorization);
        productService.deleteMenu(deleteMenuDto);
        return ResponseEntity.created(null).body(HttpStatus.OK);
    }




    /* 판매자 스토어 페이지(메뉴 관리(3, 메뉴 자세히)) */
    @PutMapping("/product/") //매뉴 수정
    public ResponseEntity editMenu(@RequestHeader String authorization, @RequestBody EditMenuDto editMenuDto) {
        String email = jwtToEmail(authorization);
        String productImg = null;
        if(editMenuDto.getProductImg() != null) {
            try {
                productImg = imageService.createImage(editMenuDto.getProductImg());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        productService.editMenu(productImg, editMenuDto);

        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @PostMapping("/product/") // 메뉴 등록
    public ResponseEntity createMenu(@RequestHeader String authorization, @ModelAttribute CreateMenuDto createMenuDto) {
        String email = jwtToEmail(authorization);
        String productImg = null;
        try {
            productImg = imageService.createImage(createMenuDto.getProductImg());
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.createMenu(productImg, createMenuDto);

        return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping("/product/{productId}") // 매뉴 상세 조회
    public ResponseEntity findMenu(@RequestHeader String authorization, @PathVariable Long productId) {
        String email = jwtToEmail(authorization);
        Product product = productService.findMenu(productId);
        return ResponseEntity.created(null).body(product);
    }

    /* 판매자 스토어 페이지(타임 딜) */






    /* etc */
    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }
}