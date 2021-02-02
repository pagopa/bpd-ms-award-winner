package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Class to be used to map a {@link PaymentInfoAwardWinner from an* {@link AwardWinner}
 */

@Service
public class AwardWinnerErrorMapper {

    /**
     * @param paymentInfoAwardWinner instance of an  {@link PaymentInfoAwardWinner}, to be mapped into a {@link AwardWinner}
     * @return {@link PaymentInfoAwardWinner} instance from the input paymentInfoAwardWinner,
     */
    public AwardWinnerError map(
            PaymentInfoAwardWinner paymentInfoAwardWinner, String exceptionDescription) {

        AwardWinnerError awardWinnerError = null;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (paymentInfoAwardWinner != null) {
            awardWinnerError = AwardWinnerError.builder().build();
            BeanUtils.copyProperties(paymentInfoAwardWinner, awardWinnerError);
            awardWinnerError.setUniqueId(paymentInfoAwardWinner.getUniqueID());
            awardWinnerError.setExceptionMessage(exceptionDescription);
        }

        return awardWinnerError;

    }

}
