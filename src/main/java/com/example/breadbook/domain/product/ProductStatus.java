package com.example.breadbook.domain.product;

import lombok.Getter;

@Getter
public enum ProductStatus {
    판매중("SALE", "현재 판매 중"),
    거래_완료("SOLD", "거래가 완료됨"),
    거래_예약중("RESERVED", "거래가 예약됨"),
    게시글_삭제("DELETED", "게시글이 삭제됨");

    private final String dbValue;  // DB에 저장될 값
    private final String description;  // 설명

    ProductStatus(String dbValue, String description) {
        this.dbValue = dbValue;
        this.description = description;
    }

    // DB에 저장된 문자열을 ENUM으로 변환하는 메서드
    public static ProductStatus fromDbValue(String dbValue) {
        for (ProductStatus status : values()) {
            if (status.getDbValue().equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown DB value: " + dbValue);
    }
}

