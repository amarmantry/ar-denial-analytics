package com.ar.backend.repository;

import com.ar.backend.dto.PayerDenialSummary;
import com.ar.backend.dto.ReasonDenialSummary;
import com.ar.backend.entity.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Long>, JpaSpecificationExecutor<Claims> {
    @Query("""
        select new com.ar.backend.dto.PayerDenialSummary(c.payer, sum(c.billedAmount), count(c))
        from Claims c
        where c.status = 'DENIED'
        group by c.payer
        order by sum(c.billedAmount) desc
        """)
    List<PayerDenialSummary> findPayerDenialSummary();

    @Query("""
        select new com.ar.backend.dto.ReasonDenialSummary(c.denialReason, sum(c.billedAmount))
        from Claims c
        where c.status = 'DENIED'
        group by c.denialReason
        order by sum(c.billedAmount) desc
        """)
    List<ReasonDenialSummary> findReasonDenialSummary();

    @Query("""
    select c from Claims c
    where c.status = 'DENIED'
      and (:payer is null or c.payer = :payer)
      and (:denialReason is null or c.denialReason = :denialReason)
      and (cast(:fromDate as date) is null or c.deniedAt >= :fromDate)
      and (cast(:toDate as date) is null or c.deniedAt <= :toDate)
    """)
    Page<Claims> findDenied(@Param("payer") String payer,
                            @Param("denialReason") String denialReason,
                            @Param("fromDate") LocalDate fromDate,
                            @Param("toDate") LocalDate toDate,
                            Pageable pageable);
}
