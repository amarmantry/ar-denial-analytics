package com.ar.backend.controller;

import com.ar.backend.entity.Claims;
import com.ar.backend.repository.ClaimsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimsController {

    private final ClaimsRepository repo;

    @GetMapping
    public Page<Claims> getClaims(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "payer", required = false) String payer,
            @RequestParam(name = "denialReason", required = false) String denialReason,
            @RequestParam(name = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(name = "toDate", required = false) LocalDate toDate) {
        if (page < 0) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "page must be 0 or more"
        );
        if (size < 1 || size > 100) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "size must be between 1 and 100"
        );
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "'from' must not be after 'to'");
        if (payer != null && payer.isBlank()) {
            payer = null;
        }
        if (denialReason != null && denialReason.isBlank()) {
            denialReason = null;
        }

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "deniedAt").and(Sort.by(Sort.Direction.DESC, "id")));

        return repo.findDenied(payer, denialReason, fromDate, toDate, pageable);
    }
}