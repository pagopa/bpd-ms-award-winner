package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPayment;

/**
 * A service to manage the Business Logic related to AwardWinner
 */
public interface IntegratedPaymentService {

    AwardWinner createRecord(IntegratedPayment integratedPayment) throws Exception;
}
