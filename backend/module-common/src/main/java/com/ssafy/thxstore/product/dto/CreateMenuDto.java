package com.ssafy.thxstore.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMenuDto {
    Long groupId;
    String name;
    Integer price;
    MultipartFile productImg;
    String amount;
    String introduce;
    // Integer rate;
    // Integer stock;
}