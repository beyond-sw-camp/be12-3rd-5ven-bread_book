package com.example.breadbook.domain.report;

import com.example.breadbook.domain.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByMemberIdx(Long idx);
}
