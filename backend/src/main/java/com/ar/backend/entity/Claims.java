package com.ar.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "claims")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Claims {
    @Id
    Long id;
    @Column(nullable = false)
    String payer;
    @Column(nullable = false)
    String provider;
    @Column(nullable = false)
    String cptCode;
    @Column(nullable = false)
    BigDecimal billedAmount;
    @Column
    String denialReason;
    @Column(nullable = false)
    LocalDate deniedAt;
    @Column(nullable = false)
    String status;
}
