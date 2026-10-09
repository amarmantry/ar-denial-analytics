package com.ar.backend.dto;

import java.math.BigDecimal;

public record ReasonDenialSummary(String denialReason, BigDecimal totalDeniedAmount) {}