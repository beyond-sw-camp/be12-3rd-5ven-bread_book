package com.example.breadbook.domain.report;

import com.example.breadbook.domain.report.model.ReportDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Tag(name = "신고 기능")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/register")
    @Operation(summary = "신고 하기", description = "신고를 하는 기능입니다.")
    public void register(@AuthenticationPrincipal Member member, Product product, @RequestBody ReportDto.ReportRegister dto) {
        reportService.register(dto, member, product);
    }

    @GetMapping("/list")
    @Operation(summary = "신고 리스트 출력", description = "신고받은 내용들을 리시트로 출력해 보여줍니다.")
    public ResponseEntity<List<ReportDto.ReportResponse>> list(Long userIdx) {
        List<ReportDto.ReportResponse> res = reportService.list(userIdx);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{reportIdx}")
    @Operation(summary = "신고 내역 상세보기", description = "하나의 신고 내역 상세보는 기능입니다.")
    public ResponseEntity<ReportDto.ReportResponse> get(@PathVariable Long reportIdx) {
        ReportDto.ReportResponse response = reportService.read(reportIdx);
        return ResponseEntity.ok(response);
    }
}
