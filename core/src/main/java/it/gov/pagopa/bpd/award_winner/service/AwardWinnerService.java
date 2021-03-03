package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;

/**
 * A service to manage the Business Logic related to AwardWinner
 */
public interface AwardWinnerService {

    AwardWinner insertIntegrationAwardWinner(AwardWinner awardWinner);

    AwardWinner updateAwardWinner(AwardWinner awardWinner) throws Exception;
}
