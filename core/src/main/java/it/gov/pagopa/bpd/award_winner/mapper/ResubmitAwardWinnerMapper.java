package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
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
    public AwardWinner map(
            AwardWinnerError awardWinnerError) {

        AwardWinner awardWinner = null;

        if (awardWinnerError != null) {
            awardWinner = AwardWinner.builder().build();
            BeanUtils.copyProperties(awardWinnerError, awardWinner);
        }

        return awardWinner;

    }

}
