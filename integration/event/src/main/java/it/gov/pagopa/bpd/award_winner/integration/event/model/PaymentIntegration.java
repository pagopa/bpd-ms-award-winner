package it.gov.pagopa.bpd.award_winner.integration.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntegration {

    String idConsap;

    String idComplaint;

    String idPagoPa;

    String fiscalCode;

    String iban;

    String name;

    String surname;

    BigDecimal cashbackAmount;

    String resultReason;

    String periodStartDate;

    String periodEndDate;

    String awardPeriodId;

    String result;

    String cro;

    String executionDate;

    String technicalCountProperty;

}
