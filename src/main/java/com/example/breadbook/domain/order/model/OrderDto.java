package com.example.breaadbook.domain.order.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderDto {

    @Getter
    public static class OrderDtoReq{
        private int amount;
        private Long memberIdx;
        private Long productIdx;
    }

}
