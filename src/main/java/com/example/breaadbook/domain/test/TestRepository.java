package com.example.breaadbook.domain.test;

import com.example.breaadbook.domain.test.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
