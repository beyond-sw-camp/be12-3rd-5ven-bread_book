package com.example.breadbook.Report;

import com.example.breadbook.Report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
