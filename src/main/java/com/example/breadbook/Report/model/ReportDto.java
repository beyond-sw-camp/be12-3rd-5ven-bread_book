package com.example.breadbook.Report.model;


import com.example.breadbook.domain.member.model.Member;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportDto {
    @Getter
    public static class ReportRegister {
        private Long member_idx;
        private Long product_idx;
        private String report_reason;
        private Date create_at;
        List<MemberRegister> memberlist = new ArrayList<>();

        public Report toEntity(Member member) {
            return Report.builder()
                    .member_idx(member.getIdx())
                    .product_idx(product.getIdx())
                    .report_reason(report_reason)
                    .create_at(create_at)
                    .member(member)
                    .build();
        }
    }

    @Getter
    public static class MemberRegister {
    }
}
