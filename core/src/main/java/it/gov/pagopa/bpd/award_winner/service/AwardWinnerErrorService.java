package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;

import java.util.List;

/**
 * A service to manage the Business Logic related to AwardWinnerError
 */
public interface AwardWinnerErrorService {

    AwardWinnerError saveErrorRecord(AwardWinnerError awardWinnerErrorRecord);

    List<AwardWinnerError> findRecordsToResubmit();

}
