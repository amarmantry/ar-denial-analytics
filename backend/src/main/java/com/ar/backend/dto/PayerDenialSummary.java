package com.ar.backend.dto;

import java.math.BigDecimal;

public record PayerDenialSummary(String payer, BigDecimal totalDeniedAmount, Long deniedClaimCount) {}