package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;

import java.io.IOException;

/**
 * A service to manage the Business Logic related to AwardWinner
 */
public interface AwardWinnerService {

    AwardWinner insertIntegrationAwardWinner(AwardWinner awardWinner) throws Exception;

    AwardWinner updateAwardWinner(AwardWinner awardWinner) throws Exception;


    void updatingWinnersTwiceWeeks() throws IOException;
}
