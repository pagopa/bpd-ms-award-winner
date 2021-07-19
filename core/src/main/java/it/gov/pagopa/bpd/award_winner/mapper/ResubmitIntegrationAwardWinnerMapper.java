package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Class to be used to map a {@link AwardWinner from an* {@link AwardWinnerError}
 */

@Service
public class ResubmitIntegrationAwardWinnerMapper {

    /**
     * @param awardWinnerError instance of an  {@link AwardWinnerError}, to be mapped into a {@link AwardWinner}
     * @return {@link AwardWinner} instance from the input awardWinnerError,
     */
    public PaymentIntegration map(
            AwardWinnerError awardWinnerError) {

        PaymentIntegration paymentIntegration = null;

        if (awardWinnerError != null) {
            paymentIntegration = PaymentIntegration.builder().build();
            BeanUtils.copyProperties(awardWinnerError, paymentIntegration);
        }

        return paymentIntegration;

    }

}
