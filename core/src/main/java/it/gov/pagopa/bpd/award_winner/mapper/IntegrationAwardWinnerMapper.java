package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to be used to map a {@link PaymentInfoAwardWinner from an* {@link AwardWinner}
 */

@Service
public class IntegrationAwardWinnerMapper {

    /**
     * @param paymentIntegrationAwardWinner instance of an  {@link PaymentIntegrationAwardWinner}, to be mapped into a {@link AwardWinner}
     * @return {@link AwardWinner} instance from the input paymentIntegrationAwardWinner,
     */
    public AwardWinner map(
            PaymentIntegrationAwardWinner paymentIntegrationAwardWinner) {

        AwardWinner awardWinner = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (paymentIntegrationAwardWinner != null) {
            awardWinner = AwardWinner.builder().build();
            awardWinner.setId(Long.valueOf(paymentIntegrationAwardWinner.getIdConsap().trim()
                    .replaceAll("[\uFEFF-\uFFFF]", "")));
            awardWinner.setRelatedEntryId(Long.valueOf(paymentIntegrationAwardWinner.getIdPagoPa().trim()
                    .replaceAll("[\uFEFF-\uFFFF]", "")));

            awardWinner.setAccountHolderFiscalCode(paymentIntegrationAwardWinner.getFiscalCode());
            awardWinner.setAccountHolderName(paymentIntegrationAwardWinner.getName());
            awardWinner.setAccountHolderSurname(paymentIntegrationAwardWinner.getSurname());
            awardWinner.setAwardPeriodId(Long.valueOf(paymentIntegrationAwardWinner.getAwardPeriodId()));
            awardWinner.setResult(paymentIntegrationAwardWinner.getEsito());
            awardWinner.setResultReason(paymentIntegrationAwardWinner.getCashbackAmount());
            awardWinner.setPayoffInstr(paymentIntegrationAwardWinner.getIban());
            awardWinner.setCro(paymentIntegrationAwardWinner.getCro());
            awardWinner.setCashback(BigDecimal.valueOf(
                    Long.parseLong(paymentIntegrationAwardWinner.getCashbackAmount()))
                    .divide(BigDecimal.valueOf(100L),2, RoundingMode.HALF_EVEN));
            awardWinner.setEnabled(true);
            awardWinner.setInsertDate(OffsetDateTime.now());
            awardWinner.setUpdateDate(OffsetDateTime.now());

            if (paymentIntegrationAwardWinner.getExecutionDate() != null
                    && !paymentIntegrationAwardWinner.getExecutionDate().isEmpty()) {
                awardWinner.setExecutionDate(LocalDate.parse(paymentIntegrationAwardWinner.getExecutionDate(), dtf));
            }

            if (paymentIntegrationAwardWinner.getPeriodStartDate() != null
                    && !paymentIntegrationAwardWinner.getPeriodStartDate().isEmpty()) {
                awardWinner.setAwardPeriodStart(
                        LocalDate.parse(paymentIntegrationAwardWinner.getPeriodStartDate(), dtf));
            }

            if (paymentIntegrationAwardWinner.getPeriodEndDate() != null
                    && !paymentIntegrationAwardWinner.getPeriodEndDate().isEmpty()) {
                awardWinner.setExecutionDate(LocalDate.parse(paymentIntegrationAwardWinner.getPeriodEndDate(), dtf));
            }

        }

        return awardWinner;

    }

}
