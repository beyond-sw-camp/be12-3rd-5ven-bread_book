package com.example.breaadbook.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;
}
