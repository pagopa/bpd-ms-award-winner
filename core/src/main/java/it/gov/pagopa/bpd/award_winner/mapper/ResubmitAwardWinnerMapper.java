package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.consap_csv_connector.integration.event.model.PaymentInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Class to be used to map a {@link AwardWinner from an* {@link AwardWinnerError}
 */

@Service
public class ResubmitAwardWinnerMapper {

    /**
     * @param awardWinnerError instance of an  {@link AwardWinnerError}, to be mapped into a {@link AwardWinner}
     * @return {@link AwardWinner} instance from the input awardWinnerError,
     */
    public PaymentInfo map(
            AwardWinnerError awardWinnerError) {

        PaymentInfo paymentInfo = null;

        if (awardWinnerError != null) {
            paymentInfo = PaymentInfo.builder().build();
            BeanUtils.copyProperties(awardWinnerError, paymentInfo);
            paymentInfo.setUniqueID(String.valueOf(awardWinnerError.getUniqueId()));
            paymentInfo.setExecutionDate(String.valueOf(awardWinnerError.getExecutionDate()));
        }

        return paymentInfo;

    }

}
