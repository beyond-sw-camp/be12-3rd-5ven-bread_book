package com.example.breadbook.domain.report;

import com.example.breadbook.domain.report.model.Report;
import com.example.breadbook.domain.report.model.ReportDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public void register(ReportDto.ReportRegister dto, Member member, Product product) {
        Report report = reportRepository.save(dto.toEntity(product, member));
    }

    @Transactional(readOnly = true)
    public List<ReportDto.ReportResponse>list() {
        List<Report> reportList = reportRepository.findAll();

        return reportList.stream().map(ReportDto.ReportResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReportDto.ReportResponse read(Long reportIdx) {
        Report report = reportRepository.findById(reportIdx).orElseThrow();
        return ReportDto.ReportResponse.from(report);
    }
}
