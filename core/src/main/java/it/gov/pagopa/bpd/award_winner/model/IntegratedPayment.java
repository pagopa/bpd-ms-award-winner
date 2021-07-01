package it.gov.pagopa.bpd.award_winner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegratedPayment {

    @NotNull
    @NotBlank
    String fiscalCode;

    Long awardPeriodId;

    Long ticketId;

    Long relatedPaymentId;

    BigDecimal amount;

    BigDecimal cashbackAmount;

    BigDecimal jackpotAmount;
}
