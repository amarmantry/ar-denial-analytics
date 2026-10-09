package com.ar.backend.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;


public class SeedGenerator {

    public static void main(String[] args) throws IOException {
        String[] payers    = {"Aetna", "Cigna", "UnitedHealthcare", "BlueCross", "Humana"};
        String[] providers = {"City Hospital", "Dr. Rao Clinic", "Dr. Shah Clinic", "Sunrise Health"};
        String[] cptCodes  = {"99213", "99214", "99215"};
        String[] reasons   = {"Missing authorization", "Eligibility lapse", "Duplicate claim",
                "Coding error", "Timely filing", "Missing documentation", "Medical necessity"};

        StringBuilder sql = new StringBuilder();

        for (int id = 1; id <= 80; id++) {
            int i = id - 1;

            String payer    = payers[i % payers.length];
            String provider = providers[i % providers.length];
            String cpt      = cptCodes[i % cptCodes.length];
            double amount   = 100 + (id * 137) % 900 + 0.50;
            LocalDate date  = LocalDate.of(2026, 7, 1).plusDays((id * 7) % 90);

            String status = "DENIED";
            if (id % 5 == 0) status = "PAID";
            else if (id % 9 == 0) status = "PENDING";

            String reason = "NULL";
            if (status.equals("DENIED")) {
                reason = "'" + reasons[i % reasons.length] + "'";
            }

            sql.append("INSERT INTO claims (id, payer, provider, cpt_code, billed_amount, denial_reason, denied_at, status) VALUES (")
                    .append(id).append(", '").append(payer).append("', '").append(provider).append("', '")
                    .append(cpt).append("', ").append(String.format(Locale.ROOT, "%.2f", amount)).append(", ")
                    .append(reason).append(", '").append(date).append("', '").append(status)
                    .append("') ON CONFLICT (id) DO NOTHING;\n");
        }

        Path out = Path.of("C:\\Users\\mantr\\OneDrive\\Desktop\\ar-denial-analytics\\backend\\src\\main\\resources\\data.sql");
        Files.writeString(out, sql.toString());
        System.out.println("Wrote " + out.toAbsolutePath());
    }
}