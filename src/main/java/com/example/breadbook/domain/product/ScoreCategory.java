package com.example.breadbook.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ScoreCategory {
    BREAD_TOP(200, Integer.MAX_VALUE, "샌드위치"),
    BREAD_SECOND(180, 199, "식빵"),
    BREAD_THIRD(100, 179, "반죽"),
    BREAD_FOURTH(0, 99, "밀가루"),
    BREAD_NEW(-50, -1, "");

    private final int min;
    private final int max;
    private final String description;



    public static String toCategoryString(Integer sellerScore) {
        if (sellerScore == null) {return "등급 정보 미상";}
        return Arrays.stream(values())
                .filter(category -> sellerScore >= category.min && sellerScore <= category.max)
                .findFirst()
                .map(ScoreCategory::getDescription)
                .orElse("등급 정보 미상");
    }

}
