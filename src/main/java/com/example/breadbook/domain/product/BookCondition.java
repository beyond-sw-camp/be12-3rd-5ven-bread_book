package com.example.breadbook.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BookCondition {
    상("GOOD", "상"),
    중("NORMAL", "중"),
    하("BAD", "하");

    private final String dbValue;  // DB 저장 값
    private final String description;  // 설명

    BookCondition(String dbValue, String description) {
        this.dbValue = dbValue;
        this.description = description;
    }

    @JsonValue
    public String getDbValue() {
        return this.dbValue;  // JSON 응답 시 'GOOD', 'NORMAL', 'BAD' 등으로 변환됨
    }

    @JsonCreator
    public static BookCondition fromDbValue(String dbValue) {
        for (BookCondition condition : values()) {
            if (condition.getDbValue().equalsIgnoreCase(dbValue)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Unknown BookCondition: " + dbValue);
    }
}

