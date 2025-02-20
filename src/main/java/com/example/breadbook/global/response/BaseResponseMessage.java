package com.example.breadbook.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum BaseResponseMessage {
    // ========================================================================================================================
    // 요청 성공, 실패, 내부 서버 오류
    REQUEST_SUCCESS(true, 200, "요청이 정상적으로 처리되었습니다"),
    REQUEST_FAIL(false, 404, "요청을 실패했습니다."),
    INTERNAL_SERVER_ERROR(false, 500, "내부 서버 오류"),

    // ========================================================================================================================
    // 회원 기능(2000)
    // 회원가입 2000


    // ========================================================================================================================
    // 상품 기능(3000)
    // 상품 생성 3000


    // ========================================================================================================================
    // 주문 기능(4000)
    // 주문 생성 4000
    ORDER_REGISTER_SUCCESS(true, 4000, "상품 주문이 완료 되었습니다.")
















    ;
    private Boolean isSuccess;
    private Integer code;
    private String message;


}
