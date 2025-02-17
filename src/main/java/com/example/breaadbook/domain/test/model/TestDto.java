package com.example.breaadbook.domain.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestDto {
    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class TestResponse {
        private Long idx;
    }
}
