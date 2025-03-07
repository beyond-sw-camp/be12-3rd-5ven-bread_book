package com.example.breadbook.global.handler;

import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> IOException(IOException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("에러");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 어떤 변수에 값을 설정하다가 에러가 났는지
        System.out.println(e.getBindingResult().getFieldError().getField());

        // 입력값 검증 할 때 작성한 message를 받아오는 코드
        System.out.println(e.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(new BaseResponse<String>(BaseResponseMessage.REQUEST_FAIL,
                e.getBindingResult().getFieldError().getDefaultMessage()));
    }
}