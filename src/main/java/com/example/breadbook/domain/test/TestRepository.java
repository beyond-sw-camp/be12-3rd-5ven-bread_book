package com.example.breadbook.domain.test;

import com.example.breadbook.domain.test.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
