package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;

/**
 * A service to manage the Business Logic related to AwardWinner
 */
public interface AwardWinnerService {

    AwardWinnerIntegration insertIntegrationAwardWinner(AwardWinnerIntegration awardWinner) throws Exception;

    AwardWinner updateAwardWinner(AwardWinner awardWinner) throws Exception;
}
