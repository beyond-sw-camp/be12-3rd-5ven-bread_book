package com.example.breadbook.domain.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestDto {
    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class TestResponse {
        private Long idx;
    }

    @Builder
    @Getter
    public static class tokenUser{
        private Long idx;
        private String userId;
        private String username;
    }
}
