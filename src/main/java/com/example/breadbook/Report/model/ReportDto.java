package com.example.breadbook.Report.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ReportDto {
    @Getter
    public static class ReportRegister {
        private Long member_idx;
        private Long product_idx;
        private String report_reason;
        private Date create_at;

        public Report toEntity(Product product, Member member) {

            return Report.builder()
                    .member_idx(member.getIdx())
                    .product_idx(product.getIdx())
                    .report_reason(report_reason)
                    .create_at(create_at)
                    .product(product)
                    .member(member)
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ReportResponse {
        private Long idx;
        private String report_reason;
        private Date create_at;
        private MemberInfo memberInfo;
        private ProductInfo productInfo;

        public static ReportResponse from(Report report) {
            return ReportResponse.builder()
                    .idx(report.getIdx())
                    .report_reason(report.getReport_reason())
                    .create_at(report.getCreate_at())
                    .productInfo(ProductInfo.from(report.getProduct()))
                    .memberInfo(MemberInfo.from(report.getMember()))
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MemberInfo {
        private Long member_idx;
        private String username;
        private String email;
//          추후 필요 정보 추가

        public static MemberInfo from(Member member) {
            return MemberInfo.builder()
                    .member_idx(member.getIdx())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProductInfo {
        private Long product_idx;
        private String booktitle;
        private String description;
        private int price;

        //          추후 의견논의와 수정 필요
        public static ProductInfo from(Product product) {
            return ProductInfo.builder()
                    .product_idx(product.getIdx())
                    .booktitle(product.getBook().getTitle())
                    .build();
        }
    }
}
