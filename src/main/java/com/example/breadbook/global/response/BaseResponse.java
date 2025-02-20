package com.example.breadbook.global.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private Boolean isSuccess;
    private String message;
    private Integer code;
    private T data;
}
