package it.gov.pagopa.bpd.award_winner.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntegrationAwardWinner {

    String idConsap;

    String idReclamo;

    String idPagoPa;

    String fiscalCode;

    String iban;

    String name;

    String surname;

    String cashbackAmount;

    String causale;

    String periodStartDate;

    String periodEndDate;

    String awardPeriodId;

    String esito;

    String cro;

    String executionDate;

    String technicalCountProperty;

}
