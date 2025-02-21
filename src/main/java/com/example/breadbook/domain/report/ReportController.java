package com.example.breadbook.domain.report;

import com.example.breadbook.domain.report.model.ReportDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/register")
    public void register(@AuthenticationPrincipal Member member, Product product, @RequestBody ReportDto.ReportRegister dto) {
        reportService.register(dto, member, product);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReportDto.ReportResponse>> list() {
        List<ReportDto.ReportResponse> res = reportService.list();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{reportIdx")
    public ResponseEntity<ReportDto.ReportResponse> get(@PathVariable Long reportIdx) {
        ReportDto.ReportResponse response = reportService.read(reportIdx);
        return ResponseEntity.ok(response);
    }
}
