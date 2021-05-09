package com.ssafy.thxstore.controller.product;

import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.product.domain.ProductGroup;
import com.ssafy.thxstore.product.dto.CreateGroupDto;
import com.ssafy.thxstore.product.dto.DeleteGroupDto;
import com.ssafy.thxstore.product.dto.EditGroupDto;
import com.ssafy.thxstore.product.dto.FindAllGroupDto;
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

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/store/group", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final AppProperties appProperties;

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

    @DeleteMapping // 그룹 삭제(매뉴 전체)
    public ResponseEntity deleteGroup(@RequestHeader String authorization, @RequestBody DeleteGroupDto deleteGroupDto) {
        String email = jwtToEmail(authorization);
        productService.deleteGroup(deleteGroupDto);

       return ResponseEntity.created(null).body(HttpStatus.OK);
    }

    @GetMapping // 그룹 전체 조회
    public ResponseEntity findAllGroup(@RequestHeader String authorization, @RequestBody FindAllGroupDto findAllGroupDto) {
        String email = jwtToEmail(authorization);
        List<ProductGroup> productGroups = productService.findAllGroup(findAllGroupDto);

        return ResponseEntity.created(null).body(productGroups);
    }


    /* 판매자 스토어 페이지(매뉴관리(2, 그룹 메뉴 리스트)) */




    /* 판매자 스토어 페이지(메뉴 관리(3, 메뉴 자세히)) */


    /* 판매자 스토어 페이지(타임 딜) */



    /* etc */
    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }
}