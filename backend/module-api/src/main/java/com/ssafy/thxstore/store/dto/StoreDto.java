package com.ssafy.thxstore.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String license;

    @NotEmpty
    private String phoneNum;

    @NotEmpty
    private String openTime;

    @NotEmpty
    private String endTime;

    private String closeDay;
}
