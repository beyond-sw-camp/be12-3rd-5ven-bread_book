package com.example.breadbook.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private Boolean isSuccess;
    private String message;
    private Integer code;
    private T data;
}
