package com.example.breadbook.domain.report;

import com.example.breadbook.domain.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
