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
    LOGIN_SUCCESS(true, 2201, "로그인 되었습니다."),
    LOGIN_UNAUTHORIZED(false, 2404, "로그인 되어 있지 않습니다."),
    LOGIN_ID_PW_FAILED(false, 2401, "ID나 비밀번호가 틀렸습니다."),
    LOGIN_VERIFY_DISABLED(false, 2403, "이메일이 인증되지 않았습니다."),
    EMAIL_VERIFY_SUCCESS(true, 2201, "이메일 인증에 성공했습니다."),
    EMAIL_VERIFY_NULL(false, 2404, "해당 uuid가 없습니다."),
    FIND_ID_SUCCESS(true, 2201, "ID를 성공적으로 찾았습니다."),
    FIND_ID_NULL(false, 2404, "해당 이름과 email로 가입된 ID가 없습니다."),

    // ========================================================================================================================
    // 상품 기능(3000)
    // 상품 생성 3000


    // ========================================================================================================================
    // 주문 기능(4000)
    // 주문 생성 4000
    ORDER_REGISTER_SUCCESS(true, 4000, "상품 주문이 완료 되었습니다."),
    ORDER_ORDERlISTFIND_SUCCESS(true, 4001, "주문 정보 확인"),
    ORDER_PAYlISTFIND_SUCCESS(true, 4002, "판매 정보 확인"),
    ORDER_ORDERDETAILS_SUCCESS(true, 4003, "주문 상세정보 확인"),

    // 리뷰 기능(5000)
    // 리뷰 생성 5000
    REVIEW_REGISTER_SUCCESS(true, 5000, "리뷰 작성이 완료 되었습니다."),
    REVIEW_FIND_SUCCESS(true, 5001, "행당 리뷰가 있습니다."),


    // ==========================================================================================================================
    CHATTINGROOM_SUCCESS(true, 6000, "채팅방이 생성되었습니다"),
    CHATTINGROOM_LIST_SUCCESS(true, 6001, "채팅방 목록이 조회되었습니다"),
    CHATTINGROOM_LIST_DETAIL_SUCCESS(true, 6002, "채팅방이 상세 조회되었습니다"),
















    ;
    private Boolean isSuccess;
    private Integer code;
    private String message;


}
