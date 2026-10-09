package com.ar.backend.controller;

import com.ar.backend.dto.PayerDenialSummary;
import com.ar.backend.dto.ReasonDenialSummary;
import com.ar.backend.repository.ClaimsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/denials")
@RequiredArgsConstructor
public class DenialsController {

    private final ClaimsRepository repo;

    @GetMapping("/by-payer")
    public List<PayerDenialSummary> byPayer() {
        return repo.findPayerDenialSummary();
    }

    @GetMapping("/by-reason")
    public List<ReasonDenialSummary> byReason() {
        return repo.findReasonDenialSummary();
    }
}
