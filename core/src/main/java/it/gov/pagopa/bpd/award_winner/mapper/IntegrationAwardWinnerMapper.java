package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;
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
    public AwardWinnerIntegration map(
            PaymentIntegrationAwardWinner paymentIntegrationAwardWinner) {

        AwardWinnerIntegration awardWinner = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (paymentIntegrationAwardWinner != null) {
            awardWinner = AwardWinnerIntegration.builder().build();
            awardWinner.setConsapId(Long.valueOf(paymentIntegrationAwardWinner.getIdConsap().trim()
                    .replaceAll("[\uFEFF-\uFFFF]", "")));
            awardWinner.setRelatedId(Long.valueOf(paymentIntegrationAwardWinner.getIdPagoPa().trim()
                    .replaceAll("[\uFEFF-\uFFFF]", "")));
            awardWinner.setTicketId(Long.valueOf(paymentIntegrationAwardWinner.getIdComplaint().trim()
                    .replaceAll("[\uFEFF-\uFFFF]", "")));

            awardWinner.setFiscalCode(paymentIntegrationAwardWinner.getFiscalCode());
            awardWinner.setAccountHolderFiscalCode(paymentIntegrationAwardWinner.getFiscalCode());
            awardWinner.setAccountHolderName(paymentIntegrationAwardWinner.getName());
            awardWinner.setAccountHolderSurname(paymentIntegrationAwardWinner.getSurname());
            awardWinner.setResult(paymentIntegrationAwardWinner.getResult());
            awardWinner.setResultReason(paymentIntegrationAwardWinner.getResultReason());
            awardWinner.setPayoffInstr(paymentIntegrationAwardWinner.getIban());
            awardWinner.setCro(paymentIntegrationAwardWinner.getCro());
            awardWinner.setCashback(paymentIntegrationAwardWinner.getCashbackAmount());
            awardWinner.setEnabled(true);
            awardWinner.setStatus(AwardWinnerIntegration.Status.INTEGRATION);
            OffsetDateTime executionDate = OffsetDateTime.now();
            awardWinner.setInsertDate(executionDate);
            awardWinner.setUpdateDate(executionDate);
            awardWinner.setAmount(paymentIntegrationAwardWinner.getAmount());
            awardWinner.setJackpot(paymentIntegrationAwardWinner.getJackpotAmount());

            if (paymentIntegrationAwardWinner.getExecutionDate() != null
                    && !paymentIntegrationAwardWinner.getExecutionDate().isEmpty()) {
                awardWinner.setExecutionDate(
                        LocalDate.parse(paymentIntegrationAwardWinner.getExecutionDate(), dtf));
            }

            if (paymentIntegrationAwardWinner.getPeriodStartDate() != null
                    && !paymentIntegrationAwardWinner.getPeriodStartDate().isEmpty()) {
                awardWinner.setAwardPeriodStart(
                        LocalDate.parse(paymentIntegrationAwardWinner.getPeriodStartDate(), dtf));
            }

            if (paymentIntegrationAwardWinner.getPeriodEndDate() != null
                    && !paymentIntegrationAwardWinner.getPeriodEndDate().isEmpty()) {
                awardWinner.setAwardPeriodEnd(
                        LocalDate.parse(paymentIntegrationAwardWinner.getPeriodEndDate(), dtf));
            }

        }

        return awardWinner;

    }

}
