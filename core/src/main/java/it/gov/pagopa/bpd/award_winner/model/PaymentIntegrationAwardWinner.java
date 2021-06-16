package it.gov.pagopa.bpd.award_winner.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntegrationAwardWinner {

    @NotBlank
    @NotNull
    @Size(max = 9)
    String idConsap;

    @NotBlank
    @NotNull
    @Size(max = 28)
    String idComplaint;

    @Size(max = 9)
    String idPagoPa;

    @NotBlank
    @NotNull
    @Size(max = 16)
    String fiscalCode;

    @NotBlank
    @NotNull
    @Size(max = 27)
    String iban;

    @NotBlank
    @NotNull
    @Size(max = 50)
    String name;

    @NotBlank
    @NotNull
    @Size(max = 50)
    String surname;

    @NotNull
    BigDecimal cashbackAmount;

    @NotNull
    BigDecimal amount;

    @NotNull
    BigDecimal jackpotAmount;

    @NotBlank
    @NotNull
    @Size(max = 140)
    String resultReason;

    @NotNull
    String periodStartDate;

    @NotNull
    String periodEndDate;

    @NotNull
    @NotBlank
    @Size(max = 25)
    String result;

    @NotNull
    @NotBlank
    @Size(max = 35)
    String cro;

    String executionDate;

}
